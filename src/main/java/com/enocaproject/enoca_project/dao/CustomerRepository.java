package com.enocaproject.enoca_project.dao;

import com.enocaproject.enoca_project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
