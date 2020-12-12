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

    private final Application application;

    private final TrajetRepository repository;
    private final long carId;
    private final String dateOfTrip;
    private final MediatorLiveData<TrajetEntity> observableTrajet;

    public TrajetSingleViewModel(@NonNull Application application,
                                 TrajetRepository trajetRepository, long carId, String dateOfTrip) {
        super(application);

        this.application = application;

        repository = trajetRepository;
        this.carId = carId;
        this.dateOfTrip = dateOfTrip;

        observableTrajet = new MediatorLiveData<>();

        observableTrajet.setValue(null);

        LiveData<TrajetEntity> trajet =
                repository.getOneTrajet(carId, dateOfTrip, application);

        observableTrajet.addSource(trajet, observableTrajet::setValue);
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<TrajetEntity> getSingleTripviewMod() {
        return observableTrajet;
    }

    public void update(TrajetEntity trajet, OnAsyncEventListener callback) {
        repository.delete(trajet, callback, application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final TrajetRepository trajetRepository;
        private long carId;
        private String dateOfTrip;

        public Factory(@NonNull Application application, final long carId, final String dateOfTrip) {
            this.application = application;
            this.carId = carId;
            this.dateOfTrip = dateOfTrip;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TrajetSingleViewModel(application, trajetRepository, carId, dateOfTrip);
        }
    }

}
