package com.tqs.lab6_4_5.lab6_4_5_cars.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(name = "maker")
    @NotNull
    private String maker;

    @Column(name = "model")
    private String model;

    @Column(name = "segment")
    private Character segment;

    @Column(name = "motortype")
    private String enginetype;

    @Column(name = "transmission")
    private String transmission;

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car(Long carId, String maker, String model) {
        this.carId = carId;
        this.maker = maker;
        this.model = model;
    }

    public Car(String maker, String model, Character segment, String enginetype, String transmission) {
        this.maker = maker;
        this.model = model;
        this.segment = segment;
        this.enginetype = enginetype;
        this.transmission = transmission;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Long getCarId() {
        return carId;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public Character getSegment() {
        return segment;
    }

    public String getEnginetype() {
        return enginetype;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSegment(Character segment) {
        this.segment = segment;
    }

    public void setEnginetype(String enginetype) {
        this.enginetype = enginetype;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", segment=" + segment +
                ", motortype='" + enginetype + '\'' +
                ", transmission='" + transmission + '\'' +
                '}';
    }
}
