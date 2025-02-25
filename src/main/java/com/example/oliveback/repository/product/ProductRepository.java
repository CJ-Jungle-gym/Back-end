package com.example.oliveback.repository.product;

import com.example.oliveback.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 카테고리별 상품 조회 (페이징)
    Page<Product> findByCategory(String category, Pageable pageable);

    // 특정 카테고리의 상품 목록 조회 (기존 코드 유지)
    List<Product> findByCategory(String skincare);

    // ✅ Pageable 지원하도록 수정
//    Page<Product> findByCategoryAndStockGreaterThan(String category, int stock, Pageable pageable);

    Page<Product> findByStockGreaterThan(int stock, Pageable pageable);

    // ✅ 카테고리별 재고가 있는 상품 조회 (리스트 반환)
    List<Product> findByCategoryAndStockGreaterThan(String category, int stock);

    // ✅ 카테고리별 재고가 있는 상품 조회 (페이징 지원)
    Page<Product> findByCategoryAndStockGreaterThan(String category, int stock, Pageable pageable);
}


