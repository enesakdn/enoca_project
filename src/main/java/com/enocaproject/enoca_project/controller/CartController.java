package com.enocaproject.enoca_project.controller;

import com.enocaproject.enoca_project.dto.CartResponseDTO; // DTO sınıfını ekle
import com.enocaproject.enoca_project.entity.Cart;
import com.enocaproject.enoca_project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Long id) {
        CartResponseDTO cartResponse = cartService.GetCart(id);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        cart.setId(id);
        CartResponseDTO updatedCartResponse = cartService.UpdateCart(cart);
        return new ResponseEntity<>(updatedCartResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/empty")
    public ResponseEntity<CartResponseDTO> emptyCart(@PathVariable Long id) {
        CartResponseDTO updatedCartResponse = cartService.EmptyCart(id);
        return new ResponseEntity<>(updatedCartResponse, HttpStatus.OK);
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartResponseDTO> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        CartResponseDTO updatedCartResponse = cartService.AddProductToCart(cartId, productId, quantity);
        return new ResponseEntity<>(updatedCartResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        CartResponseDTO updatedCartResponse = cartService.RemoveProductFromCart(cartId, productId);
        return new ResponseEntity<>(updatedCartResponse, HttpStatus.OK);
    }
}
