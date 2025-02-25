package com.example.oliveback.dto.order;

import com.example.oliveback.domain.order.Order;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private String username;
    private Long productId;
    private String productName;
    private int quantity;
    private int totalPrice;
    private LocalDateTime orderDate;

    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .productId(order.getProduct().getId()) // productId 추가
                .productName(order.getProduct().getName()) // 상품명 변경
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .build();
    }
}
