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
    // ===== GETTER =====
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getTotal() {
        return total;
    }
}