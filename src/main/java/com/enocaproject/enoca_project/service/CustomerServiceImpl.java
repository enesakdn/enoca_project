package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.CustomerRepository;
import com.enocaproject.enoca_project.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerServiceImpl implements CustomerService{

@Autowired
private CustomerRepository customerRepository;
    @Override
    public Customer addCustomer(Customer customer) {
       return  customerRepository.save(customer);
    }
}
