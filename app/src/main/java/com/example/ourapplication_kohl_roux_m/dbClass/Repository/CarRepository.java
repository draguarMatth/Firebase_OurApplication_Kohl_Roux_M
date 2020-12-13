package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.BaseApp;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private static CarRepository instance;

    private ArrayList<TrajetEntity> trajets;

    private CarRepository() {
    }

    public static CarRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CarEntity> getCar(final long carId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carId);
        return new CarLiveData(reference);
    }

    public LiveData<List<CarEntity>> getMyCars() {
 //       LiveData<List<CarEntity>> carsLiveD = ((BaseApp) application).getDatabase().carDao().getByActivity();
 //       List<CarEntity> cars = carsLiveD.getValue();

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(getMyCars());
        return new ActiveCarLiveData(reference);
    }

    public LiveData<List<CarEntity>> getAllCar() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(getAllCar());
        return new AllCarLiveData(reference);
    }

    public void insert(final CarEntity carEntity, final OnAsyncEventListener callback) {

        //       new CreateCar(callback).execute(carEntity);

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(carEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final CarEntity carEntity, final OnAsyncEventListener callback) {
 //       new UpdateCar(callback).execute(carEntity);

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carEntity.getUid())
                .updateChildren(carEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    /*    FirebaseAuth.getInstance().getCurrentUser().updatePassword(carEntity.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "update Car failure!", e)
                );

     */

    }


    public void delete(final CarEntity carEntity, OnAsyncEventListener callback) {

    //    new DeleteCar(callback).execute(carEntity);

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carEntity.getUid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }
}
