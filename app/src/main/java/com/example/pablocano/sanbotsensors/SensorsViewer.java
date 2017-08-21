package com.example.pablocano.sanbotsensors;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.InfrareListener;

public class SensorsViewer extends TopBaseActivity {

    GridView sensorsGrid;
    HardWareManager hardWareManager;
    SensorItemAdapter sensorItemAdapter;
    Button button;
    EditText sensorSelector;

    int selectedSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_viewer);

        selectedSensor = 0;

        sensorSelector = (EditText) findViewById(R.id.selected_sensor);
        button = (Button) findViewById(R.id.select_button);

        sensorsGrid = (GridView) findViewById(R.id.sensorsGridView);
        sensorItemAdapter = new SensorItemAdapter(getApplicationContext());
        sensorsGrid.setAdapter(sensorItemAdapter);

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);

        initSensorListeners();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onMainServiceConnected(){
        button.setEnabled(false);

        sensorSelector.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = true;
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    try {
                        int value = Integer.parseInt(String.valueOf(v.getText()));
                        if (value > 0 && value < 18) {
                            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                            selectedSensor = value;
                            handled = false;
                        }
                    } catch (NumberFormatException e) {

                    }
                }
                return handled;
            }
        });

        sensorSelector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int value = Integer.parseInt(s.toString());
                    if (value > 0 && value < 18) {
                        button.setEnabled(true);
                    }
                    else{
                        button.setEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    button.setEnabled(false);
                }
            }
        });
    }

    private void initSensorListeners(){
        hardWareManager.setOnHareWareListener(new InfrareListener() {
            @Override
            public void infrareDistance(int part, int distance) {
                sensorItemAdapter.setSensorValue(part,distance);
                sensorItemAdapter.notifyDataSetChanged();
            }
        });
    }

    public void selectSensor(View view){
        try {
            int value = Integer.parseInt(String.valueOf(sensorSelector.getText()));
            if (value > 0 && value < 18) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(sensorSelector.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                selectedSensor = value;
            }
        } catch (NumberFormatException e) {

        }
    }
}
