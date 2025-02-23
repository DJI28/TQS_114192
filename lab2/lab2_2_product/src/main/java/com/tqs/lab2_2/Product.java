package com.tqs.lab2_2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String image, description, title, category;
    private double price;
    private Rating rating;

    public Product() { }

    public Product(int id, String title, double price, String image, String description, String category, Rating rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.description = description;
        this.category = category;
        this.rating = rating;
    }
}