package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.BaseApp;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.trajet.CreateTrajet;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.trajet.DeleteTrajet;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.trajet.UpdateTrajet;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

import java.util.List;

public class TrajetRepository {

    private static TrajetRepository instance;

    private TrajetRepository() {
    }

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

    public LiveData<List<TrajetEntity>> getTrajet(Application application) {
        return ((BaseApp) application).getDatabase().trajetDao().getAll();
    }

    public LiveData<List<TrajetEntity>> getTrajetByCarId(final long carId, Application application) {
        return ((BaseApp) application).getDatabase().trajetDao().getByCarId(carId);
    }

    public LiveData<List<TrajetEntity>> getTrajetByName(final String name,
                                                        Application application) {
        return ((BaseApp) application).getDatabase().trajetDao().getByName(name);
    }

    public LiveData <TrajetEntity> getTrajetById(final long trajetId, Application application) {
        return ((BaseApp) application).getDatabase().trajetDao().getById(trajetId);
    }

    public LiveData <TrajetEntity> getOneTrajet(final long carId, final String dateOfTrip, Application application) {
        return ((BaseApp) application).getDatabase().trajetDao().getOneByCar(carId, dateOfTrip);
    }

    public void insert(final TrajetEntity trajetEntity, OnAsyncEventListener callback,
                       Application application) {
        new CreateTrajet(application, callback).execute(trajetEntity);
    }

    public void update(final TrajetEntity trajetEntity, OnAsyncEventListener callback,
                       Application application) {
        new UpdateTrajet(application, callback).execute(trajetEntity);
    }

    public void delete(final TrajetEntity trajetEntity, OnAsyncEventListener callback,
                       Application application) {
        new DeleteTrajet(application, callback).execute(trajetEntity);
    }
}
