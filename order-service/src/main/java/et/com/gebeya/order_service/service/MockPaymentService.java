package et.com.gebeya.order_service.service;

import et.com.gebeya.order_service.dto.requestDto.OrderItemRequestDto;
import et.com.gebeya.order_service.dto.requestDto.OrderRequestDto;
import et.com.gebeya.order_service.model.OrderItem;
import et.com.gebeya.order_service.model.Orders;
import et.com.gebeya.order_service.model.Product;
import et.com.gebeya.order_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockPaymentService {
    private final ProductRepository productRepository;

    public boolean processPayment(Orders orderRequest) {
        double totalPrice = calculateTotalPrice(orderRequest);
        // Simulate payment processing
        // For demonstration purposes, let's assume payment is successful if the total price is greater than 0
        return totalPrice > 0;
    }

    private double calculateTotalPrice(Orders orderRequest) {
        double totalPrice = 0.0;
        for (OrderItem orderItem : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + orderItem.getProduct().getId()));
            double itemPrice = product.getPrice() * orderItem.getQuantity();
            totalPrice += itemPrice;
        }
        return totalPrice;
    }
}
