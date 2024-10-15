package com.enocaproject.enoca_project.controller;

import com.enocaproject.enoca_project.dto.CustomerDTO;
import com.enocaproject.enoca_project.entity.Customer;
import com.enocaproject.enoca_project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDTO customerDTO) {

            Customer savedCustomer = customerService.addCustomer(customerDTO);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);

    }
}
