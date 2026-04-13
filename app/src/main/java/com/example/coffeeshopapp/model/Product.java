package com.example.coffeeshopapp.model;

public class Product {
    private int id;
    private String name;
    private int price;
    private String category;
    private boolean isHot;

    public Product(int id, String name, int price, String category, boolean isHot) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.isHot = isHot;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isHot() { return isHot; }
}