    package com.enocaproject.enoca_project.service;

    import com.enocaproject.enoca_project.dao.CartRepository;
    import com.enocaproject.enoca_project.dao.ProductRepository;
    import com.enocaproject.enoca_project.dto.CartItemDTO;
    import com.enocaproject.enoca_project.dto.CustomerDTO;
    import com.enocaproject.enoca_project.entity.Cart;
    import com.enocaproject.enoca_project.entity.CartItem;
    import com.enocaproject.enoca_project.entity.Product;
    import com.enocaproject.enoca_project.dto.CartResponseDTO;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.http.HttpStatus;
    import java.math.BigDecimal;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class CartServiceImpl implements CartService {

        private final CartRepository cartRepository;
        private final ProductRepository productRepository;

        @Override
        public CartResponseDTO GetCart(Long id) {
            Cart cart = cartRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + id)
            );
            return toCartResponseDTO(cart);
        }

        @Override
        @Transactional
        public CartResponseDTO updateProductQuantity(Long cartId, Long productId, int quantity) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + cartId)
            );

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in the cart with ID: " + productId));

            if (cartItem.getProduct().getStockQuantity() < quantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for product: " + cartItem.getProduct().getName());
            }

            cartItem.setQuantity(quantity);

            BigDecimal newTotalPrice = cart.getCartItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cart.setTotalPrice(newTotalPrice);

            Cart updatedCart = cartRepository.save(cart);

            return toCartResponseDTO(updatedCart);
        }


        @Override
        @Transactional
        public CartResponseDTO EmptyCart(Long id) {
            Cart cart = cartRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + id)
            );


            List<CartItem> cartItems = cart.getCartItems();


            for (CartItem cartItem : cartItems) {
                cartItem.setCart(null);

            }
            cartItems.clear();

            cart.setTotalPrice(BigDecimal.ZERO);
            Cart updatedCart = cartRepository.save(cart);
            return toCartResponseDTO(updatedCart);
        }

        @Override
        @Transactional
        public CartResponseDTO AddProductToCart(Long cartId, Long productId, int quantity) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + cartId)
            );
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productId)
            );

            if (product.getStockQuantity() < quantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock for product: " + product.getName());
            }

            CartItem existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (existingCartItem != null) {
                int newQuantity = existingCartItem.getQuantity() + quantity;

                if (product.getStockQuantity() < newQuantity) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock to add more of product: " + product.getName());
                }

                existingCartItem.setQuantity(newQuantity);
            } else {
                // Ürün sepette yoksa yeni CartItem oluştur ve ekle
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setCart(cart);
                newCartItem.setQuantity(quantity);
                cart.getCartItems().add(newCartItem);
            }

            // Toplam fiyatı güncelle
            cart.setTotalPrice(cart.getCartItems().stream()
                    .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

            productRepository.save(product);
            Cart updatedCart = cartRepository.save(cart);

            return toCartResponseDTO(updatedCart);
        }


        @Override
        @Transactional
        public CartResponseDTO RemoveProductFromCart(Long cartId, Long productId) {
            Cart cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + cartId)
            );
            CartItem cartItemToRemove = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in the cart with ID: " + productId));

            Product product = cartItemToRemove.getProduct();
            product.setStockQuantity(product.getStockQuantity() + cartItemToRemove.getQuantity());
            productRepository.save(product);

            cart.getCartItems().remove(cartItemToRemove);
            cart.setTotalPrice(cart.getTotalPrice().subtract(product.getPrice().multiply(BigDecimal.valueOf(cartItemToRemove.getQuantity()))));

            Cart updatedCart = cartRepository.save(cart);
            return toCartResponseDTO(updatedCart);
        }

        private CartResponseDTO toCartResponseDTO(Cart cart) {
            CustomerDTO customerDTO = new CustomerDTO(
                    cart.getCustomer().getFirstName(),
                    cart.getCustomer().getLastName(),
                    cart.getCustomer().getEmail()
            );

            List<CartItemDTO> cartItems = cart.getCartItems().stream()
                    .map(item -> new CartItemDTO(
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getProduct().getPrice()
                    ))
                    .toList();

            double totalPrice = cart.getTotalPrice().doubleValue();

            return new CartResponseDTO(cart.getId(), customerDTO, cartItems, totalPrice);
        }

    }
