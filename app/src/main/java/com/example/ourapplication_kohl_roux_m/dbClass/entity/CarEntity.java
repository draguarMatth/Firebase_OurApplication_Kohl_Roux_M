package com.example.ourapplication_kohl_roux_m.dbClass.entity;


import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CarEntity {
    public long uid;
    public String nickName;
    public String carTradeMark;
    public String model;
    public double consoEssence;
    public double batteryPower;
    public String wheelSize;
    public boolean carForTrip;
    public int picture;

    public CarEntity(){

    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
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
        if (!(obj instanceof com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity)) return false;
        com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity o = (com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity) obj;
        return o.getUid() == this.getUid();
    }

    @Override
    public String toString() {
        return uid + " / " + nickName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", nickName);
        result.put("model", model);
        result.put("consoEssence", consoEssence);
        result.put("batteryPower", batteryPower);
        result.put("wheelSize", wheelSize);
        result.put("carForTrip", carForTrip);

        return result;
    }
}
