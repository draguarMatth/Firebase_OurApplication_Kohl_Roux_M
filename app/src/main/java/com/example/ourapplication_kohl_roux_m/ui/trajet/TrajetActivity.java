package com.example.ourapplication_kohl_roux_m.ui.trajet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.viewModel.trajet.TrajetSingleViewModel;
import com.google.firebase.auth.FirebaseAuth;

import com.example.ourapplication_kohl_roux_m.R;
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
    private TrajetSingleViewModel viewModel;
    private TrajetEntity trajet;
    private String trajetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_trajet, frameLayout);
        previousIntent = getIntent();
        bundle = previousIntent.getExtras();
        trajetId = (String) bundle.get("TrajetId");


        setTitle("Détails Trajet");
        navigationView.setCheckedItem(position);

        nameTrajet = findViewById(R.id.nameTrajet);
        distance = findViewById(R.id.distance);
        down = findViewById(R.id.downTrip);
        up = findViewById(R.id.upTrip);
        consoElect = findViewById(R.id.consoElect);
        consoFuel = findViewById(R.id.consoFuel);

        TrajetSingleViewModel.Factory factory = new TrajetSingleViewModel.Factory(
                getApplication(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        viewModel = new ViewModelProvider(this, factory).get(TrajetSingleViewModel.class);
        viewModel.getSingleTripviewMod().observe(this, trajetVM -> {
            if (trajetVM != null) {
                trajet = trajetVM;
  //              repository.updateContent(trajet, callback);
            }
        });

    }
}
