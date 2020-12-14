package com.example.ourapplication_kohl_roux_m.dbClass.dao;
/*
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;

import java.util.List;

@Dao
public interface DbTrajetDao {

    @Query("SELECT * FROM trajets WHERE Voiture_id = (:carId) AND Date LIKE (:date) ")
    LiveData<TrajetEntity> getOneByCar(long carId, String date);

    @Query("SELECT * FROM trajets WHERE Nom_trajet LIKE (:name)")
    LiveData<List<TrajetEntity>> getByName(String name);

    @Query("SELECT * FROM trajets WHERE Voiture_id = (:carId)")
    LiveData<List<TrajetEntity>> getByCarId(long carId);

    @Query("SELECT * FROM trajets")
    LiveData<List<TrajetEntity>> getAll();

    @Query("SELECT * FROM trajets")
    List<TrajetEntity> getAllTrajets();

    @Query("SELECT * FROM trajets WHERE uid = (:uid) Limit 1")
    LiveData<TrajetEntity> getById(long uid);

    @Query("SELECT * FROM trajets WHERE uid IN (:trajetIds)")
    List<TrajetEntity> loadAllByIds(long[] trajetIds);

    @Query("SELECT * FROM trajets WHERE Nom_trajet LIKE (:name) LIMIT 1")
    TrajetEntity findByName(String name);

    @Query("SELECT * FROM trajets WHERE Date LIKE (:date) LIMIT 1")
    TrajetEntity findByDate(String date);

    @Query("SELECT * FROM trajets WHERE Distance LIKE :km LIMIT 1")
    TrajetEntity findByKilometers(double km);

    @Query("SELECT * FROM trajets WHERE Denivellation_positif LIKE :rise LIMIT 1")
    TrajetEntity findByRising(double rise);

    @Query("SELECT * FROM trajets WHERE Denivellation_positif <= :rise LIMIT 1")
    TrajetEntity findByIntervalRising(double rise);

    @Query("SELECT * FROM trajets WHERE Denivellation_negatif LIKE :deep LIMIT 1")
    TrajetEntity findByDeep(double deep);

    @Query("SELECT * FROM trajets WHERE Denivellation_negatif LIKE :deep LIMIT 1")
    TrajetEntity findByIntevalDeep(double deep);

    @Insert
    void insertAll(TrajetEntity... trajets) throws SQLiteConstraintException;

    @Insert
    long insert(TrajetEntity trajetEntity) throws SQLiteConstraintException;

    @Update
    void update(TrajetEntity trajet);

    @Delete
    void delete(TrajetEntity trajetEntity);

    @Query("DELETE FROM trajets")
    void deleteAll();

}
*/