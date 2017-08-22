package com.example.pablocano.sanbotmovement;


import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.qihancloud.opensdk.base.TopBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.qihancloud.opensdk.function.unit.HardWareManager;
import com.qihancloud.opensdk.function.unit.WheelMotionManager;
import com.qihancloud.opensdk.function.unit.interfaces.hardware.InfrareListener;

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
    WheelMotionManager wheelMotionManager;

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


        // Access to the buttons


        // Access to the text views


        // Connect with the Sanbot hardware
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

        // Initialize variables


        // Necessary for the app to not be minimized
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Method called on the start of the activity
     */
    @Override
    protected void onMainServiceConnected(){

    }

    public void start(View view){
        NoAngleWheelMotion noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_FORWARD_RUN,10,3000);
        wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
    }

    public void stop(View view){
        NoAngleWheelMotion noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_STOP_RUN,(byte)0);
        wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
    }
}
