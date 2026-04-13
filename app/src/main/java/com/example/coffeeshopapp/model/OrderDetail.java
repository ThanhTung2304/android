package com.example.coffeeshopapp.model;

public class OrderDetail {
    private int orderId;
    private String productName;
    private int price;
    private int quantity;

    public OrderDetail(int orderId, String productName, int price, int quantity) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}