package com.enocaproject.enoca_project.entity;

import com.enocaproject.enoca_project.general.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product extends BaseEntity {

    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;

}
