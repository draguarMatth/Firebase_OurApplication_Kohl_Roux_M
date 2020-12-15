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

public class TrajetSingleViewModelById extends AndroidViewModel {

    private final TrajetRepository repository;
    private final String trajetId;
    private final String carId;
    private final MediatorLiveData<TrajetEntity> observableTrajet;

    public TrajetSingleViewModelById(@NonNull Application application,
                                     TrajetRepository trajetRepository, String trajetId, String carId) {
        super(application);

        repository = trajetRepository;
        this.trajetId = trajetId;
        this.carId = carId;
        observableTrajet = new MediatorLiveData<>();

        observableTrajet.setValue(null);

        LiveData<TrajetEntity> trajet =
                repository.getTrajetById(trajetId, carId);

        observableTrajet.addSource(trajet, observableTrajet::setValue);
    }

    public LiveData<TrajetEntity> getSingleTripviewMod() {
        return observableTrajet;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TrajetRepository trajetRepository;
        private String trajetId;
        private String carId;

        public Factory(@NonNull Application application, final String trajetId, final String carId) {
            this.application = application;
            this.trajetId = trajetId;
            this.carId = carId;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TrajetSingleViewModelById(application, trajetRepository, trajetId, carId);
        }
    }

}
