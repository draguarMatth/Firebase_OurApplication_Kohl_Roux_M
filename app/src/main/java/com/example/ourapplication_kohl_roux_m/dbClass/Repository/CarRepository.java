package com.example.ourapplication_kohl_roux_m.dbClass.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ourapplication_kohl_roux_m.BaseApp;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.car.CreateCar;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.car.DeleteCar;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.car.UpdateCar;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private static CarRepository instance;

    private ArrayList<TrajetEntity> trajets;

    private CarRepository() {
    }

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

    public LiveData<CarEntity> getCar(final long carId, Application application) {
        return ((BaseApp) application).getDatabase().carDao().getCar(carId);
    }

    public LiveData<List<CarEntity>> getMyCars(Application application) {
        LiveData<List<CarEntity>> carsLiveD = ((BaseApp) application).getDatabase().carDao().getByActivity();
        List<CarEntity> cars = carsLiveD.getValue();

        return ((BaseApp) application).getDatabase().carDao().getByActivity();
    }

    public LiveData<List<CarEntity>> getAllCar(Application application) {
        return ((BaseApp) application).getDatabase().carDao().getAll();
    }

    public void insert(final CarEntity carEntity, OnAsyncEventListener callback,
                       Application application) {
        new CreateCar(application, callback).execute(carEntity);
    }

    public void update(final CarEntity carEntity, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCar(application, callback).execute(carEntity);
    }


    public void delete(final CarEntity carEntity, OnAsyncEventListener callback,
                       Application application) {

        new DeleteCar(application, callback).execute(carEntity);

    }
}
