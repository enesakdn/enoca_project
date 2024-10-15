package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dto.CustomerDTO;
import com.enocaproject.enoca_project.entity.Customer;
import org.springframework.stereotype.Service;


public interface CustomerService {

    Customer addCustomer(CustomerDTO customerDTO);

}
