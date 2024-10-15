    package com.enocaproject.enoca_project.dto;

    import java.math.BigDecimal;

    public record CartItemDTO(Long productId,
                              String productName,
                              int quantity,
                              BigDecimal price) {
    }
