package com.example.pablocano.sanbotsensors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/**
 * @author PabloCano
 */

public class SensorItemAdapter extends BaseAdapter {
    private int sensors_values[];
    private LayoutInflater inflater;

    private boolean inUse;

    public SensorItemAdapter(Context context){
        this.sensors_values = new int[18];
        inflater = (LayoutInflater.from(context));
        inUse = false;
    }

    @Override
    public int getCount(){
        return sensors_values.length;
    }

    @Override
    public Object getItem(int i){
        return null;
    }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if (view == null){
            view = inflater.inflate(R.layout.sensor, null);
        }

        TextView sensorName = (TextView) view.findViewById(R.id.sensor_name);
        TextView sensorValue = (TextView) view.findViewById(R.id.sensor_value);
        sensorName.setText(String.format(Locale.ENGLISH, "Sensor number %d",i));
        sensorValue.setText(String.format(Locale.ENGLISH, "%d cms.",sensors_values[i]),TextView.BufferType.EDITABLE);
        return view;
    }

    void setSensorValue(int sensor, int distance){
        if (inUse || sensor < 0 || sensor > 17) {
            return;
        }
        sensors_values[sensor] = distance;
    }

    @Override
    public void notifyDataSetChanged(){
        inUse = true;
        super.notifyDataSetChanged();
        inUse = false;
    }
}

