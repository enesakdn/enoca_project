package com.enocaproject.enoca_project.service;

import com.enocaproject.enoca_project.dto.OrderResponseDTO;
import com.enocaproject.enoca_project.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDTO PlaceOrder(Long customerId);
    OrderResponseDTO GetOrderForCode(Long id);
    List<OrderResponseDTO> GetAllOrdersForCustomer(Long id);

}
