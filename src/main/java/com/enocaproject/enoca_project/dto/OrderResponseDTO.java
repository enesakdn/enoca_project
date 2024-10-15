package com.enocaproject.enoca_project.dto;

import java.math.BigDecimal;

public record OrderResponseDTO(CustomerDTO customer, String product_name, BigDecimal product_price,Integer piece) {
}
