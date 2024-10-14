package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.CartRepository;
import com.enocaproject.enoca_project.dao.CustomerRepository;
import com.enocaproject.enoca_project.dao.OrderRepository;
import com.enocaproject.enoca_project.entity.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Override
    @Transactional
    public Order PlaceOrder(Long customerId) {
        // Müşteriyi ID ile bul
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId)
        );

        // Müşterinin sepetini bul
        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for customer with ID: " + customerId)
        );

        // Sepet boşsa hata döndür
        if (cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The cart is empty, cannot place an order.");
        }

        // Yeni sipariş oluşturma
        Order order = new Order();
        order.setCustomer(customer);  // Siparişe müşteri bilgisini ekle

        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        // Sepetteki ürünleri siparişe ekle
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            // Ürün bilgilerini siparişe ekle
            order.setProduct_name(product.getName());
            order.setProduct_price(product.getPrice());
            order.setPiece(quantity);

            // Toplam sipariş fiyatını hesapla
            totalOrderPrice = totalOrderPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            // Stok güncelle
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.CreateProduct(product);
        }

        // Siparişi kaydet
        orderRepository.save(order);

        // Sepeti boşalt
         cartService.EmptyCart(cart.getId());

        // Sipariş bilgilerini döndür
        return order;
    }
    @Override
    public Order GetOrderForCode(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    @Override
    public List<Order> GetAllOrdersForCustomer(Long customerId) {
        // Müşteriyi ID ile bulma. Eğer bulunamazsa hata fırlatıyoruz.
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId)
        );

        // Müşteriye ait tüm siparişleri döndürüyoruz
        return orderRepository.findAllByCustomer(customer);
    }
}
