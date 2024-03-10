package et.com.gebeya.order_service.service;

import et.com.gebeya.order_service.Enum.OrderStatus;
import et.com.gebeya.order_service.dto.requestDto.OrderItemRequestDto;
import et.com.gebeya.order_service.dto.requestDto.OrderRequestDto;
import et.com.gebeya.order_service.dto.responseDto.OrderResponseDto;
import et.com.gebeya.order_service.model.*;
import et.com.gebeya.order_service.repository.OrderRepository;
import et.com.gebeya.order_service.repository.ProductRepository;
import et.com.gebeya.order_service.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto request){
        Orders orders=new Orders();
        Restaurant restaurant=restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()->new IllegalArgumentException("No restaurant found with this id"));

        List<Address> addresses=restaurant.getAddresses();
        if (addresses.isEmpty()){
            throw new  IllegalArgumentException("No delivery address found for the restaurant");
        }
        Address deliveryAddress = addresses.get(0);
        String fullAddress = deliveryAddress.getWereda() + ", " + deliveryAddress.getSubCity() + ", " + deliveryAddress.getCity();
        orders.setDeliveryAddress(fullAddress);
        orders.setRestaurant(restaurant);
        orders.setOrderStatus(OrderStatus.PENDING);

       double totalPrice=0.0;
       List<OrderItem> successfulOrderItems=new ArrayList<>();
        List<OrderItemRequestDto> failedOrderItems = new ArrayList<>();
        for (OrderItemRequestDto itemRequest : request.getOrderItems()) {
            Product product = productRepository.findById(Long.valueOf(itemRequest.getProductId()))
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemRequest.getProductId()));

            int availableQuantity = product.getQuantity();
            int requestQuantity = itemRequest.getQuantity();

            if (availableQuantity >= requestQuantity) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(requestQuantity);
                orderItem.setPrice(product.getPrice() * requestQuantity);

                // Associate the OrderItem with the Orders entity
                orderItem.setOrders(orders);

                successfulOrderItems.add(orderItem);
                totalPrice += orderItem.getPrice();
            } else {
                failedOrderItems.add(itemRequest);
            }
        }

        // Set the OrderItems for the Orders entity
        orders.setOrderItems(successfulOrderItems);
        orders.setTotalPrice(totalPrice);

        // Persist the Orders entity
        Orders savedOrder = orderRepository.save(orders);

        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(savedOrder.getId());
        response.setOrderStatus(savedOrder.getOrderStatus());
        response.setTotalPrice(savedOrder.getTotalPrice());
        if (!failedOrderItems.isEmpty()) {
            response.setMessage("Some items were not available in sufficient quantity and were not included in the order");
        }
        return response;
    }
    public void cancelOrder(Integer orderId, Integer restaurantId) {
        // Retrieve the order from the repository
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!orders.getRestaurant().getId().equals(restaurantId)) {
            throw new IllegalArgumentException("Restaurant is not authorized to cancel this order");
        }

        // Update order status to cancelled
        orders.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(orders);
    }
    @Transactional
    public List<Orders> getAllOrdersExceptCancelled() {
        return orderRepository.findByOrderStatusNot(OrderStatus.CANCELED);

    }

    public List<Orders> getOrdersByRestaurantId(Integer restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
    public void updateOrderStatusToCompleted(Integer orderId) {
        // Retrieve the order from the repository
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Check if the order status is already completed
        if (orders.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Order is already completed");
        }
        if (orders.getOrderStatus() == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("Order is cancelled");
        }

        // Update product quantities
        List<OrderItem> orderItems = orders.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            int orderedQuantity = orderItem.getQuantity();
            int currentQuantity = product.getQuantity();
            int updatedQuantity = currentQuantity - orderedQuantity;
            if (updatedQuantity < 0) {
                // Handle insufficient quantity error
                orders.setOrderStatus(OrderStatus.DECLINED);
                throw new RuntimeException("Insufficient quantity for product: " + product.getName());

            }
            product.setQuantity(updatedQuantity);
            productRepository.save(product);
        }

        // Update order status to completed
        orders.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(orders);
    }

}
