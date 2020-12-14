package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllTripsLiveData extends LiveData<List<TrajetEntity>> {

    private static final String TAG = "AllTripsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public AllTripsLiveData(DatabaseReference ref) {
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
            setValue(doRoadTripsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TrajetEntity> doRoadTripsList(DataSnapshot snapshot) {
        List<TrajetEntity> trajets = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TrajetEntity entity = childSnapshot.getValue(TrajetEntity.class);
            entity.setUid(childSnapshot.getKey());
            trajets.add(entity);
        }
        return trajets;
    }

}
