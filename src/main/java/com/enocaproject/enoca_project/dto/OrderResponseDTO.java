package com.enocaproject.enoca_project.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        CustomerDTO customer,
        List<OrderItemDTO> orderItems
) {
    public record OrderItemDTO(
            String productName,
            BigDecimal productPrice,
            Integer quantity
    ) {}
}
