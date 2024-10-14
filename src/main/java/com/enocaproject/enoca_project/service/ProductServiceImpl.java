package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.ProductRepository;
import com.enocaproject.enoca_project.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Optional<Product> GetProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product;
    }
    @Override
    public Product CreateProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public Product UpdateProduct(Product product) {
        if (productRepository.existsById(product.getId())) {
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + product.getId());
        }
    }

    @Override
    public Product DeleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return product.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
