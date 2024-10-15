package com.enocaproject.enoca_project.controller;

import com.enocaproject.enoca_project.dto.ProductDTO; // Import the DTO
import com.enocaproject.enoca_project.entity.Product;
import com.enocaproject.enoca_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStockQuantity(productDTO.stockQuantity());

        Product createdProduct = productService.CreateProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.GetProduct(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setId(id);
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStockQuantity(productDTO.stockQuantity());

        Product updatedProduct = productService.UpdateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.DeleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
