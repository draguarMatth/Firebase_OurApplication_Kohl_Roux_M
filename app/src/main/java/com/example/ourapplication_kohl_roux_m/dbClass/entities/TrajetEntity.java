package com.example.ourapplication_kohl_roux_m.dbClass.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "trajets", foreignKeys = @ForeignKey(entity = CarEntity.class, parentColumns = "uid", childColumns = "Voiture_id", onDelete = CASCADE))
public class TrajetEntity implements Comparable, Parcelable {

    public static final Parcelable.Creator<TrajetEntity> CREATOR = new Parcelable.Creator<TrajetEntity>() {
        @Override
        public TrajetEntity createFromParcel(Parcel source) {
            return new TrajetEntity(source);
        }

        @Override
        public TrajetEntity[] newArray(int size) {
            return new TrajetEntity[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    public long uid;
    @ColumnInfo(name = "Voiture_id")
    @NonNull
    public long carId;
    @ColumnInfo(name = "Nom_trajet")
    public String name;
    @ColumnInfo(name = "Date")
    @NonNull
    public String date;
    @ColumnInfo(name = "Distance")
    public double kmTot;
    @ColumnInfo(name = "Denivellation_positif")
    public double totRise;
    @ColumnInfo(name = "Denivellation_negatif")
    public double totDeep;
    @ColumnInfo(name = "Consommation_Essence")
    public double gasolinTot;

    @ColumnInfo(name = "Recharge_electrique")
    public double electricityTot;

    public TrajetEntity(@NonNull long carId, String name, @NonNull String date, double kmTot,
                        double totRise, double totDeep, double gasolinTot, double electricityTot) {
        this.carId = carId;
        this.name = name;
        this.kmTot = kmTot;
        this.date = date;
        this.totRise = totRise;
        this.totDeep = totDeep;
        this.gasolinTot = gasolinTot;
        this.electricityTot = electricityTot;
    }

    public TrajetEntity(Parcel in) {
        uid = in.readLong();
        carId = in.readLong();
        name = in.readString();
        date = in.readString();
        kmTot = in.readDouble();
        totRise = in.readDouble();
        totDeep = in.readDouble();
        gasolinTot = in.readDouble();
        electricityTot = in.readDouble();
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

