package et.com.gebeya.order_service.service;

import et.com.gebeya.order_service.Enum.OrderStatus;
import et.com.gebeya.order_service.dto.requestDto.*;
import et.com.gebeya.order_service.dto.responseDto.OrderResponseDto;
import et.com.gebeya.order_service.dto.responseDto.ProductResponse;
import et.com.gebeya.order_service.model.*;
import et.com.gebeya.order_service.repository.OrderRepository;
import et.com.gebeya.order_service.repository.ProductRepository;
import et.com.gebeya.order_service.repository.RestaurantRepository;
import et.com.gebeya.order_service.repository.Specification.OrderSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final SmsService smsService;
    private final MockPaymentService mockPaymentService;
    private final WebClient.Builder webclientBuilder;
    @Value("${afroMessage.identifier}")
    public String identifier;


    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto request) {
        Orders orders = createOrderFromRequest(request);
        Orders savedOrder = orderRepository.save(orders);
        sendOrderSubmittedNotification(savedOrder);
        return createOrderResponse(savedOrder);
    }

    @Transactional
    public void cancelOrder(Integer orderId, Integer restaurantId) {
        Orders orders = getOrderById(orderId);
        validateRestaurantAuthorization(orders, restaurantId);
        orders.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(orders);
        sendOrderCancelledNotification(orders);
    }

    @Transactional
    public List<Orders> getAllOrdersExceptCancelled() {
        return orderRepository.findByOrderStatusNot(OrderStatus.CANCELED);
    }

    public List<Orders> getOrdersByRestaurantId(Integer restaurantId) {
        Specification<Orders> spec = OrderSpecification.findByRestaurantId(restaurantId);
        return orderRepository.findAll(spec);
    }

    @Transactional
    public String declineOrAcceptOrders(Integer orderId) {
        Orders orders = getOrderById(orderId);
        validateOrderStatus(orders, OrderStatus.PENDING);
        if (!validateProductQuantities(orders)) {
            orders.setOrderStatus(OrderStatus.DECLINED);
            orderRepository.save(orders);
            return orders.getOrderStatus().toString();
        }
        orders.setOrderStatus(OrderStatus.ACCEPTED);
        orderRepository.save(orders);
        sendOrderAcceptedNotification(orders);
        return orders.getOrderStatus().toString();
    }

    @Transactional
    public OrderPaymentInfo completeOrder(Integer orderId) {
        Orders orders = getOrderById(orderId);
        validateOrderStatus(orders, OrderStatus.ACCEPTED);
        boolean paymentSuccessful = mockPaymentService.processPayment(orders);
        if (!paymentSuccessful) {
            throw new RuntimeException("Payment processing failed");
        }
        updateProductQuantities(orders);
        orders.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(orders);
        sendOrderCompletedNotification(orders);
        return calculateOrderPaymentInfo(orders);
    }

    @Transactional
    private Orders createOrderFromRequest(OrderRequestDto request) {
        Orders orders = new Orders();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("No restaurant found with this id"));

        List<Address> addresses = restaurant.getAddresses();
        if (addresses.isEmpty()) {
            throw new IllegalArgumentException("No delivery address found for the restaurant");
        }
        Address deliveryAddress = addresses.get(0);
        String fullAddress = deliveryAddress.getWereda() + ", " + deliveryAddress.getSubCity() + ", " + deliveryAddress.getCity();
        orders.setDeliveryAddress(fullAddress);
        orders.setRestaurant(restaurant);
        orders.setOrderStatus(OrderStatus.PENDING);

        double totalPrice = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDto itemRequest : request.getOrderItems()) {
//            Product product = productRepository.findById(Long.valueOf(itemRequest.getProductId()))
//                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemRequest.getProductId()));
Product product=getProductById(itemRequest.getProductId());
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice() * itemRequest.getQuantity());
            orderItem.setOrders(orders);
            orderItems.add(orderItem);
            totalPrice += orderItem.getPrice();
        }

        orders.setOrderItems(orderItems);
        orders.setTotalPrice(totalPrice);
        return orders;
    }


    private OrderResponseDto createOrderResponse(Orders savedOrder) {
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(savedOrder.getId());
        response.setOrderStatus(savedOrder.getOrderStatus());
        response.setTotalPrice(savedOrder.getTotalPrice());
        return response;
    }

    private Orders getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    private void validateRestaurantAuthorization(Orders orders, Integer restaurantId) {
        if (!orders.getRestaurant().getId().equals(restaurantId)) {
            throw new IllegalArgumentException("Restaurant is not authorized to cancel this order");
        }
    }

    private void validateOrderStatus(Orders orders, OrderStatus expectedStatus) {
        if (!orders.getOrderStatus().equals(expectedStatus)) {
            throw new IllegalArgumentException("Order status is not as expected");
        }
    }

    private boolean validateProductQuantities(Orders orders) {
        for (OrderItem orderItem : orders.getOrderItems()) {
            Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int currentQuantity = product.getQuantity();
            if (currentQuantity < orderedQuantity) {
                return false;
            }
        }
        return true;
    }
    private Product getProductById(Long productId){
        return webclientBuilder.build()
                .get()
                .uri("http://INVENTORY-MANAGEMENT/api/v1/products/get/"+productId)
                .header("role","SYSTEM")
                .header("roleId","0")
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    private void updateProductQuantities(Orders orders) {
        WebClient webClient=webclientBuilder.build();
        for (OrderItem orderItem : orders.getOrderItems()) {
            Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int currentQuantity = product.getQuantity();
            int updatedQuantity = currentQuantity - orderedQuantity;
         product.setQuantity(updatedQuantity);
//            productRepository.save(product);
            StockAdjustmentDTO stockAdjustment=new StockAdjustmentDTO(product.getId(), orderedQuantity);

                ProductResponse response=webClient.put()
                        .uri("http://INVENTORY-MANAGEMENT/api/v1/products/order")
                        .header("role","SYSTEM")
                        .header("roleId","0")
                        .bodyValue(stockAdjustment)
                        .retrieve()
                        .bodyToMono(ProductResponse.class)

                        .block() ;


        }
    }


    private OrderPaymentInfo calculateOrderPaymentInfo(Orders orders) {
        double totalPrice = orders.getOrderItems().stream()
                .mapToDouble(orderItem -> orderItem.getProduct().getPrice() * orderItem.getQuantity())
                .sum();
        return new OrderPaymentInfo(orders.getId(), totalPrice);
    }

    private void sendOrderSubmittedNotification(Orders orders) {
        String message = "Your order has been submitted and is pending.";
        sendNotification(orders.getRestaurant(), message);
    }

    private void sendOrderCancelledNotification(Orders orders) {
        String message = "Your order has been cancelled.";
        sendNotification(orders.getRestaurant(), message);
    }

    private void sendOrderAcceptedNotification(Orders orders) {
        String message = "Your order has been accepted.";
        sendNotification(orders.getRestaurant(), message);
    }

    private void sendOrderCompletedNotification(Orders orders) {
        String message = "Your order has been completed.";
        sendNotification(orders.getRestaurant(), message);
    }

    private void sendNotification(Restaurant restaurant, String message) {
        try {
            smsService.sendSms(restaurant.getPhoneNumber().get(0).getPhoneNumber(), identifier, "", message);
        } catch (IOException e) {
            log.error("Failed to send SMS notification to restaurant: " + restaurant.getOwnerName(), e);
        }
    }


}
