package com.example.ourapplication_kohl_roux_m.ui.car;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.example.ourapplication_kohl_roux_m.viewModel.car.CarSingleViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CarDescription extends BaseActivity {

    private static final String TAG = "CarDescription";

    private ImageView imageCar;
    private TextView txtVwNickname;
    private TextView txtVwMark;
    private TextView txtVwModel;
    private CheckBox chkBoxActivity;
    private FloatingActionButton fab;

    private CarSingleViewModel viewModel;
    private CarEntity carEntity;
    private long carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_modify_car, frameLayout);

        carId = (long) getIntent().getExtras().get("CarId");

        setTitle(getString(R.string.car_desc));
        navigationView.setCheckedItem(position);

        imageCar = findViewById(R.id.image);
        txtVwNickname = findViewById(R.id.textViewNickName);
        txtVwMark = findViewById(R.id.textViewMark);
        txtVwModel = findViewById(R.id.textViewModel);
        chkBoxActivity = findViewById(R.id.checkBoxActivity);
        fab = findViewById(R.id.floatingActionButton);

        CarSingleViewModel.Factory factory = new CarSingleViewModel.Factory(carId,
                getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(CarSingleViewModel.class);
        viewModel.getMyCarViewMod().observe(this, carL -> {
            if (carL != null) {
                carEntity = carL;
                setupUi();
            }
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

    private void updateCar(final CarEntity newCar) {

        Toast toastSuccess = Toast.makeText(this, getString(R.string.car_data_mod), Toast.LENGTH_LONG);
        Toast toastFailed = Toast.makeText(this, getString(R.string.car_failed_mod), Toast.LENGTH_LONG);

        new UpdateCar(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, getString(R.string.modify_car_s));
                Intent intent = new Intent(CarDescription.this, ListMyActiveCars.class);
                startActivity(intent);
                toastSuccess.show();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, getString(R.string.modify_car_f), e);
                toastFailed.show();
            }
        }).execute(newCar);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    private void setupUi() {

        txtVwNickname.setText(carEntity.getNickName());

        if (carEntity.isCarForTrip())
            chkBoxActivity.setChecked(true);

        chkBoxActivity.setActivated(true);

        fab.setOnClickListener(view -> {
            carEntity.setNickName(txtVwNickname.getText().toString());
            if(chkBoxActivity.isChecked())
                carEntity.setCarForTrip(true);
            else
                carEntity.setCarForTrip(false);
            carEntity.setPicture(R.drawable.i8);

            updateCar(carEntity);
        });
    }
}
