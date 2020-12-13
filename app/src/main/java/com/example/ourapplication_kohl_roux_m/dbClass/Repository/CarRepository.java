package com.example.ourapplication_kohl_roux_m.dbClass.Repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.firebase.CarListliveData;

import com.example.ourapplication_kohl_roux_m.dbClass.firebase.CarLiveData;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class CarRepository {

    private static CarRepository instance;

    public static CarRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new CarRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CarEntity> getCarById(final long carUid) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("uid")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("car")
                .child(String.valueOf(carUid));
        return new CarLiveData(reference);
    }

    public LiveData<List<CarEntity>> getCarByName(final String nickname) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("nickname")
                .child(String.valueOf(nickname))
                .child("car");
        return new CarListliveData(reference, nickname);
    }

    public void insert(final CarEntity car, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("nickname")
                .child(car.getNickName())
                .child("car");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("nickname")
                .child(car.getNickName())
                .child("car")
                .child(key)
                .setValue(car, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final CarEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("nickname")
                .child(car.getNickName())
                .child("car")
                .child(car.getNickName())
                .updateChildren(car.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final CarEntity car, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("nickname")
                .child(car.getNickName())
                .child("car")
                .child(car.getNickName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void transaction(final CarEntity sender, final CarEntity recipient,
                            OnAsyncEventListener callback) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                rootReference
                        .child("nickname")
                        .child(sender.getNickName())
                        .child("car")
                        .child(String.valueOf(sender.getUid()))
                        .updateChildren(sender.toMap());

                rootReference
                        .child("nickname")
                        .child(recipient.getNickName())
                        .child("car")
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
