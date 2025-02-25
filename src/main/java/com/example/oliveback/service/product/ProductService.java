package com.example.oliveback.service.product;

import com.example.oliveback.domain.product.Product;
import com.example.oliveback.domain.user.Role;
import com.example.oliveback.domain.user.Users;
import com.example.oliveback.dto.product.ProductRequest;
import com.example.oliveback.dto.product.ProductResponse;
import com.example.oliveback.exception.CustomException;
import com.example.oliveback.repository.product.ProductRepository;
import com.example.oliveback.repository.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(int page, int size, String category) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Product> products;

        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategoryAndStockGreaterThan(category, 0, pageable);
        } else {
            products = productRepository.findByStockGreaterThan(0, pageable);
        }

        // Page<Product>를 Page<ProductResponse>로 변환
        return products.map(ProductResponse::fromEntity);
    }



    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(CustomException.ProductNotFoundException::new);
        return ProductResponse.fromEntity(product);
    }

    @Transactional
    public ProductResponse createProduct(String username, ProductRequest request) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(CustomException.UserNotFoundException::new);

        if (user.getRole() != Role.ADMIN) {
            throw new CustomException.AccessDeniedException();
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .stock(request.getStock()) // stock 추가
                .build();

        productRepository.save(product);
        return ProductResponse.fromEntity(product);
    }

    // 카테고리별 상품 조회 (재고가 있는 상품만 반환)
    public List<ProductResponse> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryAndStockGreaterThan(category, 0);

        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getStock() // stock 추가
                ))
                .collect(Collectors.toList());
    }
}
