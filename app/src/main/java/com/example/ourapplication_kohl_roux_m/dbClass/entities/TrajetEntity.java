package com.example.ourapplication_kohl_roux_m.dbClass.entities;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TrajetEntity implements Comparable {

    private String uid;
    private String name;
    private String date;
    private double kmTot;
    private double totRise;
    private double totDeep;
    private double gasolinTot;
    private double electricityTot;
    private String carRef;

    public TrajetEntity (){}

    public TrajetEntity(@NonNull String carId, String name, String date, double kmTot,
                        double totRise, double totDeep, double gasolinTot, double electricityTot) {
        this.carRef = carId;
        this.name = name;
        this.kmTot = kmTot;
        this.date = date;
        this.totRise = totRise;
        this.totDeep = totDeep;
        this.gasolinTot = gasolinTot;
        this.electricityTot = electricityTot;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getKmTot() {
        return kmTot;
    }

    public void setKmTot(double kmTot) {
        this.kmTot = kmTot;
    }

    public double getTotRise() {
        return totRise;
    }

    public void setTotRise(double totRise) {
        this.totRise = totRise;
    }

    public double getTotDeep() {
        return totDeep;
    }

    public void setTotDeep(double totDeep) {
        this.totDeep = totDeep;
    }

    public double getGasolinTot() {
        return gasolinTot;
    }

    public void setGasolinTot(double gasolinTot) {
        this.gasolinTot = gasolinTot;
    }

    public double getElectricityTot() {
        return electricityTot;
    }

    public void setElectricityTot(double electricityTot) {
        this.electricityTot = electricityTot;
    }

    @Exclude
    public String getCarRef() {
        return carRef;
    }

    public void setCarRef(String carRef) {
        this.carRef = carRef;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof TrajetEntity)) return false;
        TrajetEntity o = (TrajetEntity) obj;
        return o.getUid() == this.getUid() || o.getCarRef() == this.getCarRef();
    }

    @Override
    public String toString() {
        return uid + " / " + carRef + " / " + name + " / " + date;
    }

    @Override
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("date", date);
        result.put("kmTot", kmTot);
        result.put("totRise", totRise);
        result.put("totDeep", totDeep);
        result.put("gasolinTot", gasolinTot);
        result.put("electricityTot", electricityTot);

        return result;
    }


}

