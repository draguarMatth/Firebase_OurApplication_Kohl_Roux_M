package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.pojo.CarRoadTrips;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllCarLiveData extends LiveData<List<CarRoadTrips>> {

    private static final String TAG = "AllCarLiveData";

    private final DatabaseReference reference;
    private final AllCarLiveData.MyValueEventListener listener = new AllCarLiveData.MyValueEventListener();

    public AllCarLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(carWithRoadTripsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CarRoadTrips> carWithRoadTripsList(DataSnapshot snapshot) {
        List<CarRoadTrips> carsList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().isEmpty()) {
                CarRoadTrips carRoadTrips = new CarRoadTrips();
                carRoadTrips.carEntity = childSnapshot.getValue(CarEntity.class);
                carRoadTrips.carEntity.setUid(childSnapshot.getKey());
                carRoadTrips.trajets = tripDependingActivityCar(childSnapshot.child("trajets"),
                        childSnapshot.getKey());
                carsList.add(carRoadTrips);
            }
        }
        return carsList;
    }

    private List<TrajetEntity> tripDependingActivityCar(DataSnapshot snapshot, String carRef) {
        List<TrajetEntity> trajets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().isEmpty()) {
                TrajetEntity entity = childSnapshot.getValue(TrajetEntity.class);
                entity.setUid(childSnapshot.getKey());
                entity.setCarRef(carRef);
                trajets.add(entity);
            }
        }
        return trajets;
    }




}
