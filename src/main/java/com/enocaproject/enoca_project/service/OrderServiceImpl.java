package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.CartRepository;
import com.enocaproject.enoca_project.dao.CustomerRepository;
import com.enocaproject.enoca_project.dao.OrderRepository;
import com.enocaproject.enoca_project.dto.CustomerDTO;
import com.enocaproject.enoca_project.dto.OrderResponseDTO;
import com.enocaproject.enoca_project.entity.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public OrderResponseDTO PlaceOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId)
        );

        Cart cart = cartRepository.findByCustomer(customer).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for customer with ID: " + customerId)
        );

        if (cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The cart is empty, cannot place an order.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            OrderItem orderItem = new OrderItem(product.getName(), product.getPrice(), quantity);
            order.getOrderItems().add(orderItem);

            totalOrderPrice = totalOrderPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.CreateProduct(product);
        }

        orderRepository.save(order);
        cartService.EmptyCart(cart.getId());

        return toOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO GetOrderForCode(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with ID: " + id)
        );
        return toOrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> GetAllOrdersForCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId)
        );


        return orderRepository.findAllByCustomer(customer).stream()
                .map(this::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO toOrderResponseDTO(Order order) {
        List<OrderResponseDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> new OrderResponseDTO.OrderItemDTO(item.getProductName(), item.getProductPrice(), item.getQuantity()))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                new CustomerDTO(order.getCustomer().getFirstName(),
                        order.getCustomer().getLastName(),
                        order.getCustomer().getEmail()),
                itemDTOs,
                order.getOrderDate()
        );
    }


}
