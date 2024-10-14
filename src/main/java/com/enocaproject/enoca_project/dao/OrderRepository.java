package com.enocaproject.enoca_project.dao;

import com.enocaproject.enoca_project.entity.Customer;
import com.enocaproject.enoca_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(Customer customer);
}
