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

public class TripByNameLiveData extends LiveData<List<TrajetEntity>> {

    private static final String TAG = "TripByNameLiveData";

    private final DatabaseReference reference;
    private final String carRef;
    private final String tripName;
    private final TripByNameLiveData.MyValueEventListener listener = new TripByNameLiveData.MyValueEventListener();

    public TripByNameLiveData(DatabaseReference ref, String name) {
        this.reference = ref;
        this.carRef = ref.getParent().getParent().getKey();
        this.tripName = name;
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
            setValue(listOfTrips(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TrajetEntity> listOfTrips(DataSnapshot snapshot) {
        List<TrajetEntity> trajets = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TrajetEntity trajet = childSnapshot.getValue(TrajetEntity.class);
            trajet.setUid(childSnapshot.getKey());
            trajet.setNamOfTrip(tripName);
            trajets.add(trajet);
        }

        return trajets;
    }
}
