package com.enocaproject.enoca_project.entity;

import com.enocaproject.enoca_project.general.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="CUSTOMER")
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
