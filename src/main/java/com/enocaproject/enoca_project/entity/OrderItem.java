package com.enocaproject.enoca_project.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;

}
