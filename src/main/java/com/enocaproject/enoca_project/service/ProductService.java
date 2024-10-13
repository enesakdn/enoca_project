package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.entity.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> GetProduct(Long id);
    Product CreateProduct(Product product);
    Product UpdateProduct(Product product);
    Product DeleteProduct(Long id);
}
