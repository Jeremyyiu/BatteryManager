package com.example.jeremy.controller;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.Slider;

/**
 * Created by Jeremy on 28/02/2018.
 */

public class BrightnessSettings extends DialogFragment implements Controller {
    //Slider to manipulate the brightness of the device
    Slider brightnessSlider = null;

    //current brightness settings
    int initialBrightness;

    private void initialSlider(){
        brightnessSlider.setValue(initialBrightness);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brightnessSlider = (Slider) brightnessSlider.findViewById(R.id.brightness_slider);

    }

    public View brightnessDialog(View view) {
        int value = brightnessSlider.getValue();
        mListener.onScreenComplete(value);
        getDialog().dismiss();

        // set logic for confirm and cancel buttons
        TextView text = (TextView)view.findViewById(R.id.confirm);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = brightnessSlider.getValue();
                mListener.onScreenComplete(value);
                getDialog().dismiss();
            }
        });
        text = (TextView)view.findViewById(R.id.cancel);
        text.setVisibility(View.VISIBLE);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }



    /**
     * The following function is realized by CustomSettings to get value from dialog
     * OnScreenCompleteListener
     * mListener
     * onAttach
     */
    public static interface OnScreenCompleteListener {
        public abstract void onScreenComplete(int param);
    }

    private OnScreenCompleteListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.mListener = (OnScreenCompleteListener)activity;
        }catch (final ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
}
