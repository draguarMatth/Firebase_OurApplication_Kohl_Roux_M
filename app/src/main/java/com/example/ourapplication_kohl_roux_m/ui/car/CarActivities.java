package com.example.ourapplication_kohl_roux_m.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;
import com.example.ourapplication_kohl_roux_m.ui.trajet.ListTrajet_BazActivity;
import com.example.ourapplication_kohl_roux_m.viewModel.car.CarSingleViewModel;

public class CarActivities extends BaseActivity {

    private static final String TAG = "CarActivity";

    private CarSingleViewModel viewModel;
    private CarEntity carEntity;

    private ImageButton btnModify;
    private ImageButton btnRoadTrip;
    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_car_activity, frameLayout);

        carId = (String) getIntent().getExtras().get("CarId");

        setTitle(getString(R.string.car_list));
        navigationView.setCheckedItem(position);


        btnModify = findViewById(R.id.imgBtnModify);
        btnModify.setOnClickListener(view -> {
            Intent intent = new Intent(CarActivities.this, CarDescription.class);
            intent.putExtra("CarId", carId);
            startActivity(intent);

        });

        btnRoadTrip = findViewById(R.id.imgBtnRoadTrip);
        btnRoadTrip.setOnClickListener(view -> {
            Intent intent = new Intent(CarActivities.this, ListTrajet_BazActivity.class);
            intent.putExtra("CarId", carId);
            startActivity(intent);

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        finish();
        return super.onNavigationItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

}
