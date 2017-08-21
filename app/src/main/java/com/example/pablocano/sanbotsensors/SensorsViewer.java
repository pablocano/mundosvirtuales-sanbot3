package com.example.pablocano.sanbotsensors;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.InfrareListener;

public class SensorsViewer extends TopBaseActivity {

    GridView sensorsGrid;
    HardWareManager hardWareManager;
    SensorItemAdapter sensorItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_viewer);

        sensorsGrid = (GridView) findViewById(R.id.sensorsGridView);
        sensorItemAdapter = new SensorItemAdapter(getApplicationContext());
        sensorsGrid.setAdapter(sensorItemAdapter);

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);

        initSensorListeners();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onMainServiceConnected(){
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
}
