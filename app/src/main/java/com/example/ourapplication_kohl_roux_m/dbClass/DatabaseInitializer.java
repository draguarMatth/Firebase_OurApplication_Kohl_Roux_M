package com.example.ourapplication_kohl_roux_m.dbClass;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDataBase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addTrajet(final AppDataBase db, final long carId, final String name, final String date,
                                  final double kmTot, final double totRise, final double totDeep,
                                  final double gasolinTot, final double electricityTot) {
        TrajetEntity trajetEntity = new TrajetEntity(carId, name, date, kmTot, totRise, totDeep, gasolinTot, electricityTot);
        db.trajetDao().insert(trajetEntity);
    }

    private static void addCar(final AppDataBase db, final String nickName, final String carTradeMark, final String model,
                               final double consoEssence, final double batteryPower, final String wheelSize, final boolean carForTrip, final int drawable) {

        CarEntity carEntity = new CarEntity(nickName, carTradeMark, model, consoEssence, batteryPower, wheelSize, carForTrip, drawable);
        db.carDao().insert(carEntity);
    }

    private static void populateWithTestData(AppDataBase db) {
        db.carDao().deleteAll();
        db.trajetDao().deleteAll();

        addCar(db, "titine", "Hyundahi", "Bionic", 1.1, 8.9, "205 55 R16", true, 0);
        addCar(db, "CharAbeuh", "Fourragie", "TTonic", 3.4, 7.2, "", true, R.drawable.i8);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addTrajet(db, 1, "Anonymous", "07 novembre 2020", 0, 0, 0, 0, 0);
        addTrajet(db, 1, "Jvéoboulo", "07.11.2019", 152.3, 3, 4, 5, 95);
        addTrajet(db, 2, "", "24.02.2020", 64.8, 12, 3, 42.3, 56.4);
        addTrajet(db, 2, "Parlavass", "12.08.2020", 152.3, 3, 4, 12.6, 60);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDataBase database;

        PopulateDbAsync(AppDataBase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }

}
