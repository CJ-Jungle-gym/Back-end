package com.example.oliveback.controller.order;

import com.example.oliveback.dto.order.OrderRequest;
import com.example.oliveback.dto.order.OrderResponse;
import com.example.oliveback.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ✅ 상품 ID 기반으로 주문 생성하도록 수정
    @PostMapping("/{username}")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable String username,
            @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(username, request));
    }

    // 특정 사용자의 주문 조회
    @GetMapping("/{username}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getUserOrders(username));
    }

    // 관리자용 전체 주문 목록 조회
    @GetMapping("/{username}/all")
    public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getAllOrders(username));
    }
}

