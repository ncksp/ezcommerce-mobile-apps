package com.ezcommerce.model;

public class Cart {
    private int id;
    private String name;
    private float price;
    private String author;
    private String img;
    private int qty;

    public Cart(int id, String name, float price, String author, String img, int qty) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.img = img;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getImg() {
        return img;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
