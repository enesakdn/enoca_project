package com.enocaproject.enoca_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ORDER")
@Data
public class Order {

    private long product_id;
    private Long customerId;
}
