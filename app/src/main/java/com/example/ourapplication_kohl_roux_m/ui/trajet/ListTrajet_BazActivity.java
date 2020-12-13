package com.example.ourapplication_kohl_roux_m.ui.trajet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.adapter.RecyclerAdapter;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.ui.BaseActivity;
import com.example.ourapplication_kohl_roux_m.ui.Settings.SettingsActivity;
import com.example.ourapplication_kohl_roux_m.ui.management.CreateTrip;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.example.ourapplication_kohl_roux_m.util.RecyclerViewItemClickListener;
import com.example.ourapplication_kohl_roux_m.viewModel.trajet.TrajetListByCarViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListTrajet_BazActivity extends BaseActivity {

    private static final String TAG = "ListTrajet";

    private Intent previousIntent;
    private Bundle bundle;

    private List<TrajetEntity> trajets;
    private RecyclerAdapter<TrajetEntity> adapter;
    private TrajetListByCarViewModel viewModel;
    public long carId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_list_trajet, frameLayout);

        setTitle(getString(R.string.list_ride));
        navigationView.setCheckedItem(position);

        RecyclerView recyclerView = findViewById(R.id.trajetsRecyclerView);
        previousIntent = getIntent();
        bundle = previousIntent.getExtras();
        carId = (long) bundle.get("CarId");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        TrajetListByCarViewModel.Factory factory = new TrajetListByCarViewModel.Factory(
                getApplication(), carId);
        viewModel = new ViewModelProvider(this, factory).get(TrajetListByCarViewModel.class);
        viewModel.getTrajetByCarViewModel().observe(this, trajetsL -> {
            if (trajetsL != null) {
                trajets = trajetsL;
                adapter.setData(trajets);
            }
        });

        trajets = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + trajets.get(position).getName());

                TrajetEntity trajet = trajets.get(position);
                Intent intent = new Intent(ListTrajet_BazActivity.this, TrajetActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("Trajet", trajet);
                intent.putExtra("TrajetId", trajets.get(position).getUid());
                intent.putExtra("CardId", carId);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + trajets.get(position).getName());
                createDeleteDialog(position);
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {

                    Intent intent = new Intent(ListTrajet_BazActivity.this, CreateTrip.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    intent.putExtra("CarId", carId);
                    startActivity(intent);

                }
        );

        recyclerView.setAdapter(adapter);
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

    private void createDeleteDialog(final int position) {

        final TrajetEntity trajet = trajets.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Trajet effacé, Trajet perdu");
        alertDialog.setCancelable(true);

        final TextView deleteMessage = view.findViewById(R.id.tv_delete_item);
        deleteMessage.setText("Attention, " + trajet.getName() + ", sera définitivement perdu !");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Effacer", (dialog, which) -> {
            Toast toast = Toast.makeText(this, "Trajet effacé.", Toast.LENGTH_LONG);

            viewModel.deleteTrajetViewModel(trajet, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "deleteTrajet: success");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "deleteTrajet: failure", e);
                        }
                    }
            );
            toast.show();
        });
        alertDialog.show();
    }

}
