package com.enocaproject.enoca_project.dao;

import com.enocaproject.enoca_project.entity.Cart;
import com.enocaproject.enoca_project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomer(Customer customer);
}
