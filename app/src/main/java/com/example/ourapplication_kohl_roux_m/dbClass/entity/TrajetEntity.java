package com.example.ourapplication_kohl_roux_m.dbClass.entity;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TrajetEntity {

    public long uid;
    public String name;
    public long carId;
    public double kmTot;
    public String date;
    public double totRise;
    public double totDeep;
    public double gasolinTot;
    public double electricityTot;

    public TrajetEntity(){

    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public double getKmTot() {
        return kmTot;
    }

    public void setKmTot(double kmTot) {
        this.kmTot = kmTot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
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

    public void addGasolin(double gasolinTot) {
        this.gasolinTot += gasolinTot;
    }

    public void removeGasolin(double gasolinTot) {
        this.gasolinTot -= gasolinTot;
    }

    public double getElectricityTot() {
        return electricityTot;
    }

    public void setElectricityTot(double electricityTot) {
        this.electricityTot = electricityTot;
    }

    public void addElectricity(double electricityTot) {
        this.electricityTot += electricityTot;
    }

    public void removeElectricity(double electricityTot) {
        this.electricityTot -= electricityTot;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity)) return false;
        com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity o = (com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity) obj;
        return o.getUid() == this.getUid() || o.getCarId() == this.getCarId();
    }

    @Override
    public String toString() {
        return uid + " / " + carId + " / " + name + " / " + date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("kmTot", kmTot);
        result.put("date", date);
        result.put("totRise", totRise);
        result.put("totDeep", totDeep);
        result.put("gasolinTot", gasolinTot);
        result.put("electricityTot", electricityTot);

        return result;
    }
}
