package com.enocaproject.enoca_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name="CARD")
public class Cart {

    private long customer_id;
    private long order_id;
    private BigDecimal total_price;
}
