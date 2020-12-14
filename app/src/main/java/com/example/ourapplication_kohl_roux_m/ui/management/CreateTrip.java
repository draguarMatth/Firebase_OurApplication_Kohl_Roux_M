package com.example.ourapplication_kohl_roux_m.ui.management;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.example.ourapplication_kohl_roux_m.viewModel.trajet.TrajetListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class CreateTrip extends BaseActivity {

    private static final String TAG = "RegisterNewRoadTrip";

    private EditText name;
    private EditText date;
    private String nameNewTrip, dateNewTrip;

    private Intent previousIntent;
    public Bundle bundle;
    private long carId;
    private String trajetDate;
    private TrajetEntity newTrajet;
    TrajetListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_roadtrip_init);

        previousIntent = getIntent();
        bundle = previousIntent.getExtras();
        carId = (long) bundle.get("CarId");

        initializeForm();
        Toast toast = Toast.makeText(this, getString(R.string.battery_and_gas_input), Toast.LENGTH_LONG);

         setTitle(getString(R.string.create_ride));
         navigationView.setCheckedItem(position);
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

    private void initializeForm() {

        String format = "dd/MM/yy H:mm:ss";
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        java.util.Date date1 = new java.util.Date();

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);

        date.setText(formater.format(date1));

        FloatingActionButton save = findViewById(R.id.saveTrip);
        save.setOnClickListener(view -> saveChanges(
                nameNewTrip = name.getText().toString(),
                dateNewTrip = date.getText().toString(),
                carId
        ));
    }

    private void saveChanges(String name, String date, long carID) {

        setupViewModels();



 /*       newTrajet = new TrajetEntity(carId, nameNewTrip, dateNewTrip, 0,
                0, 0, 0, 0);




        new CreateTrajet(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                trajetDate = newTrajet.getDate();
                Intent intent = new Intent(CreateTrip.this, NewTrajetConsumptionInput.class);
                intent.putExtra("TrajetDate", trajetDate);
                intent.putExtra("CarId", carId);
                startActivity(intent);

                Log.d(TAG, getString(R.string.create_ride_succes));
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, getString(R.string.create_ride_error), e);
            }

        }).execute(newTrajet);
*/
        viewModel.insetTrajet(newTrajet,new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "IsertTrip: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "IsertTrip: failure", e);
            }
        });

    }

    private void setupViewModels() {
        TrajetListViewModel.Factory factory = new TrajetListViewModel.Factory(getApplication());

        viewModel = new ViewModelProvider(this, factory).get(TrajetListViewModel.class);

    }

    private void setResponse(Boolean response) {
        if (response) {
            trajetDate = newTrajet.getDate();
            Intent intent = new Intent(CreateTrip.this, NewTrajetConsumptionInput.class);
            intent.putExtra("TrajetDate", trajetDate);
            intent.putExtra("CarId", carId);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.input_error), Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
