package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class OneTripByIdLiveData extends LiveData<TrajetEntity> {

    private static final String TAG = "OneTripByIdLiveData";

    private final DatabaseReference reference;
    private final String trajetId;
    private final MyValueEventListener listener = new MyValueEventListener();

    public OneTripByIdLiveData(DatabaseReference ref, String trajetId) {
        this.reference = ref;
        this.trajetId = trajetId;
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
            for (DataSnapshot childSnapshot : dataSnapshot.child("trajets").getChildren()) {
                if (childSnapshot.getKey().equals(trajetId)) {
                    TrajetEntity entity = childSnapshot.getValue(TrajetEntity.class);
                    entity.setUid(trajetId);
                    setValue(entity);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
