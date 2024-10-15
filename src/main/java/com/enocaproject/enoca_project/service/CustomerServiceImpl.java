package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dao.CustomerRepository;
import com.enocaproject.enoca_project.dto.CustomerDTO;
import com.enocaproject.enoca_project.entity.Cart;
import com.enocaproject.enoca_project.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Customer addCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.firstName());
        customer.setLastName(customerDTO.lastName());
        customer.setEmail(customerDTO.email());

        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.ZERO);

        customer.setCart(cart);

        return customerRepository.save(customer);
    }
}
