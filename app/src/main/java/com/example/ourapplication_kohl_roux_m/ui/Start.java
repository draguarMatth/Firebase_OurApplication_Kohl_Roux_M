package com.example.ourapplication_kohl_roux_m.ui;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.ui.Settings.SettingsActivity;
import com.example.ourapplication_kohl_roux_m.ui.trajet.ListTrajet_BazActivity;
//import com.example.ourapplication_kohl_roux_m.ui.car.ListMyCar;
import com.google.android.gms.common.util.DeviceProperties;
//import com.opencsv.CSVWriter;
import com.example.ourapplication_kohl_roux_m.ui.car.ListMyActiveCars;

import java.io.File;
import java.util.ArrayList;

public class Start extends AppCompatActivity {

    private Button choix;
    private Switch myswitch;
    private ArrayList<String> fileList;

    protected void onCreate(Bundle savecInstacneState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);

        super.onCreate(savecInstacneState);
        setContentView(R.layout.activity_start);

        choix = findViewById(R.id.btnstart);
        fileList = new ArrayList<>();

        choix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start.this, ListMyActiveCars.class);
                startActivity(intent);
            }
        });

        File downLoadFile = getExternalFilesDir("/liste");

        myswitch = findViewById(R.id.myswitch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            myswitch.setChecked(true);
        }

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });



        /*  CarEntity carEntity = new CarEntity("titine", "Hyundahi", "Bionic", 1.1, 8.9, "205 55 R16", true, 0);
         CarEntity carEntity = new CarEntity("CharAbeuh", "Fourragie", "TTonic", 3.4, 7.2, "", true, R.drawable.i8);

        TrajetEntity trajetEntity = new TrajetEntity( 1, "Anonymous", "07 novembre 2020", 0, 0, 0, 0, 0);
       TrajetEntity trajetEntity = new TrajetEntity( 1, "Jvéoboulo", "07.11.2019", 152.3, 3, 4, 5, 95);
        TrajetEntity trajetEntity = new TrajetEntity(2, "", "24.02.2020", 64.8, 12, 3, 42.3, 56.4);
        TrajetEntity trajetEntity = new TrajetEntity( 2, "Parlavass", "12.08.2020", 152.3, 3, 4, 12.6, 60);
    */


    }


    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), Start.class);
        startActivity(i);
        finish();
    }

}
