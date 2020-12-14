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

public class TrajetListByCarViewModel extends AndroidViewModel {

    private final TrajetRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<TrajetEntity>> observableTrajets;
//    private final MediatorLiveData<List<CarEntity>> observableCars;

    public TrajetListByCarViewModel(@NonNull Application application,
                                    TrajetRepository trajetRepository) {
        super(application);

        repository = trajetRepository;

        observableTrajets = new MediatorLiveData<>();

        observableTrajets.setValue(null);

        LiveData<List<TrajetEntity>> trajetList =
                repository.getTrajetByCarId();

        observableTrajets.addSource(trajetList, observableTrajets::setValue);
    }

    public LiveData<List<TrajetEntity>> getTrajetByCarViewModel() {
        return observableTrajets;
    }

    public void deleteTrajetViewModel(final TrajetEntity trajetEntity,
                                      OnAsyncEventListener callback) {
        repository.delete(trajetEntity,callback);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String carId;

        private final TrajetRepository trajetRepository;

        public Factory(@NonNull Application application, String carId) {
            this.application = application;
            this.carId = carId;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new TrajetListByCarViewModel(application, trajetRepository);
        }
    }
}
