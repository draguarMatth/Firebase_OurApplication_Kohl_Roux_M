package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CarLiveData extends LiveData<CarEntity> {
    private static final String TAG = "CarLiveData";

    private final DatabaseReference reference;
    private final String nickname;
    private final CarLiveData.MyValueEventListener listener = new CarLiveData.MyValueEventListener();

    public CarLiveData(DatabaseReference reference, String nickname) {
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
            CarEntity entity = dataSnapshot.getValue(CarEntity.class);
            entity.setUid(Long.parseLong(dataSnapshot.getKey()));
            entity.setNickName(nickname);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
