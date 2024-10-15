package com.enocaproject.enoca_project.controller;

import com.enocaproject.enoca_project.dto.CartResponseDTO;
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

    @PutMapping("/{cartId}/products/{productId}/quantity")
    public ResponseEntity<CartResponseDTO> updateProductQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        CartResponseDTO updatedCartResponse = cartService.updateProductQuantity(cartId, productId, quantity);
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
