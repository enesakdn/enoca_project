package com.enocaproject.enoca_project.dao;

import com.enocaproject.enoca_project.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
