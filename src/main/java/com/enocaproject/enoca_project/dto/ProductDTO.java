package com.enocaproject.enoca_project.dto;

import java.math.BigDecimal;

public record ProductDTO(String name, BigDecimal price, Integer stockQuantity) {}
