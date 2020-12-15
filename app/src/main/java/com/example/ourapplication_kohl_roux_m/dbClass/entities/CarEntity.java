package com.example.ourapplication_kohl_roux_m.dbClass.entities;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CarEntity implements Comparable {

    public String uid;

    public String nickname;

    public String carTradeMark;

    public String model;

    public double consoFuel;

    public double batteryPower;

    public String wheelSize;

    public boolean carForTrip;

    public int picture;

    public CarEntity (){}

    public CarEntity(@NonNull String nickName, @NonNull String carTradeMark, @NonNull String model,
                     @NonNull double consoEssence, double batteryPower, String wheelSize, @NonNull boolean carForTrip, int picture) {
        this.nickname = nickName;
        this.carTradeMark = carTradeMark;
        this.model = model;
        this.consoFuel = consoEssence;
        this.batteryPower = batteryPower;
        this.wheelSize = wheelSize;
        this.carForTrip = carForTrip;
        this.picture = picture;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

   public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public String getCarTradeMark() {
        return carTradeMark;
    }

    public void setCarTradeMark(String carTradeMark) {
        this.carTradeMark = carTradeMark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getConsoFuel() {
        return consoFuel;
    }

    public void setConsoFuel(double consoFuel) {
        this.consoFuel = consoFuel;
    }

    public double getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(double batteryPower) {
        this.batteryPower = batteryPower;
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
        return uid + " / " + nickname;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickname", nickname);
        result.put("carTradeMark", carTradeMark);
        result.put("model", model);
        result.put("consoFuel", consoFuel);
        result.put("batteryCapacity", batteryPower);
        result.put("wheelSize", wheelSize);
        result.put("carForTrip", carForTrip);
        result.put("picture", picture);

        return result;
    }
}

