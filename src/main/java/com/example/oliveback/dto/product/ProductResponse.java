package com.example.oliveback.dto.product;

import com.example.oliveback.domain.product.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String category;
    private String description;
    private String imageUrl;
    private int stock; // stock 추가

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .stock(product.getStock()) // stock 반영
                .build();
    }
}
