package com.ebookfrenzy.carflowingproject.Model;

import java.io.Serializable;

public class Car {
    private String carPlate;
    private String name;

    public Car(String carPlate, String name) {
        this.carPlate = carPlate;
        this.name = name;
    }

    public Car() {
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
