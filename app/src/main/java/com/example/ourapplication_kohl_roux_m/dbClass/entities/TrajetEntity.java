package com.example.ourapplication_kohl_roux_m.dbClass.entities;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class TrajetEntity implements Comparable {

    private String  uid;
    private String namOfTrip;
    private String date;
    private String kmTot;
    private String totRise;
    private String totDeep;
    private String gasolinTot;
    private String electricityTot;
    private String carRef;

    public TrajetEntity (){}

    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNamOfTrip() {
        return namOfTrip;
    }

    public void setNamOfTrip(String namOfTrip) {
        this.namOfTrip = namOfTrip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKmTot() {
        return kmTot;
    }

    public void setKmTot(String kmTot) {
        this.kmTot = kmTot;
    }

    public String getTotRise() {
        return totRise;
    }

    public void setTotRise(String totRise) {
        this.totRise = totRise;
    }

    public String getTotDeep() {
        return totDeep;
    }

    public void setTotDeep(String totDeep) {
        this.totDeep = totDeep;
    }

    public String getGasolinTot() {
        return gasolinTot;
    }

    public void setGasolinTot(String gasolinTot) {
        this.gasolinTot = gasolinTot;
    }

    public String getElectricityTot() {
        return electricityTot;
    }

    public void setElectricityTot(String electricityTot) {
        this.electricityTot = electricityTot;
    }

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
        return o.getUid() == this.getUid() || o.getCarId() == this.getCarId();
    }

    @Override
    public String toString() {
        return uid + " / " + carId + " / " + name + " / " + date;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeLong(carId);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeDouble(kmTot);
        dest.writeDouble(totRise);
        dest.writeDouble(totDeep);
        dest.writeDouble(gasolinTot);
        dest.writeDouble(electricityTot);
    }


}

