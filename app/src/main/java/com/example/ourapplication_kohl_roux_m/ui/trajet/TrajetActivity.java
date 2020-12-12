package com.example.ourapplication_kohl_roux_m.ui.trajet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;

public class TrajetActivity extends BaseActivity {

    private static final String TAG = "ViewTrajet";
    Bundle bundle;
    TextView nameTrajet;
    TextView distance;
    TextView down;
    TextView up;
    TextView consoElect;
    TextView consoFuel;
    private Intent previousIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_trajet, frameLayout);
        previousIntent = getIntent();
        bundle = previousIntent.getExtras();

        setTitle("Détails Trajet");
        navigationView.setCheckedItem(position);

        nameTrajet = findViewById(R.id.nameTrajet);
        distance = findViewById(R.id.distance);
        down = findViewById(R.id.downTrip);
        up = findViewById(R.id.upTrip);
        consoElect = findViewById(R.id.consoElect);
        consoFuel = findViewById(R.id.consoFuel);

        TrajetEntity trajet = bundle.getParcelable("Trajet");

        String name = trajet.getName();
        double dist = trajet.getKmTot();
        double deep = trajet.getTotDeep();
        double rise = trajet.getTotRise();
        double consoE = trajet.getElectricityTot();
        double consoF = trajet.getGasolinTot();

        nameTrajet.setText(name);
        distance.setText(String.valueOf(dist));
        down.setText(String.valueOf(deep));
        up.setText(String.valueOf(rise));
        consoElect.setText(String.valueOf(consoE));
        consoFuel.setText(String.valueOf(consoF));

    }
}
