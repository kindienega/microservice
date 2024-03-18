package et.com.gebeya.order_service.controller;

import et.com.gebeya.order_service.dto.requestDto.OrderPaymentInfo;
import et.com.gebeya.order_service.dto.requestDto.OrderRequestDto;
import et.com.gebeya.order_service.dto.responseDto.OrderResponseDto;
import et.com.gebeya.order_service.model.Orders;
import et.com.gebeya.order_service.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto request) {
        try {
            OrderResponseDto response = orderService.placeOrder(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while placing the order");
        }
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Integer orderId,
                                         @RequestParam("restaurantId") Integer restaurantId) {
        try {
            orderService.cancelOrder(orderId, restaurantId);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while cancelling the order");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrdersExceptCancelled() {
        try {
            List<Orders> orders = orderService.getAllOrdersExceptCancelled();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving orders");
        }
    }

    @GetMapping("/restaurant/get/{restaurantId}")
    public ResponseEntity<?> getOrdersByRestaurantId(@PathVariable("restaurantId") Integer restaurantId) {
        try {
            List<Orders> orders = orderService.getOrdersByRestaurantId(restaurantId);
            return ResponseEntity.ok(orders);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the given restaurant");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving orders");
        }
    }

    @PutMapping("/{orderId}/update-status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderId") Integer orderId) {
        try {
            String orderPaymentInfo = orderService.declineOrAcceptOrders(orderId);
            return ResponseEntity.ok(orderPaymentInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating order status");
        }
    }

    @PutMapping("/complete/{orderId}/order")
    public ResponseEntity<?> completeOrder(@PathVariable Integer orderId) {
        try {
            OrderPaymentInfo orderPaymentInfo = orderService.completeOrder(orderId);
            return ResponseEntity.ok(orderPaymentInfo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while completing the order");
        }
    }
}
