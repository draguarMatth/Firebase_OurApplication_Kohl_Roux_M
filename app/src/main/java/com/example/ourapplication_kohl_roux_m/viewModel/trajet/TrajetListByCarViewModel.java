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
import com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;

import java.util.List;

public class TrajetListByCarViewModel extends AndroidViewModel {

    private final Application application;

    private final TrajetRepository repository;

    private final MediatorLiveData<List<TrajetEntity>> observableTrajets;

    public TrajetListByCarViewModel(@NonNull Application application,
                                    final String nickname,
                                    TrajetRepository trajetRepository) {
        super(application);

        this.application = application;

        repository = trajetRepository;

        observableTrajets = new MediatorLiveData<>();

        observableTrajets.setValue(null);

        LiveData<List<TrajetEntity>> trajetList =
                repository.getCar(nickname);

        observableTrajets.addSource(trajetList, observableTrajets::setValue);
    }

    public LiveData<List<TrajetEntity>> getTrajetByCarViewModel() {
        return observableTrajets;
    }

    public void deleteTrajetViewModel(TrajetEntity trajetEntity,
                                      OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getTrajetRepository()
                .delete(trajetEntity, callback);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String nickname;

        private final TrajetRepository trajetRepository;

        public Factory(@NonNull Application application, String nickname) {
            this.application = application;
            this.nickname = nickname;
            trajetRepository = ((BaseApp) application).getTrajetRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new TrajetListByCarViewModel(application, nickname, trajetRepository);
        }
    }
}
