package com.example.carsaleproject.model;

public class Advertise {

    public String title,carOwner,carBrand,carPrice,carYear,carFuel,carGear,carKm,phoneNumber;

    public Advertise(String title, String carOwner, String carBrand, String carPrice, String carYear, String carFuel, String carGear,String carKm,String phoneNumber) {
        this.title = title;
        this.carOwner = carOwner;
        this.carBrand = carBrand;
        this.carPrice = carPrice;
        this.carYear = carYear;
        this.carFuel = carFuel;
        this.carGear = carGear;
        this.carKm = carKm;
        this.phoneNumber = phoneNumber;
    }

    public Advertise(String title, String carBrand, String carPrice) {
        this.title = title;
        this.carBrand = carBrand;
        this.carPrice = carPrice;
    }
}
