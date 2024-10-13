package com.enocaproject.enoca_project.entity;

import com.enocaproject.enoca_project.general.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAST_ORDERS")
@Data
public class PastOrder extends BaseEntity {

    private Long customerId;
    private LocalDateTime orderDate;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

}
