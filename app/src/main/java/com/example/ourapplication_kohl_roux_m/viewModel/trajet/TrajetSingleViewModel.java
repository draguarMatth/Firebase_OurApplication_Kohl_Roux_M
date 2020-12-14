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

public class TrajetSingleViewModel extends AndroidViewModel {

    private final TrajetRepository repository;
//    private final long carId;
    private final String trajetId;
    private final MediatorLiveData<TrajetEntity> observableTrajet;

    public TrajetSingleViewModel(@NonNull Application application,
                                 TrajetRepository trajetRepository, String trajetId) {
        super(application);

        repository = trajetRepository;
//        this.carId = carId;
        this.trajetId = trajetId;

        observableTrajet = new MediatorLiveData<>();

        observableTrajet.setValue(null);

        LiveData<TrajetEntity> trajet =
                repository.getTrajetById(trajetId);

        observableTrajet.addSource(trajet, observableTrajet::setValue);
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<TrajetEntity> getSingleTripviewMod() {
        return observableTrajet;
    }

    public void update(TrajetEntity trajet, OnAsyncEventListener callback) {
        repository.delete(trajet, callback);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TrajetRepository trajetRepository;
//        private long carId;
        private String trajetId;

        public Factory(@NonNull Application application, final String trajetId) {
            this.application = application;
//            this.carId = carId;
            this.trajetId = trajetId;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TrajetSingleViewModel(application, trajetRepository, trajetId);
        }
    }

}
