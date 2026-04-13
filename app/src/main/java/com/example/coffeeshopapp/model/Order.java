package com.example.coffeeshopapp.model;

public class Order {
    private int id;
    private String name, phone, address;
    private int total;

    public Order(int id, String name, String phone, String address, int total) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }
}