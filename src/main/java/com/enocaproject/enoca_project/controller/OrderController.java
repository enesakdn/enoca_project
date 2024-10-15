package com.enocaproject.enoca_project.controller;

import com.enocaproject.enoca_project.dto.OrderResponseDTO;
import com.enocaproject.enoca_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place/{customerId}")
    public ResponseEntity<OrderResponseDTO> placeOrder(@PathVariable Long customerId) {
        OrderResponseDTO orderResponseDTO = orderService.PlaceOrder(customerId);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.GetOrderForCode(id);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        List<OrderResponseDTO> orders = orderService.GetAllOrdersForCustomer(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
