package com.example.pablocano.sanbotmovement;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;

/**
 * Activity to characterize the IR sensor of Sanbot
 *
 * @author Pablo Cano Montecinos
 * @version 1.0
 */
public class MovementActivity extends TopBaseActivity {

    /**
     * The connection with the hardware API of Sanbot
     */
    private WheelMotionManager wheelMotionManager;

    /**
     * All the buttons present in the screen
     */
    private Button btnNAWMStart, btnNAWMStop, btnRAWMStart, btnRAWMStop, btnDWMStart, btnDWMStop;

    /**
     * All the edit text present in the screen
     */
    private EditText edtNAWM, edtRAWM, edtDWM;

    /**
     * All the spinners present in the screen
     */
    private Spinner spnNAWMType, spnNAWMVel, spnRAWMType, spnRAWMVel, spnDWMType, spnDWMVel;

    /**
     * The noAngleWheelMotion command that is used to control the robot motion
     */
    private NoAngleWheelMotion noAngleWheelMotion;

    /**
     * The relativeAngleWheelMotion command that is used to control the robot motion
     */
    private RelativeAngleWheelMotion relativeAngleWheelMotion;

    /**
     * The distanceWheelMotion command that is used to control the robot motion
     */
    private DistanceWheelMotion distanceWheelMotion;

    /**
     * The only handler of the spinners
     */
    private CustomOnItemSelectedListener itemSelectedListener;

    /**
     * The only handler of the edit text
     */
    private CustomOnEditorActionListener editorActionListener;

    /**
     * Method called on the creation of the activity
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Creation steps
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_viewer);

        // Init components
        initializeComponents();

        // Init commands
        initializeCommands();

        // Connect with the Sanbot hardware
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

        // Necessary for the app to not be minimized
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Method called on the start of the activity
     */
    @Override
    protected void onMainServiceConnected(){

    }

    /**
     * Initialize all the commands of this class with default values
     */
    private void initializeCommands() {
        noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_RUN, 0, 0);
        relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_STOP, 0, 0);
        distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_STOP_RUN, 0, 0);
    }

    /**
     * Initialize every component of the app
     */
    private void initializeComponents(){
        // Custom Listeners
        itemSelectedListener = new CustomOnItemSelectedListener(this);
        editorActionListener = new CustomOnEditorActionListener(this);

        // Spinners
        spnNAWMType = (Spinner) findViewById(R.id.nAWM_Action_spinner);
        spnNAWMVel = (Spinner) findViewById(R.id.nAWM_Velocity_spinner);
        spnRAWMType = (Spinner) findViewById(R.id.rAWM_Action_spinner);
        spnRAWMVel = (Spinner) findViewById(R.id.rAWM_Velocity_spinner);
        spnDWMType = (Spinner) findViewById(R.id.dWM_Action_spinner);
        spnDWMVel = (Spinner) findViewById(R.id.dWM_Velocity_spinner);

        // Buttons
        btnNAWMStart =(Button) findViewById(R.id.nAWM_start);
        btnNAWMStop = (Button) findViewById(R.id.nAWM_stop);
        btnRAWMStart =(Button) findViewById(R.id.rAWM_start);
        btnRAWMStop =(Button) findViewById(R.id.rAWM_stop);
        btnDWMStart =(Button) findViewById(R.id.dWM_start);
        btnDWMStop =(Button) findViewById(R.id.dWM_stop);

        // Edit text
        edtNAWM = (EditText) findViewById(R.id.nAWM_edit);
        edtRAWM = (EditText) findViewById(R.id.rAWM_edit);
        edtDWM = (EditText) findViewById(R.id.dWM_edit);

        // Set spinners listeners
        spnNAWMType.setOnItemSelectedListener(itemSelectedListener);
        spnNAWMVel.setOnItemSelectedListener(itemSelectedListener);
        spnRAWMType.setOnItemSelectedListener(itemSelectedListener);
        spnRAWMVel.setOnItemSelectedListener(itemSelectedListener);
        spnDWMType.setOnItemSelectedListener(itemSelectedListener);
        spnDWMVel.setOnItemSelectedListener(itemSelectedListener);

        // Set the edit text listeners
        edtNAWM.setOnEditorActionListener(editorActionListener);
        edtRAWM.setOnEditorActionListener(editorActionListener);
        edtDWM.setOnEditorActionListener(editorActionListener);
    }

    /**
     * Action handler for the start NoAngleWheelMotion button
     * @param view The view that was call
     */
    public void startNAWM(View view){
        wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
    }

    /**
     * Action handler for the stop NoAngleWheelMotion button
     * @param view The view that was call
     */
    public void stopNAWM(View view){
        noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_STOP_RUN);
        wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
    }

    /**
     * Action handler for the start RelativeAngleWheelMotion button
     * @param view The view that was call
     */
    public void startRAWM(View view){
        wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
    }

    /**
     * Action handler for the stop RelativeAngleWheelMotion button
     * @param view The view that was call
     */
    public void stopRAWM(View view){
        relativeAngleWheelMotion.setAction(RelativeAngleWheelMotion.TURN_STOP);
        wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
    }

    /**
     * Action handler for the start DistanceWheelMotion button
     * @param view The view that was call
     */
    public void startDWM(View view){
        wheelMotionManager.doDistanceMotion(distanceWheelMotion);
    }

    /**
     * Action handler for the stop DistanceWheelMotion button
     * @param view The view that was call
     */
    public void stopDWM(View view){
        distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_STOP_RUN);
        wheelMotionManager.doDistanceMotion(distanceWheelMotion);
    }

    /**
     * Access to the command NoAngleWheelMotion
     * @return the instance of the command present in this class
     */
    public NoAngleWheelMotion getNoAngleWheelMotion(){
        return noAngleWheelMotion;
    }

    /**
     * Access to the command RelativeAngleWheelMotion
     * @return the instance of the command present in this class
     */
    public RelativeAngleWheelMotion getRelativeAngleWheelMotion(){
        return relativeAngleWheelMotion;
    }

    /**
     * Access to the command DistanceWheelMotion
     * @return the instance of the command present in this class
     */
    public DistanceWheelMotion getDistanceWheelMotion(){
        return distanceWheelMotion;
    }
}
