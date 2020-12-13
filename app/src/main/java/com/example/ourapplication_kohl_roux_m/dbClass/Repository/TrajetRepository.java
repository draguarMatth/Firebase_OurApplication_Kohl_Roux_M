package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
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
                .getReference("trajets")
                .child(getTrajets());
        return new AllTripsLiveData(reference);
    }

    public LiveData<List<TrajetEntity>> getTrajetsByCarId(final long carId) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(getTrajetsByCarId(carId));
        return new AllTripsByCarLiveData(reference, carId);
    }

    public LiveData<List<TrajetEntity>> getTrajetByName(final String name) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(getTrajetByName(name));
        return new TripByNameLiveData(reference, name);

    }

    public LiveData <TrajetEntity> getTrajetById(final long trajetId) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(getTrajetById(trajetId));
        return new OneTripByIdLiveData(reference, trajetId);
    }

    public LiveData <TrajetEntity> getOneTrajet(final long carId, final String dateOfTrip) {

        // return ((BaseApp) application).getDatabase().trajetDao().getOneByCar(carId, dateOfTrip);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(getOneTrajet(carId,dateOfTrip));
        return new OneTripByCarLiveData(reference, carId,dateOfTrip);
    }

    public void insert(final TrajetEntity trajetEntity, OnAsyncEventListener callback) {

 //       new CreateTrajet(application, callback).execute(trajetEntity);

        FirebaseDatabase.getInstance()
                .getReference("trajets")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(trajetEntity, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final TrajetEntity trajetEntity, OnAsyncEventListener callback) {

        // new UpdateTrajet(application, callback).execute(trajetEntity);

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

        // new DeleteTrajet(application, callback).execute(trajetEntity);

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
