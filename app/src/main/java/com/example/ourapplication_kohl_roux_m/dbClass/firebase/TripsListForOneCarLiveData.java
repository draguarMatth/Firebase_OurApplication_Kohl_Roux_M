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

public class TripsListForOneCarLiveData extends LiveData<List<TrajetEntity>> {

    private static final String TAG = "CarLiveData";

    private final DatabaseReference reference;
    private final String carId;
    private final TripsListForOneCarLiveData.MyValueEventListener listener = new TripsListForOneCarLiveData.MyValueEventListener();

    public TripsListForOneCarLiveData(DatabaseReference ref) {
        reference = ref;
        carId = ref.getParent().getParent().getKey();
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
            setValue(getTrips(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TrajetEntity> getTrips (DataSnapshot snapshot) {
        List<TrajetEntity> trajets = new ArrayList<>();

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().isEmpty()) {
                TrajetEntity trajet = new TrajetEntity();
                trajet = childSnapshot.getValue(TrajetEntity.class);

                trajets.add(trajet);
            }
        }
        return trajets;
    }
}
