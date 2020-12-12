package com.example.ourapplication_kohl_roux_m.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.adapter.RecyclerAdapter;
import com.example.ourapplication_kohl_roux_m.dbClass.asynch.car.CreateCar;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.util.OnAsyncEventListener;
import com.example.ourapplication_kohl_roux_m.viewModel.car.CarMyListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ChooseNewCar extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";

    // You need create a new directory "liste" and paste in "csvFile"
    // in the directory " /Android/data/com.example.ourapplication_kohl_roux_m "
    // in the emulated storage or in your local storage of your device
    private final static String fileListCars = "/liste/csvstock/listevoituresplugin_virgule.csv";
    private final static String fileListWheelSize = "/liste/csvstock/taillesroues.csv";

    private CarEntity carToAdd;
    private List<CarEntity> cars;
    private RecyclerAdapter<CarEntity> adapterCar;
    private CarMyListViewModel viewModelAdd;

    private TextView nickNameEditText;
    private AutoCompleteTextView autoCompTradeMark, autoCompModel, autoCompConsoFuel, autoCompBattery;
    private Spinner spinnerWheelW, spinnerWheelH, spinnerWheelD;

    private ArrayList <String> carFondation = new ArrayList<>();
    private ArrayList <String> wheels = new ArrayList<>();
    private List<String> listTradeMark, listModel, listConsoFuel, listBattery, listWheelW, listWheelH, listWheelD ;

    protected String carNickname, battery, consoFuel, model, tradeMark, wheelSize, wheelW, wheelH, wheelD;

    private ArrayAdapter<String> adapterTradeMark, adapterModel, adapterConsoFuel, adapterBattery, adapterWheelW, adapterWheelH, adapterWheelD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_new_car);

        setTitle(getString(R.string.add_car));

        initializeForm();

    }

    private void initializeForm() {

        nickNameEditText = findViewById(R.id.nickNameEdText);
        nickNameEditText.setText("");

        listTradeMark = new ArrayList<>();
        listModel = new ArrayList<>();
        listConsoFuel = new ArrayList<>();
        listBattery = new ArrayList<>();
        listWheelW = new ArrayList<>();
        listWheelH = new ArrayList<>();
        listWheelD = new ArrayList<>();

        autoCompTradeMark = findViewById(R.id.autoTextTradeMark);
        autoCompModel = findViewById(R.id.autoTextModel);
        autoCompConsoFuel = findViewById(R.id.autoTextConsofuel);
        autoCompBattery = findViewById(R.id.autoTextBattery);
        spinnerWheelW = findViewById(R.id.wheelWidth);
        spinnerWheelH = findViewById(R.id.wheelHeight);
        spinnerWheelD = findViewById(R.id.wheelDiameter);

        listTradeMark = extractListFromCsv("",0, 0, fileListCars, carFondation);
        adapterTradeMark = new ArrayAdapter
                (this, R.layout.row_listview, listTradeMark);
        autoCompTradeMark.setAdapter(adapterTradeMark);

        autoCompTradeMark.setCursorVisible(false);

        autoCompTradeMark.setOnItemClickListener((parent, view, place, id) -> {
            autoCompTradeMark.showDropDown();
            tradeMark = (String) parent.getItemAtPosition(place);
            listModel = extractListFromList(tradeMark, 0, 1, fileListCars, carFondation);
            adapterModel = new ArrayAdapter
                    (this, R.layout.row_listview, listModel);
            autoCompModel.setAdapter(adapterModel);

            autoCompModel.setCursorVisible(false);
            autoCompModel.showDropDown();

        });
        autoCompTradeMark.setOnClickListener(arg0 -> autoCompTradeMark.showDropDown());

        autoCompModel.setOnItemClickListener((parent, view, place, id) -> {
            autoCompModel.showDropDown();
            model = (String) parent.getItemAtPosition(place);
            listConsoFuel = extractListFromList(tradeMark, 0, 2, fileListCars, carFondation);

            adapterConsoFuel = new ArrayAdapter
                    (this, R.layout.row_listview, listConsoFuel);
            autoCompConsoFuel.setAdapter(adapterConsoFuel);

            autoCompConsoFuel.setCursorVisible(false);
            autoCompConsoFuel.showDropDown();

        });
        autoCompModel.setOnClickListener(arg0 -> autoCompModel.showDropDown());

        autoCompConsoFuel.setOnItemClickListener((parent, view, place, id) -> {
            autoCompConsoFuel.showDropDown();
            consoFuel = (String) parent.getItemAtPosition(place);

            listBattery = extractListFromList(tradeMark, 0, 3, fileListCars, carFondation);
            adapterBattery = new ArrayAdapter
                    (this, R.layout.row_listview, listBattery);
            autoCompBattery.setAdapter(adapterBattery);

            autoCompBattery.setCursorVisible(false);
            autoCompBattery.showDropDown();

        });
        autoCompConsoFuel.setOnClickListener(arg0 -> autoCompConsoFuel.showDropDown());

        autoCompBattery.setOnItemClickListener((parent, view, place, id) -> {
            autoCompBattery.showDropDown();
            battery = (String) parent.getItemAtPosition(place);
        });
        autoCompBattery.setOnClickListener(arg0 -> autoCompBattery.showDropDown());

        listWheelW = extractListFromCsv("", 0, 0, fileListWheelSize, wheels);
        adapterWheelW = new ArrayAdapter
                (this, R.layout.row_listview, listWheelW);
        spinnerWheelW.setAdapter(adapterWheelW);

        listWheelH = extractListFromCsv("", 0, 1, fileListWheelSize, wheels);
        adapterWheelH = new ArrayAdapter
                (this, R.layout.row_listview, listWheelH);
        spinnerWheelH.setAdapter(adapterWheelH);

        listWheelD = extractListFromCsv("", 0, 2, fileListWheelSize, wheels);
        adapterWheelD = new ArrayAdapter
                (this, R.layout.row_listview, listWheelD);
        spinnerWheelD.setAdapter(adapterWheelD);

        FloatingActionButton saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setVisibility(View.VISIBLE);
        saveBtn.setOnClickListener(view -> {
            wheelW = (String) spinnerWheelW.getSelectedItem();
            wheelH = (String) spinnerWheelH.getSelectedItem();
            wheelD = (String) spinnerWheelD.getSelectedItem();

            wheelSize = wheelW + "/" + wheelH + " - " + wheelD;
            carNickname = nickNameEditText.getText().toString();
            tradeMark = autoCompTradeMark.getText().toString();
            model = autoCompModel.getText().toString();
            consoFuel = autoCompConsoFuel.getText().toString();
            battery = autoCompBattery.getText().toString();

            carToAdd = new CarEntity(carNickname, tradeMark, model, Double.valueOf(consoFuel), Double.valueOf(battery), wheelSize, true, 0);

            addNewCar(carToAdd);
        });

    }

    private void addNewCar(final CarEntity newCar) {

        final CarEntity car = newCar;
        Toast toastSuccess = Toast.makeText(this, getString(R.string.add_car_succes), Toast.LENGTH_LONG);
        Toast toastFailed = Toast.makeText(this, getString(R.string.add_car_failed), Toast.LENGTH_LONG);

        new CreateCar(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, getString(R.string.add_car_s));
                    Intent intent = new Intent(ChooseNewCar.this, ListMyActiveCars.class);
                    startActivity(intent);
                    toastSuccess.show();
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, getString(R.string.add_car_f), e);
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


    private ArrayList<String> extractListFromCsv(String toCompare, int place, int rang, String path, ArrayList<String> tokens) {
        ArrayList<String> list = new ArrayList<>();
        listTradeMark = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(getExternalFilesDir(path));


            int cpt = 0;
            while (scanner.hasNextLine()) {
                tokens.add(scanner.nextLine());

                String tradeMarkTemp;
                tradeMarkTemp = tokens.get(cpt).split(";")[rang];

                if (!tradeMarkTemp.isEmpty() && (!list.contains(tradeMarkTemp) || list.isEmpty()))
                    list.add(tradeMarkTemp);

                cpt++;
            }
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_SHORT).show();
        }

        Collections.sort(list);
        return list;
    }

    private ArrayList<String> extractListFromList(String toCompare, int place, int rang, String path, ArrayList<String> tokens) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (toCompare.equals(tokens.get(i).split(";")[place]) && !list.contains(tokens.get(i).split(";")[rang]))
                list.add(tokens.get(i).split(";")[rang]);
        }

        Collections.sort(list);
        return list;
    }
}


