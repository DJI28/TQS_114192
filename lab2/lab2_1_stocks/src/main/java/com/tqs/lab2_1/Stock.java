package com.tqs.lab2_1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {
    private String label;
    private int quantity;

    public Stock(String label, int quantity) {
        this.label = label;
        this.quantity = quantity;
    }
}
