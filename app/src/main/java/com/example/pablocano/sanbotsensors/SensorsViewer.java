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
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.InfrareListener;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Activity to characterize the IR sensor of Sanbot
 *
 * @author Pablo Cano Montecinos
 * @version 1.0
 */
public class SensorsViewer extends TopBaseActivity {

    /**
     * The connection with the hardware API of Sanbot
     */
    HardWareManager hardWareManager;

    /**
     * The button which select a sensor
     */
    Button selectButton;

    /**
     * The button which start the analysis of a sensor
     */
    Button analysisButton;

    /**
     * The editable field used to select a sensor
     */
    EditText sensorSelector;

    /**
     * Text View used to show to the user the current selected sensor
     */
    TextView selectedSensorView;

    /**
     * Text View used to show to the user the values measured by the selected sensor
     */
    TextView sensorValueView;

    TextView meanView;
    TextView stdView;
    TextView samplesView;

    /**
     * The selected sensor index
     */
    int selectedSensor;

    /**
     * A list of samples used to calculate the mean and standard deviation of a sensor
     */
    ArrayList<Integer> listOfSamples;

    /**
     * If the sensor is been processing
     */
    boolean processing;

    /**
     * Method called on the creation of the activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Creation steps
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_viewer);

        // Access to the edit text
        sensorSelector = (EditText) findViewById(R.id.sensor_selector);

        // Access to the buttons
        selectButton = (Button) findViewById(R.id.select_button);
        analysisButton = (Button) findViewById(R.id.analysis_button);

        // Access to the text views
        sensorValueView = (TextView) findViewById(R.id.sensor_value);
        selectedSensorView = (TextView) findViewById(R.id.selected_sensor);
        meanView = (TextView) findViewById(R.id.mean_value);
        stdView = (TextView) findViewById(R.id.std_value);
        samplesView = (TextView) findViewById(R.id.n_samples_value);

        // Connect with the Sanbot hardware
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        initSensorListeners();

        // Initialize variables
        selectedSensor = 0;
        listOfSamples = new ArrayList<>();
        processing = false;

        // Necessary for the app to not be minimized
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Method called on the start of the activity
     */
    @Override
    protected void onMainServiceConnected(){
        selectButton.setEnabled(false);
        analysisButton.setEnabled(false);
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
                            selectedSensorView.setText(v.getText());
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

            /**
             * Method called when the text in the sensor selector has been edited.
             * @param s The new text in the sensor selector
             */
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int value = Integer.parseInt(s.toString());
                    if (value > 0 && value < 18) {
                        analysisButton.setEnabled(true);
                        selectButton.setEnabled(true);
                    }
                    else{
                        analysisButton.setEnabled(false);
                        selectButton.setEnabled(false);
                    }
                } catch (NumberFormatException e) {
                    selectButton.setEnabled(false);
                    analysisButton.setEnabled(false);
                }
            }
        });
    }

    /**
     * Start the communication with the Sanbot hardware
     */
    private void initSensorListeners(){
        hardWareManager.setOnHareWareListener(new InfrareListener() {

            /**
             * Method called when one of the IR sensor has a new value
             * @param part The index of the sensor which sent the data
             * @param distance The distance measured by the current sensor
             */
            @Override
            public void infrareDistance(int part, int distance) {
                if(selectedSensor > 0 && part == selectedSensor){
                    sensorValueView.setText(String.format(Locale.ENGLISH,"%d",distance));
                    if(processing){
                        listOfSamples.add(distance);
                        samplesView.setText(String.format(Locale.ENGLISH, "%d", listOfSamples.size()));
                    }
                }
            }
        });
    }

    /**
     * Handles the select button
     * @param view
     */
    public void selectSensor(View view){
        try {
            int value = Integer.parseInt(String.valueOf(sensorSelector.getText()));
            if (value > 0 && value < 18) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(sensorSelector.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                selectedSensor = value;
                selectedSensorView.setText(sensorSelector.getText());
            }
        } catch (NumberFormatException e) {

        }
    }

    /**
     * Handles the start button
     * @param view
     */
    public void startAnalysis(View view){
        if(processing){
            processing = false;
            analysisButton.setText(R.string.start_button);

            double mean = 0;
            for (int sample : listOfSamples){
                mean += sample;
            }

            mean = mean / listOfSamples.size();

            String meanString = doubleToString(mean);
            if(meanString != null) {
                meanView.setText(meanString);
            }

            double std = 0;
            for (int sample : listOfSamples){
                double diff = (sample - mean);
                std += diff * diff;
            }

            std /= listOfSamples.size() - 1;
            std = Math.sqrt(std);

            String stdString = doubleToString(std);
            if(stdString != null) {
                stdView.setText(stdString);
            }

        }
        else{
            processing = true;
            analysisButton.setText(R.string.stop_button);
            listOfSamples.clear();
            meanView.setText("");
            stdView.setText("");
        }
    }

    private static String doubleToString(Double d) {
        if (d == null)
            return null;
        if (d.isNaN() || d.isInfinite())
            return d.toString();

        // pre java 8, a value of 0 would yield "0.0" below
        if (d.doubleValue() == 0)
            return "0";
        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }
}
