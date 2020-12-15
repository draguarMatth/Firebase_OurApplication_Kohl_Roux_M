package com.example.ourapplication_kohl_roux_m.ui.management;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.adapter.RecyclerAdapter;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;
import com.example.ourapplication_kohl_roux_m.ui.trajet.ListTrajet_BazActivity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.example.ourapplication_kohl_roux_m.util.RecyclerViewItemClickListener;
import com.example.ourapplication_kohl_roux_m.viewModel.trajet.TrajetSingleViewModelByDateForOneCar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NewTrajetConsumptionInput extends BaseActivity {

    private static final String TAG = "InputConsumptionsActivity";
    public Bundle bundleTemp;
    private Intent previousIntent;
    private Bundle bundle;

    private Button addElectButton;
    private Button addFuelButton;
    private FloatingActionButton save;
    private EditText addElectItem;
    private EditText addFuelItem;
    private TextView valueInList;

    private Application application;

    private TrajetSingleViewModelByDateForOneCar viewmodelForOneTrip;
    private TrajetEntity upDTrajet;
    private String trajetDate;
    private String trajetId;
    private String carId;
    private RecyclerAdapter<String> electAdapter;
    private List<String> electInputs;
    private RecyclerAdapter<String> fuelAdapter;
    private List<String> fuelInputs;
    private int electInputsHashcode;
    private int fuelInputsHashcode;
    private RecyclerView recyclerViewElect;
    private RecyclerView recyclerViewFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getApplication();

        getLayoutInflater().inflate(R.layout.activity_comsumption_input, frameLayout);
        previousIntent = getIntent();
        bundle = previousIntent.getExtras();
        trajetDate = (String) bundle.get("TrajetDate");
        carId = (String) bundle.get("CarId");
        trajetId = (String) bundle.get("TrajetId");

        setTitle(getString(R.string.consommation));
        navigationView.setCheckedItem(position);

        addElectItem = findViewById(R.id.saisieElect);
        addFuelItem = findViewById(R.id.saisieCarb);
        addElectButton = findViewById(R.id.addElectButton);
        addFuelButton = findViewById(R.id.addFuelButton);
        valueInList = findViewById(R.id.tvRecyclerView);
        recyclerViewElect = findViewById(R.id.electInputsRecyclerView);
        recyclerViewFuel = findViewById(R.id.fuelInputsRecyclerView);
        save = findViewById(R.id.saveTrip);

        RecyclerView.LayoutManager layoutManagerElect = new LinearLayoutManager(null);
        recyclerViewElect.setLayoutManager(layoutManagerElect);
        RecyclerView.LayoutManager layoutManagerFuel = new LinearLayoutManager(null);
        recyclerViewFuel.setLayoutManager(layoutManagerFuel);

        DividerItemDecoration dividerItemDecorationElect = new DividerItemDecoration(recyclerViewElect.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerViewElect.addItemDecoration(dividerItemDecorationElect);
        DividerItemDecoration dividerItemDecorationFuel = new DividerItemDecoration(recyclerViewFuel.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerViewFuel.addItemDecoration(dividerItemDecorationFuel);

        electInputs = new ArrayList<String>();
        electInputsHashcode = electInputs.hashCode();

        electAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + electInputs.get(position));

                Toast toast = Toast.makeText(NewTrajetConsumptionInput.this, getString(R.string.long_press), Toast.LENGTH_LONG);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + electInputs.get(position));

                createModifyDialog(position, electInputsHashcode);
            }
        });

        fuelInputs = new ArrayList<String>();
        fuelInputsHashcode = fuelInputs.hashCode();

        fuelAdapter = new RecyclerAdapter<String>(new RecyclerViewItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + fuelInputs.get(position));

                Toast toast = Toast.makeText(NewTrajetConsumptionInput.this, getString(R.string.long_press), Toast.LENGTH_LONG);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + fuelInputs.get(position));

                createModifyDialog(position, fuelInputsHashcode);
            }
        });

        electAdapter.setData(electInputs);
        fuelAdapter.setData(fuelInputs);

        recyclerViewElect.setAdapter(electAdapter);
        recyclerViewFuel.setAdapter(fuelAdapter);

        addElectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = addElectItem.getText().toString();

                electInputs.add(newValue);
                electAdapter.setData(electInputs);
                addElectItem.setText("");

                recyclerViewElect.setAdapter(electAdapter);
                recyclerViewElect.refreshDrawableState();

                bundleTemp = getIntent().getBundleExtra("tempSaisiesConsom");
            }
        });

        addFuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newValue = addFuelItem.getText().toString();

                fuelInputs.add(newValue);
                fuelAdapter.setData(fuelInputs);
                addFuelItem.setText("");

                recyclerViewFuel.setAdapter(fuelAdapter);
                recyclerViewFuel.refreshDrawableState();

                bundleTemp = getIntent().getBundleExtra("tempSaisiesConsom");
            }
        });


        TrajetSingleViewModelByDateForOneCar.Factory factory = new TrajetSingleViewModelByDateForOneCar.Factory(getApplication(), carId, trajetDate);
        viewmodelForOneTrip = new ViewModelProvider(this, factory).get(TrajetSingleViewModelByDateForOneCar.class);
        viewmodelForOneTrip.getSingleTripByDateForOneCarViewMod().observe(this, trajetL -> {
            if (trajetL != null) {
                upDTrajet = trajetL;
                save.setOnClickListener(view -> {
                            saveChanges();
                        }
                );
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


    @SuppressLint("StaticFieldLeak")
    private void createModifyDialog(final int position, final int hashcodeInputs) {

        List<String> aModif = getList(hashcodeInputs);

        final String value = getList(hashcodeInputs).get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.title_activity_modify_Inputs));
        alertDialog.setCancelable(false);

        final TextView modifyMessage = view.findViewById(R.id.editNumberToModify);
        modifyMessage.setText(value);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.modify), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.data_mod), Toast.LENGTH_LONG);

            String newValue = modifyMessage.getText().toString();

            aModif.set(position, newValue);
            getRecyclerView(hashcodeInputs).refreshDrawableState();

            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.delete), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.data_dele), Toast.LENGTH_LONG);

            aModif.remove(position);
            getRecyclerView(hashcodeInputs).refreshDrawableState();

            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);

        alertDialog.show();
    }

    private List<String> getList(int hashcode) {

        if (hashcode == electInputsHashcode)
            return electInputs;
        return fuelInputs;
    }

    private RecyclerView getRecyclerView(int hashcode) {

        if (hashcode == electInputsHashcode) {
            electAdapter.setData(electInputs);
            recyclerViewElect.setAdapter(electAdapter);
            return recyclerViewElect;
        }
        fuelAdapter.setData(fuelInputs);
        recyclerViewElect.setAdapter(fuelAdapter);
        return recyclerViewFuel;
    }

    private void saveChanges() {

        upDTrajet.setElectricityTot(calcul(electInputs)) ;
        upDTrajet.setGasolinTot (calcul(fuelInputs));
        upDTrajet.setKmTot (1000);
        upDTrajet.setTotDeep (5);
        upDTrajet.setTotRise (5);

        viewmodelForOneTrip.update(upDTrajet, carId, new OnAsyncEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess() {
                setResponse(true);
                Log.d(TAG, getString(R.string.Input_succ));
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, getString(R.string.Input_fail), e);
            }

        });

    }

    private void setResponse(Boolean response) {
        if (response) {
            Intent intent = new Intent(NewTrajetConsumptionInput.this, ListTrajet_BazActivity.class);
            intent.putExtra("CarId", carId);
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.Ride_n_save), Toast.LENGTH_LONG);

        }
    }

    private double calcul(List<String> saisies) {
        double result = 0;
        for (String item : saisies) {
            result += (Double.valueOf(item));
        }

        return result;
    }

}
