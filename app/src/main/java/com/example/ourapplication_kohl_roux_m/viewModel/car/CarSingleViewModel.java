package com.example.ourapplication_kohl_roux_m.viewModel.car;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourapplication_kohl_roux_m.BaseApp;
import com.example.ourapplication_kohl_roux_m.dbClass.Repository.CarRepository;
import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

public class CarSingleViewModel extends AndroidViewModel {

    private final Application application;

    private final CarRepository repository;
    private final MediatorLiveData<CarEntity> observableCar;

    public CarSingleViewModel(@NonNull Application application,
                              final long carId,
                              CarRepository carRepository) {
        super(application);

        this.application = application;

        repository = carRepository;

        observableCar = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCar.setValue(null);

        LiveData<CarEntity> car =
                repository.getCar(carId);


        // observe the changes of the entities from the database and forward them
        observableCar.addSource(car, observableCar::setValue);
    }

    /**
     * Expose the LiveData MyCars query so the UI can observe it.
     */
    public LiveData<CarEntity> getMyCarViewMod() {
        return observableCar;
    }

    public void modifyOneCar(final CarEntity carEntity, OnAsyncEventListener callback) {
        repository.update(carEntity, callback);
    }

    /**
     * A creator is used to inject the id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CarRepository repository;

        private long carId;

        public Factory(final long carId, @NonNull Application application) {
            this.application = application;
            this.carId = carId;
            repository = ((BaseApp) application).getCarRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CarSingleViewModel(carId, application, repository);
        }
    }
}
