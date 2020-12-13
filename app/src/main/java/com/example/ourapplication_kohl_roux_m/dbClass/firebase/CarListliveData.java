package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;

public class CarListliveData extends LiveData<List<CarEntity>> {

    private static final String TAG = "CarListLiveData";

    private final DatabaseReference reference;
    private final String nickname;
    private final MyValueEventListener listener = new MyValueEventListener();


    public CarListliveData(DatabaseReference reference, String nickname) {
        this.reference = reference;
        this.nickname = nickname;
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

    private List<CarEntity> toAccounts(DataSnapshot snapshot) {
        List<CarEntity> accounts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CarEntity entity = childSnapshot.getValue(CarEntity.class);
            entity.setUid(Long.parseLong(childSnapshot.getKey()));
            entity.setNickName(nickname);
            accounts.add(entity);
        }
        return accounts;
    }
}
