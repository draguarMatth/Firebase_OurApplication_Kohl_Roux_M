package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrajetListliveData extends LiveData<List<TrajetEntity>> {

    private static final String TAG = "TrajetListLiveData";

    private final DatabaseReference reference;
    private final String name;
    private final TrajetListliveData.MyValueEventListener listener = new TrajetListliveData.MyValueEventListener();

    public TrajetListliveData(DatabaseReference reference, String name) {
        this.reference = reference;
        this.name = name;
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
            setValue(toAccounts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<TrajetEntity> toAccounts(DataSnapshot snapshot) {
        List<TrajetEntity> accounts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            TrajetEntity entity = childSnapshot.getValue(TrajetEntity.class);
            entity.setUid(Long.parseLong(childSnapshot.getKey()));
            entity.setName(name);
            accounts.add(entity);
        }
        return accounts;
    }
}

