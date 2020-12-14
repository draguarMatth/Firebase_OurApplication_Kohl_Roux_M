package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.ActiveCarsAndTheseTripsLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.AllCarLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.CarLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.pojo.CarRoadTrips;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CarRepository {

    private static CarRepository instance;

//    private ArrayList<TrajetEntity> trajets;

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

    public LiveData<CarEntity> getCar(final String carId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carId);

        return new CarLiveData(reference);
    }

 //   public LiveData<List<CarRoadTrips>> getMyCars(final boolean carActivity) {
    public LiveData<List<CarEntity>> getMyCars(final boolean carActivity) {
 //       LiveData<List<CarEntity>> carsLiveD = ((BaseApp) application).getDatabase().carDao().getByActivity();
 //       List<CarEntity> cars = carsLiveD.getValue();

        String activityValue;

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");

        return new ActiveCarsAndTheseTripsLiveData(reference, carActivity);
    }

    public LiveData<List<CarRoadTrips>> getAllCar() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new AllCarLiveData(reference);
    }

    public void insert(final CarEntity carEntity, final OnAsyncEventListener callback) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        String key = reference.push().getKey();

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(key)
                .setValue(carEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final CarEntity carEntity, final OnAsyncEventListener callback) {

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

    }


    public void delete(final CarEntity carEntity, OnAsyncEventListener callback) {

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
