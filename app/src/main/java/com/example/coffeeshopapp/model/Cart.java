package com.example.coffeeshopapp.model;

public class Cart {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private boolean isChecked;

    public Cart(int id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isChecked = true; // mặc định chọn
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isChecked() { return isChecked; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setChecked(boolean checked) { isChecked = checked; }
}