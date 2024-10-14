package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.entity.Order;

import java.util.List;

public interface OrderService {

    Order PlaceOrder( Long customerId);
    Order GetOrderForCode(Long id);
    List<Order> GetAllOrdersForCustomer(Long id);

}
