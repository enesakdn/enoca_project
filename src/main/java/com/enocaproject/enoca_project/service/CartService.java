package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dto.CartResponseDTO; // DTO sınıfını ekle
import com.enocaproject.enoca_project.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartResponseDTO GetCart(Long id);
    CartResponseDTO updateProductQuantity(Long cartId, Long productId, int quantity);
    CartResponseDTO EmptyCart(Long id);
    CartResponseDTO AddProductToCart(Long cartId, Long productId, int quantity);
    CartResponseDTO RemoveProductFromCart(Long cartId, Long productId);
}
