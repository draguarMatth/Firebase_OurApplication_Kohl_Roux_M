package com.example.ourapplication_kohl_roux_m.dbClass.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TrajetLiveData extends LiveData<TrajetEntity> {
    private static final String TAG = "TrajetLiveData";

    private final DatabaseReference reference;
    private final String name;

    private final TrajetLiveData.MyValueEventListener listener = new TrajetLiveData.MyValueEventListener();

    public TrajetLiveData(DatabaseReference ref) {
        reference = ref;
        name = ref.getParent().getParent().getKey();
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
            entity.setNamOfTrip(name);

  /*          entity = dataSnapshot.child("trajets").
            carEntity.setNickname(childSnapshot.child("nickname").getValue(String.class));

            entity.setNamOfTrip(kmTot);
            ", kmTot);
                    result.put("date", date);
            result.put("totRise", totRise);
            result.put("totDeep", totDeep);
            result.put("gasolinTot", gasolinTot);
            result.put("electricityTot", electricityTot);
*/
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
