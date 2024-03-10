package et.com.gebeya.order_service.controller;

import et.com.gebeya.order_service.dto.requestDto.OrderPaymentInfo;
import et.com.gebeya.order_service.dto.requestDto.OrderRequestDto;
import et.com.gebeya.order_service.dto.responseDto.OrderResponseDto;
import et.com.gebeya.order_service.model.Orders;
import et.com.gebeya.order_service.service.OrderService;
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
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto request) {
        try {
            OrderResponseDto response = orderService.placeOrder(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Integer orderId,
                                              @RequestParam("restaurantId") Integer restaurantId) {
        orderService.cancelOrder(orderId, restaurantId);
        return ResponseEntity.ok("Order cancelled successfully");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrdersExceptCancelled() {

            List<Orders> orders = orderService.getAllOrdersExceptCancelled();
            return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Orders>> getOrdersByRestaurantId(@PathVariable("restaurantId")Integer restaurantId) {
        List<Orders> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderPaymentInfo> completeOrder(@PathVariable("orderId") Integer orderId) {
        OrderPaymentInfo orderPaymentInfo = orderService.updateOrderStatusToCompleted(orderId);
        return ResponseEntity.ok(orderPaymentInfo);
    }
}
