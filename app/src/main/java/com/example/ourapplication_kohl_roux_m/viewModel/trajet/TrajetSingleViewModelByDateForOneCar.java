package com.example.ourapplication_kohl_roux_m.viewModel.trajet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourapplication_kohl_roux_m.BaseApp;
import com.example.ourapplication_kohl_roux_m.dbClass.Repository.TrajetRepository;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

public class TrajetSingleViewModelByDateForOneCar extends AndroidViewModel {

    private final TrajetRepository repository;
    private final String carId;
    private final String date;
    private final MediatorLiveData<TrajetEntity> observableTrajet;

    public TrajetSingleViewModelByDateForOneCar(@NonNull Application application,
                                     TrajetRepository trajetRepository,
                                                String carId, String date) {
        super(application);

        repository = trajetRepository;
        this.carId = carId;
        this.date = date;

        observableTrajet = new MediatorLiveData<>();

        observableTrajet.setValue(null);

        LiveData<TrajetEntity> trajet =
                repository.getOneTrajet(carId, date);

        observableTrajet.addSource(trajet, observableTrajet::setValue);
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<TrajetEntity> getSingleTripByDateForOneCarViewMod() {
        return observableTrajet;
    }

    public void update(TrajetEntity trajet, String carId, OnAsyncEventListener callback) {
        repository.update(trajet, carId, callback);
    }

    public void delete(TrajetEntity trajet, String carId, OnAsyncEventListener callback) {
        repository.delete(trajet, carId, callback);
    }
    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TrajetRepository trajetRepository;
        private String carId;
        private String date;

        public Factory(@NonNull Application application, final String carId, final String date) {
            this.application = application;
            this.carId = carId;
            this.date = date;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TrajetSingleViewModelByDateForOneCar(application, trajetRepository, carId, date);
        }
    }

}
