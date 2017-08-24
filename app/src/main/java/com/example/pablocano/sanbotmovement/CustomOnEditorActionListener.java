package com.example.pablocano.sanbotmovement;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.qihancloud.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.qihancloud.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.qihancloud.opensdk.mcu.utils.ConvertUtils;

/**
 * Custom implementation of the interface OnEditorActionListener
 * @author Pablo Cano M.
 * @version 1.0
 */
public class CustomOnEditorActionListener implements TextView.OnEditorActionListener {

    /**
     * Access to the main activity of this app
     */
    private MovementActivity movementActivity;

    /**
     * Constructor of the class
     * @param movementActivity  The main activity of this app.
     */
    public CustomOnEditorActionListener(MovementActivity movementActivity){
        this.movementActivity = movementActivity;
    }

    /**
     * Called when an action is being performed.
     *
     * @param v        The view that was clicked.
     * @param actionId Identifier of the action.  This will be either the
     *                 identifier you supplied, or {@link EditorInfo#IME_NULL
     *                 EditorInfo.IME_NULL} if being called due to the enter key
     *                 being pressed.
     * @param event    If triggered by an enter key, this is the event;
     *                 otherwise, this is null.
     * @return Return true if you have consumed the action, else false.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        InputMethodManager in = (InputMethodManager) movementActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(v.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        try{
            int value = Integer.parseInt(String.valueOf(v.getText()));

            if (v.getId() == R.id.nAWM_edit){
                NoAngleWheelMotion noAngleWheelMotion = movementActivity.getNoAngleWheelMotion();
                noAngleWheelMotion.setDuration(value);
            }
            else if (v.getId() == R.id.rAWM_edit){
                RelativeAngleWheelMotion relativeAngleWheelMotion = movementActivity.getRelativeAngleWheelMotion();
                byte[] byteArr = ConvertUtils.integerToByteArray(value, 2);
                relativeAngleWheelMotion.setLsbAngle(byteArr[1]);
                relativeAngleWheelMotion.setMsbAngle(byteArr[0]);
            }
            else if (v.getId() == R.id.dWM_edit){
                DistanceWheelMotion distanceWheelMotion = movementActivity.getDistanceWheelMotion();
                byte[] byteArr = ConvertUtils.integerToByteArray(value, 2);
                distanceWheelMotion.setLsbDistance(byteArr[1]);
                distanceWheelMotion.setMsbDistance(byteArr[0]);
            }

            return false;
        }
        catch (NumberFormatException e){
            return true;
        }
    }
}
