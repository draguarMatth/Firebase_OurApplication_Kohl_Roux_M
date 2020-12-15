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

import java.util.List;
/*
public class TrajetListByNameViewModel extends AndroidViewModel {

    private final Application application;

    private final TrajetRepository repository;

    private final MediatorLiveData<List<TrajetEntity>> observableTrajets;

    public TrajetListByNameViewModel(@NonNull Application application,
                                     final String tripName,
                                     TrajetRepository trajetRepository) {
        super(application);

        this.application = application;

        repository = trajetRepository;

        observableTrajets = new MediatorLiveData<>();

        observableTrajets.setValue(null);

        LiveData<List<TrajetEntity>> trajetList =
                repository.getTrajetByName(tripName);

        observableTrajets.addSource(trajetList, observableTrajets::setValue);
    }

    public LiveData<List<TrajetEntity>> getTrajet() {
        return observableTrajets;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String tripName;

        private final TrajetRepository trajetRepository;

        public Factory(@NonNull Application application, String tripName) {
            this.application = application;
            this.tripName = tripName;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new TrajetListByNameViewModel(application, tripName, trajetRepository);
        }
    }
}
*/