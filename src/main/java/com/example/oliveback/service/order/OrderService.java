package com.example.oliveback.service.order;

import com.example.oliveback.domain.order.Order;
import com.example.oliveback.domain.product.Product;
import com.example.oliveback.domain.user.Role;
import com.example.oliveback.domain.user.Users;
import com.example.oliveback.dto.order.OrderRequest;
import com.example.oliveback.dto.order.OrderResponse;
import com.example.oliveback.exception.CustomException;
import com.example.oliveback.repository.order.OrderRepository;
import com.example.oliveback.repository.product.ProductRepository;
import com.example.oliveback.repository.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(String username, OrderRequest request) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(CustomException.UserNotFoundException::new);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(CustomException.ProductNotFoundException::new);

        if (request.getQuantity() < 1) {
            throw new CustomException.InvalidOrderException();
        }

        if (product.getStock() < request.getQuantity()) {
            try {
                throw new CustomException.InsufficientStockException();
            } catch (CustomException.InsufficientStockException e) {
                throw new RuntimeException(e);
            }
        }

        // 주문 생성 및 저장
        Order newOrder = Order.builder()
                .user(user)
                .product(product)
                .quantity(request.getQuantity())
                .totalPrice(product.getPrice() * request.getQuantity()) // 자동 계산
                .orderDate(LocalDateTime.now())
                .build();

        orderRepository.save(newOrder);

        // 재고 차감
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        return OrderResponse.fromEntity(newOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(CustomException.UserNotFoundException::new);

        return orderRepository.findByUser(user)
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(CustomException.UserNotFoundException::new);

        if (user.getRole() != Role.ADMIN) {
            throw new CustomException.AccessDeniedException();
        }

        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }
}

