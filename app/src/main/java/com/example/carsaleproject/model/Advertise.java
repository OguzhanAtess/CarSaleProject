package com.example.carsaleproject.model;

import java.io.Serializable;

public class Advertise implements Serializable {

    public String title, car_owner, car_brand, car_price, car_year, car_fuel, car_gear, car_km, phone_number;

    public Advertise(String title, String carOwner, String carBrand, String carPrice, String carYear, String carFuel, String carGear,String carKm,String phoneNumber) {
        this.title = title;
        this.car_owner = carOwner;
        this.car_brand = carBrand;
        this.car_price = carPrice;
        this.car_year = carYear;
        this.car_fuel = carFuel;
        this.car_gear = carGear;
        this.car_km = carKm;
        this.phone_number = phoneNumber;
    }

    public Advertise(String title, String carBrand, String carPrice) {
        this.title = title;
        this.car_brand = carBrand;
        this.car_price = carPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getCar_owner() {
        return car_owner;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getCar_price() {
        return car_price;
    }

    public String getCar_year() {
        return car_year;
    }

    public String getCar_fuel() {
        return car_fuel;
    }

    public String getCar_gear() {
        return car_gear;
    }

    public String getCar_km() {
        return car_km;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
