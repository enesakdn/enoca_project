package com.enocaproject.enoca_project.dao;

import com.enocaproject.enoca_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
