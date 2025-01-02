package ru.fast.bills.dao.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String house;
}
