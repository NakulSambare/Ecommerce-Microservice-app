package com.ecommerce.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Data
@NoArgsConstructor
@Entity
@Table(name="user_address")
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
