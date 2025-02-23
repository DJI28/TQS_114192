package com.tqs.lab2_2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {
    private double rate;
    private int count;

    public Rating() {
    }

    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }
}