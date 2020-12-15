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
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

import java.util.List;

public class CarMyListViewModel extends AndroidViewModel {

    private final CarRepository repository;
    private final MediatorLiveData<List<CarEntity>> observableCars;

    public CarMyListViewModel(@NonNull Application application,
                              CarRepository carRepository) {
        super(application);

       repository = carRepository;

        observableCars = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCars.setValue(null);

        LiveData<List<CarEntity>> carList =
                repository.getMyCars(true);

        // observe the changes of the entities from the database and forward them
        observableCars.addSource(carList, observableCars::setValue);
    }

    /**
     * Expose the LiveData MyCars query so the UI can observe it.
     */
    public LiveData<List<CarEntity>> getMyCarsViewMod() {
        return observableCars;
    }

    public void deleteOneCar(CarEntity carEntity, OnAsyncEventListener callback) {
        repository.delete(carEntity, callback);
    }

    public void modifyOneCar(final CarEntity carEntity, OnAsyncEventListener callback) {
        repository.update(carEntity, callback);
    }

    public void createTunedCar(final CarEntity carEntity, OnAsyncEventListener callback) {
        repository.insert(carEntity, callback);
    }

    /**
     * A creator is used to inject the id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CarRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getCarRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CarMyListViewModel(application, repository);
        }
    }
}
