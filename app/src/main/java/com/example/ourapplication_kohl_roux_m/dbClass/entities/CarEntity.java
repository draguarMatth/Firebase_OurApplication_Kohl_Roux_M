package com.example.ourapplication_kohl_roux_m.dbClass.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class CarEntity implements Comparable {

    @ColumnInfo(name = "uid")
    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "Nickname")
    public String nickName;

    @ColumnInfo(name = "Marque")
    @NonNull
    public String carTradeMark;

    @ColumnInfo(name = "model")
    @NonNull
    public String model;

    @ColumnInfo(name = "conso_essence")
    @NonNull
    public double consoEssence;

    @ColumnInfo(name = "charge_batterie")
    public double batteryPower;

    @ColumnInfo(name = "taille_jante")
    public String wheelSize;

    @ColumnInfo(name = "active")
    @NonNull
    public boolean carForTrip;

    @ColumnInfo(name = "picture")
    public int picture;

    public CarEntity(@NonNull String nickName, @NonNull String carTradeMark, @NonNull String model,
                     @NonNull double consoEssence, double batteryPower, String wheelSize, @NonNull boolean carForTrip, int picture) {
        this.nickName = nickName;
        this.carTradeMark = carTradeMark;
        this.model = model;
        this.consoEssence = consoEssence;
        this.batteryPower = batteryPower;
        this.wheelSize = wheelSize;
        this.carForTrip = carForTrip;
        this.picture = picture;
    }

    public long getUid() {
        return uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCarTradeMark() {
        return carTradeMark;
    }

    public String getModel() {
        return model;
    }

    public double getConsoEssence() {
        return consoEssence;
    }

    public double getBatteryPower() {
        return batteryPower;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int pathRDrawable) {
        this.picture = pathRDrawable;
    }

    public String getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(String wheelSize) {
        this.wheelSize = wheelSize;
    }

    public boolean isCarForTrip() {
        return carForTrip;
    }

    public void setCarForTrip(boolean carForTrip) {
        this.carForTrip = carForTrip;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CarEntity)) return false;
        CarEntity o = (CarEntity) obj;
        return o.getUid() == this.getUid();
    }

    @Override
    public String toString() {
        return uid + " / " + nickName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

}
