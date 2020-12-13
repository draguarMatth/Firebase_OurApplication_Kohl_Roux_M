package com.example.ourapplication_kohl_roux_m;

import android.app.Application;

import com.example.ourapplication_kohl_roux_m.dbClass.Repository.CarRepository;
import com.example.ourapplication_kohl_roux_m.dbClass.Repository.TrajetRepository;


public class BaseApp extends Application {

    public CarRepository getCarRepository() {
        return CarRepository.getInstance();
    }

    public TrajetRepository getTrajetRepository() {
        return TrajetRepository.getInstance();
    }
}