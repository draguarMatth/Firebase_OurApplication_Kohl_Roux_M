package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.AllTripsLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.OneTripByCarLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.OneTripByIdLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.TripByNameLiveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.TripsListForOneCarLiveData;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TrajetRepository {

    private static TrajetRepository instance;

    private TrajetRepository() {
    }

    public static TrajetRepository getInstance() {
        if (instance == null) {
            synchronized (TrajetRepository.class) {
                if (instance == null) {
                    instance = new TrajetRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<TrajetEntity>> getTrajets() {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars");
        return new AllTripsLiveData(reference);
    }

    public LiveData <TrajetEntity> getTrajetById(final String trajetId, final String carId) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars").child(carId);
        return new OneTripByIdLiveData(reference, trajetId);
    }

    public LiveData <TrajetEntity> getOneTrajet(final String carId, final String dateOfTrip) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars").child(carId);
        return new OneTripByCarLiveData(reference, carId, dateOfTrip);
    }

    public LiveData <List<TrajetEntity>> getTrajetByCarId(final String uid) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars").child(uid);
        return new TripsListForOneCarLiveData(reference, uid);
    }

    public void insert(final String carId, final TrajetEntity trajetEntity,final OnAsyncEventListener callback) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cars").child(carId);

        String id = reference.child("trajets").push().getKey();

        reference
                .child("trajets")
                .child(id)
                .setValue(trajetEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TrajetEntity trajetEntity, final String carId, OnAsyncEventListener callback) {

        String test = trajetEntity.getUid();
        System.out.println(test);

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carId)
                .child("trajets")
                .child(trajetEntity.getUid())
                .updateChildren(trajetEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TrajetEntity trajetEntity,String carId, OnAsyncEventListener callback) {

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(carId)
                .child("trajets")
                .child(trajetEntity.getUid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
