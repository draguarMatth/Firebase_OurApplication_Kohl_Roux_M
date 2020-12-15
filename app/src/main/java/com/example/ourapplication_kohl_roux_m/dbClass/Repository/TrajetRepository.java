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
                .getReference("trajets");
        return new AllTripsLiveData(reference);
    }

    public LiveData<List<TrajetEntity>> getTrajetByName(final String name) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets");
        return new TripByNameLiveData(reference, name);
    }

    public LiveData <TrajetEntity> getTrajetById(final String trajetId) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets");
        return new OneTripByIdLiveData(reference, trajetId);
    }

    public LiveData <TrajetEntity> getOneTrajet(final String carId, final String dateOfTrip) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets");
        return new OneTripByCarLiveData(reference, carId, dateOfTrip);
    }

    public LiveData <List<TrajetEntity>> getTrajetByCarId(final String uid) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets");
        return new TripsListForOneCarLiveData(reference, uid);
    }

    public void insert(final TrajetEntity trajetEntity,final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("trajets").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(id)
                .setValue(trajetEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TrajetEntity trajetEntity, OnAsyncEventListener callback) {

        FirebaseDatabase.getInstance()
                .getReference("cars")
                .child(trajetEntity.getUid())
                .updateChildren(trajetEntity.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TrajetEntity trajetEntity, OnAsyncEventListener callback) {

        FirebaseDatabase.getInstance()
                .getReference("cars")
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
