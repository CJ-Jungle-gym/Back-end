package com.example.oliveback.controller.product;

import com.example.oliveback.dto.product.ProductRequest;
import com.example.oliveback.dto.product.ProductResponse;
import com.example.oliveback.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ✅ Page 반환으로 변경하여 페이징 처리 지원
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category) {
        // productService.getProducts는 이제 Page<ProductResponse>를 반환
        return ResponseEntity.ok(productService.getProducts(page, size, category));
    }


    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ✅ 상품 등록 시 stock 포함하도록 수정
    @PostMapping("/{username}")
    public ResponseEntity<ProductResponse> createProduct(
            @PathVariable String username,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(username, request));
    }
}
