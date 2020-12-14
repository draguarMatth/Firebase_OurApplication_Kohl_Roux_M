package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OneTripByCarLiveData extends LiveData<TrajetEntity> {

    private static final String TAG = "OneTripByCarLiveData";

    private final DatabaseReference reference;
    private final String carRef;
    private final String dateOfTrip;
    private final OneTripByCarLiveData.MyValueEventListener listener = new OneTripByCarLiveData.MyValueEventListener();

    public OneTripByCarLiveData(DatabaseReference ref, String carRef, String dateOfTrip) {
        reference = ref;
        //carRef = ref.getParent().getParent().getKey();
        this.carRef = carRef;
        this.dateOfTrip = dateOfTrip;
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
            TrajetEntity entity = dataSnapshot.getValue(TrajetEntity.class);
            entity.setUid(dataSnapshot.getKey());
            entity.setCarRef(carRef);
            entity.setDate(dateOfTrip);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }


}
