package com.example.pablocano.sanbotmovement;

import android.view.View;
import android.widget.AdapterView;

import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;

/**
 * Custom class that implements the OnItemSelectedListener, to be used in every spinner of this app
 * @author Pablo Cano M.
 * @version 1.0
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    /**
     * Reference to the main activity of the app
     */
    private MovementActivity movementActivity;

    public CustomOnItemSelectedListener(MovementActivity movementActivity){
        this.movementActivity = movementActivity;
    }

    /**
     * Callback function which is called when one of the item is selected
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param lId The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long lId) {
        int id= parent.getId();
        if (id == R.id.nAWM_Action_spinner){
            NoAngleWheelMotion noAngleWheelMotion = movementActivity.getNoAngleWheelMotion();
            switch (position){
                case 0:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_FORWARD_RUN);
                    break;
                case 1:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_BACK_RUN);
                    break;
                case 2:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_LEFT_CIRCLE);
                    break;
                case 3:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_RIGHT_CIRCLE);
                    break;
                case 4:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_LEFT_FORWARD_RUN);
                    break;
                case 5:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_RIGHT_FORWARD_RUN);
                    break;
                case 6:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_LEFT_BACK_RUN);
                    break;
                case 7:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_RIGHT_BACK_RUN);
                    break;
                case 8:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_TURN_LEFT);
                    break;
                case 9:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_TURN_RIGHT);
                    break;
                case 10:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_STOP_TURN);
                    break;
                case 11:
                    noAngleWheelMotion.setAction(NoAngleWheelMotion.ACTION_STOP_RUN);
                    break;
            }
        }
        else if (id == R.id.nAWM_Velocity_spinner){
            NoAngleWheelMotion noAngleWheelMotion = movementActivity.getNoAngleWheelMotion();
            if(position > 0) {
                noAngleWheelMotion.setSpeed((byte) (position + 1));
            }
            else{
                noAngleWheelMotion.setAction((byte) 0);
            }

        }
        else if (id == R.id.rAWM_Action_spinner){
            RelativeAngleWheelMotion relativeAngleWheelMotion = movementActivity.getRelativeAngleWheelMotion();
            switch (position){
                case 0:
                    relativeAngleWheelMotion.setAction(RelativeAngleWheelMotion.TURN_LEFT);
                    break;
                case 1:
                    relativeAngleWheelMotion.setAction(RelativeAngleWheelMotion.TURN_RIGHT);
                    break;
                case 2:
                    relativeAngleWheelMotion.setAction(RelativeAngleWheelMotion.TURN_STOP);
                    break;
            }
        }
        else if (id == R.id.rAWM_Velocity_spinner){
            RelativeAngleWheelMotion relativeAngleWheelMotion = movementActivity.getRelativeAngleWheelMotion();
            if(position > 0) {
                relativeAngleWheelMotion.setSpeed((byte) (position + 1));
            }
            else{
                relativeAngleWheelMotion.setAction((byte) 0);
            }
        }
        else if (id == R.id.dWM_Action_spinner){
            DistanceWheelMotion distanceWheelMotion = movementActivity.getDistanceWheelMotion();
            switch (position){
                case 0:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_FORWARD_RUN);
                    break;
                case 1:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_BACK_RUN);
                    break;
                case 2:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_LEFT_FORWARD_RUN);
                    break;
                case 3:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_RIGHT_FORWARD_RUN);
                    break;
                case 4:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_LEFT_BACK_RUN);
                    break;
                case 5:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_RIGHT_BACK_RUN);
                    break;
                case 6:
                    distanceWheelMotion.setAction(DistanceWheelMotion.ACTION_STOP_RUN);
                    break;
            }
        }
        else if (id == R.id.dWM_Velocity_spinner){
            DistanceWheelMotion distanceWheelMotion = movementActivity.getDistanceWheelMotion();
            if(position > 0) {
                distanceWheelMotion.setSpeed((byte) (position + 1));
            }
            else{
                distanceWheelMotion.setAction((byte) 0);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
