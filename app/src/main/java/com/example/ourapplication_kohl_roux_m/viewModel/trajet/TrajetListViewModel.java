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

import java.util.List;

public class TrajetListViewModel extends AndroidViewModel {

    private final TrajetRepository repository;
    private final MediatorLiveData<List<TrajetEntity>> observableTrajets;

    public TrajetListViewModel(@NonNull Application application,
                               TrajetRepository trajetRepository) {
        super(application);

        repository = trajetRepository;

        observableTrajets = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTrajets.setValue(null);

        LiveData<List<TrajetEntity>> trajetList =
                repository.getTrajets();

        // observe the changes of the entities from the database and forward them
        observableTrajets.addSource(trajetList, observableTrajets::setValue);
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<TrajetEntity>> getTrajetByCarViewModel() {
        return observableTrajets;
    }

    public void deleteTrajet(TrajetEntity trajet, String carId, OnAsyncEventListener callback) {
        repository.delete(trajet, carId, callback);
    }

    public void insetTrajet(String carId, TrajetEntity trajet, OnAsyncEventListener callback) {
        repository.insert(carId, trajet, callback);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final TrajetRepository trajetRepository;


        public Factory(@NonNull Application application) {
            this.application = application;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TrajetListViewModel(application, trajetRepository);
        }
    }

}
