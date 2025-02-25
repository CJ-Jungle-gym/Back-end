//package com.example.oliveback.domain.order;
//
//import com.example.oliveback.domain.product.Product;
//import com.example.oliveback.domain.user.Users;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "orders") // 예약어 방지
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false) // 연관관계 설정
//    private Product product;
//
//    @Column(nullable = false)
//    private int quantity;
//
//    @Column(nullable = false)
//    private int totalPrice;
//
//    @Column(nullable = false)
//    private LocalDateTime orderDate;
//}
package com.example.oliveback.domain.order;

import com.example.oliveback.domain.product.Product;
import com.example.oliveback.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // 예약어 방지
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // 연관관계 설정
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    // productName 필드 추가
    @Column(nullable = false)
    private String productName;

    // @PrePersist를 사용하여 productName을 자동으로 채워줌
    @PrePersist
    public void setProductName() {
        if (this.product != null) {
            this.productName = this.product.getName();
        }
    }
}

