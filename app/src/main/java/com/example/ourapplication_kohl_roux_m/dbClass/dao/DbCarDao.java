package com.example.ourapplication_kohl_roux_m.dbClass.dao;

import android.database.sqlite.SQLiteConstraintException;
/*
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;

import java.util.List;

@Dao
public interface DbCarDao {

    @Query("SELECT * FROM cars WHERE uid IN (:carIds)")
    LiveData<CarEntity> getById(long[] carIds);

    @Query("SELECT * FROM cars")
    LiveData<List<CarEntity>> getAll();

    @Query("SELECT * FROM cars WHERE active = 1 ")
    LiveData<List<CarEntity>> getByActivity();

    @Query("SELECT * FROM cars WHERE uid = (:uid) ")
    LiveData<CarEntity> getCar(long uid);

    @Query("SELECT * FROM cars WHERE active = 1 ")
    List<CarEntity> getActive();

    @Query("SELECT * FROM cars WHERE Nickname LIKE (:name)")
    CarEntity findByNickName(String name);

    @Insert
    long insert(CarEntity carEntity) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CarEntity> carEntityList);

    @Update
    void update(CarEntity carEntity);

    @Delete
    void delete(CarEntity carEntity);

    @Query("DELETE FROM cars")
    void deleteAll();

}
*/