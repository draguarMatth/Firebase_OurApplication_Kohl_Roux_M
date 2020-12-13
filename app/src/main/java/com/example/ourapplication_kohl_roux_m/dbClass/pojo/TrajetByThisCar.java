package com.example.ourapplication_kohl_roux_m.dbClass.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity;

import java.util.List;

public class TrajetByThisCar {
    @Embedded
    public CarEntity carEntity;

    @Relation(parentColumn = "uid", entityColumn = "Voiture_id", entity = TrajetEntity.class)
    public List<TrajetEntity> trajets;
}
