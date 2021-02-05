package com.ezcommerce.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private float price;
    @SerializedName("author")
    private String author;
    @SerializedName("type")
    private String type;
    @SerializedName("img")
    private String img;
    @SerializedName("category")
    private String category;

    public Product(int id, String name, String description, float price, String author, String type, String img, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.type = type;
        this.img = img;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public String getCategory() {
        return category;
    }
}
