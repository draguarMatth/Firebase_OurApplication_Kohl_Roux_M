package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActiveCarsLiveData extends LiveData<List<CarEntity>> {
//    public class ActiveCarsAndTheseTripsLiveData extends LiveData<List<CarRoadTrips>> {

    private static final String TAG = "ActiveCarsAndTheseTripsLiveData";

    private final DatabaseReference reference;
    private final boolean carActivity;
    private final ActiveCarsLiveData.MyValueEventListener listener = new ActiveCarsLiveData.MyValueEventListener();

    public ActiveCarsLiveData(DatabaseReference ref, Boolean activity) {
        reference = ref;
        carActivity = activity;
     //   carId = ref.getParent().getParent().getKey();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(carWithRoadTripsList(dataSnapshot));
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CarEntity> carWithRoadTripsList(DataSnapshot snapshot) {
        List<CarEntity> carsList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                if (childSnapshot.child("carForTrip").getValue(Boolean.TYPE) == carActivity) {
                CarEntity carEntity;

                carEntity = childSnapshot.getValue(CarEntity.class);
                carEntity.setUid(childSnapshot.getKey());
                carEntity.setNickname(childSnapshot.child("nickname").getValue(String.class));
                carEntity.setCarTradeMark(childSnapshot.child("carTradeMark").getValue(String.class));
                carEntity.setModel(childSnapshot.child("model").getValue(String.class));
                carEntity.setConsoFuel(childSnapshot.child("consoFuel").getValue(double.class));
                carEntity.setBatteryPower(childSnapshot.child("batteryPower").getValue(double.class));
                carEntity.setWheelSize(childSnapshot.child("wheelSize").getValue(String.class));
                carEntity.setCarForTrip(childSnapshot.child("carForTrip").getValue(boolean.class));
                carEntity.setPicture(childSnapshot.child("picture").getValue(int.class));

                carsList.add(carEntity);
            }
        }

        return carsList;
    }

    private List<TrajetEntity> tripDependingActivityCar(DataSnapshot snapshot, String carRef) {
        List<TrajetEntity> trajets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TrajetEntity entity = childSnapshot.getValue(TrajetEntity.class);
            entity.setUid(childSnapshot.getKey());
            entity.setCarRef(carRef);
            trajets.add(entity);
        }
        return trajets;
    }

}
