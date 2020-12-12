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

    private final Application application;

    private final TrajetRepository repository;
    private final MediatorLiveData<List<TrajetEntity>> observableTrajets;

    public TrajetListViewModel(@NonNull Application application,
                               TrajetRepository trajetRepository) {
        super(application);

        this.application = application;

        repository = trajetRepository;

        observableTrajets = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableTrajets.setValue(null);

        LiveData<List<TrajetEntity>> trajetList =
                repository.getTrajet(application);

        // observe the changes of the entities from the database and forward them
        observableTrajets.addSource(trajetList, observableTrajets::setValue);
    }

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<TrajetEntity>> getTrajetsviewMod() {
        return observableTrajets;
    }

    public void deleteTrajet(TrajetEntity trajet, OnAsyncEventListener callback) {
        repository.delete(trajet, callback, application);
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
