package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.entity.Cart;
import org.springframework.stereotype.Service;


@Service
public interface CartService {
    Cart GetCart(Long id);
    Cart UpdateCart(Cart cart);
    Cart EmptyCart(Long id);
    Cart AddProductToCart(Long cartId, Long productId, int quantity);
    Cart RemoveProductFromCart(Long cartId, Long productId);
}
