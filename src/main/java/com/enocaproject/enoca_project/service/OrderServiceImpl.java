package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.OrderRepository; // Siparişleri yönetmek için bir DAO oluşturmalısın
import com.enocaproject.enoca_project.entity.Order;
import com.enocaproject.enoca_project.entity.Product;
import com.enocaproject.enoca_project.general.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Order PlaceOrder(Long productId, Long customerId) {

        Product product = productService.GetProduct(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        if (product.getStockQuantity() <= 0) {
            throw new ProductNotFoundException("Ürün stokta yok");
        }

        Order order = new Order();
        order.setProduct_id(productId);
        order.setCustomerId(customerId);
        return orderRepository.save(order);
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
        return orderRepository.findAllByCustomerId(customerId);
    }
}
