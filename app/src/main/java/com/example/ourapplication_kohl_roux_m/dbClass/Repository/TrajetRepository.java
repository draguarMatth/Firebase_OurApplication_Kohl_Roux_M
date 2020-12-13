package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

import com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.TrajetListliveData;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.TrajetLiveData;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;


public class TrajetRepository {

    private static TrajetRepository instance;

    public static TrajetRepository getInstance() {
        if (instance == null) {
            synchronized (TrajetRepository.class) {
                if (instance == null) {
                    instance = new TrajetRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<TrajetEntity>> getCar(final String nickname) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("name")
                .child(String.valueOf(nickname))
                .child("trajet");
        return new TrajetListliveData(reference, nickname);
    }

    public void insert(final TrajetEntity car, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("name")
                .child(car.getName())
                .child("trajet");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("name")
                .child(car.getName())
                .child("trajet")
                .child(key)
                .setValue(car, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final TrajetEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("name")
                .child(car.getName())
                .child("trajet")
                .child(car.getName())
                .updateChildren(car.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final TrajetEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("name")
                .child(car.getName())
                .child("trajet")
                .child(car.getName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void transaction(final TrajetEntity sender, final TrajetEntity recipient,
                            OnAsyncEventListener callback) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                rootReference
                        .child("name")
                        .child(sender.getName())
                        .child("trajet")
                        .child(String.valueOf(sender.getUid()))
                        .updateChildren(sender.toMap());

                rootReference
                        .child("name")
                        .child(recipient.getName())
                        .child("trajet")
                        .child(String.valueOf(recipient.getUid()))
                        .updateChildren(recipient.toMap());

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    callback.onFailure(databaseError.toException());
                } else {
                    callback.onSuccess();
                }
            }
        });
    }
}
