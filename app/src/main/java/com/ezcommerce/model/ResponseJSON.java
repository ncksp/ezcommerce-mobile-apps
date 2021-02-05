package com.ezcommerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJSON {
    @SerializedName("statusCode")
    private int statusCode;
    @SerializedName("nim")
    private String nim;
    @SerializedName("nama")
    private String nama;
    @SerializedName("productId")
    private int productId;
    @SerializedName("credits")
    private String credits;
    @SerializedName("products")
    private List<Product> products;

    public ResponseJSON(int statusCode, String nim, String nama, int productId, String credits, List<Product>  products) {
        this.statusCode = statusCode;
        this.nim = nim;
        this.nama = nama;
        this.productId = productId;
        this.credits = credits;
        this.products = products;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public int getProductId() {
        return productId;
    }

    public String getCredits() {
        return credits;
    }

    public List<Product> getProducts() {
        return products;
    }
}
