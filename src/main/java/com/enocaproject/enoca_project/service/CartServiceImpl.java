package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.CartRepository;
import com.enocaproject.enoca_project.dao.ProductRepository;
import com.enocaproject.enoca_project.entity.Cart;
import com.enocaproject.enoca_project.entity.CartItem;
import com.enocaproject.enoca_project.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart GetCart(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + id)
        );
    }

    @Override
    @Transactional
    public Cart UpdateCart(Cart cart) {
        if (cart == null || cart.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cart data");
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart EmptyCart(Long id) {
        Cart cart = GetCart(id);
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart AddProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = GetCart(cartId);


        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productId)
        );

        // Ürünün stok miktarını kontrol etmek için koşul. Stokta yoksa hata fırlatacak.
        if (product.getStockQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for product: " + product.getName());
        }

        // Yeni bir CartItem (sepet öğesi) oluşturuyoruz ve ilgili ürünü ve sepeti bu öğeye atıyoruz.
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);

        cart.getCartItems().add(cartItem);

        // Sepetin toplam fiyatını güncellendi. Mevcut toplam fiyata eklenen ürünlerin fiyatını çarpı miktar eklendi.
        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));

        product.setStockQuantity(product.getStockQuantity() - quantity);

        // Üründeki stok güncellemesini kaydediyoruz.
        productRepository.save(product);

        return cartRepository.save(cart);
    }


    @Override
    @Transactional
    public Cart RemoveProductFromCart(Long cartId, Long productId) {
        Cart cart = GetCart(cartId);
        CartItem cartItemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in the cart with ID: " + productId));

        Product product = cartItemToRemove.getProduct();
        product.setStockQuantity(product.getStockQuantity() + cartItemToRemove.getQuantity());
        productRepository.save(product);

        cart.getCartItems().remove(cartItemToRemove);
        cart.setTotalPrice(cart.getTotalPrice().subtract(product.getPrice().multiply(BigDecimal.valueOf(cartItemToRemove.getQuantity()))));

        return cartRepository.save(cart);
    }
}
