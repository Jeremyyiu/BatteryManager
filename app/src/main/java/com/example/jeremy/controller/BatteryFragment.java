package com.example.jeremy.controller;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment class for battery
 */
public class BatteryFragment extends Fragment {
    @BindView(R.id.batteryTemp_Text2)
    TextView batteryTemp;
    @BindView(R.id.batteryHealth_Text2)
    TextView batteryHealth;
    @BindView(R.id.batteryTech_Text2)
    TextView batteryTech;
    @BindView(R.id.batteryVoltage_Text2)
    TextView batteryVoltage;
    @BindView(R.id.batteryProgressBar)
    ProgressBar batteryProgressBar;
    @BindView(R.id.batteryCurrentValue)
    TextView batteryCurrentValue;

    private Context mContext;
    private Unbinder unbinder;

    // Filters for broadcast receiver
    IntentFilter intentFilter = null;
    BroadcastReceiver mReceiver;
    BatteryService batteryService;
    Bundle extras = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        unbinder = ButterKnife.bind(this, view);
        batteryService = new BatteryService();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBatteryItems();
        if (extras != null) {
            String health = getHealth(extras.getInt("health"));
            int level = extras.getInt("level");
            String technology = extras.getString("technology");
            int temperature = extras.getInt("temperature");
            int voltage = extras.getInt("voltage");
            float batteryPct = extras.getFloat("batteryPct");

            batteryTech.setText(technology);


            batteryProgressBar.setProgress((int) (batteryPct * 100));
            batteryCurrentValue.setText("" + (int) (batteryPct * 100));

            batteryHealth.setTextColor(health.equals("Good") ? Color.GREEN : Color.RED);
            batteryHealth.setText(health);

            Float floatVoltage = (float) (voltage) / 1000;
            batteryVoltage.setText("" + floatVoltage + " V");

            Float floatTemperature = (float) (temperature) / 10;
            if (floatTemperature > 45) {
                batteryTemp.setTextColor(Color.RED);
            } else if (floatTemperature <= 45 && floatTemperature > 35) {
                batteryTemp.setTextColor(Color.YELLOW);
            } else {
                batteryTemp.setTextColor(Color.GREEN);
            }
            batteryTemp.setText("" + floatTemperature + " °C");
        }
    }


    public String getHealth(int health) {
        String batteryStatus = "";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                batteryStatus = "Cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                batteryStatus = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                batteryStatus = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                batteryStatus = "Overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                batteryStatus = "Unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                batteryStatus = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                batteryStatus = "Unspecified Failure";
                break;
            default:
                batteryStatus = "Error - Could not receive";
                break;
        }
        return batteryStatus;
    }

    public void initBatteryItems() {
        batteryProgressBar.setMax(100);
        Intent i = new Intent(this.getActivity(), batteryService.getClass());
        getActivity().startService(i);

        // Set and register broadcast receiver
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.jeremy.controller.batteryservice");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                extras = intent.getExtras();

                if (extras != null) {
                    int plugged_result, status_result, health_result, level_result, scale_result, temperature_result, voltage_result;
                    boolean present_result;
                    String technology_result;

                    present_result = extras.getBoolean("present");
                    status_result = extras.getInt("status");
                    plugged_result = extras.getInt("plugged");
                    health_result = extras.getInt("health");
                    scale_result = extras.getInt("scale");
                    level_result = extras.getInt("level");
                    technology_result = extras.getString("technology");
                    temperature_result = extras.getInt("temperature");
                    voltage_result = extras.getInt("voltage");

                    extras.putFloat("batteryPct", level_result / (float) scale_result);
                    editBatteryItems edit = new editBatteryItems();
                    editBatteryStringItems editTechItem = new editBatteryStringItems();

                    Log.d("Debug info", "Received signal: " + present_result);

                    //Update the UI with the new values
                    edit.execute(plugged_result, health_result, scale_result, level_result, temperature_result, voltage_result);
                    editTechItem.execute(technology_result);
                }
            }
        };
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    public class editBatteryStringItems extends AsyncTask<String, String, Boolean> {
        String technology;

        @Override
        protected Boolean doInBackground(final String... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    technology = params[0];
                }
            });

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            batteryTech.setText(technology);
        }

    }

    public class editBatteryItems extends AsyncTask<Integer, String, Boolean> {
        int level = 0;
        String health = "";
        int voltage = 0;
        int temperature = 0;
        int plugged = 0;
        int scale = 0;
        float batteryPct = 0;

        @Override
        protected Boolean doInBackground(final Integer... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    plugged = params[0];
                    scale = params[2];
                    level = params[3];

                    batteryPct = level / (float) scale;
                    health = getHealth(params[1]);
                    temperature = params[4];
                    voltage = params[5];
                }

            });
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            batteryProgressBar.setProgress((int) (batteryPct * 100));
            batteryCurrentValue.setText("" + (int) (batteryPct * 100));

            batteryHealth.setTextColor(health.equals("Good") ? Color.GREEN : Color.RED);
            batteryHealth.setText(health);

            Float floatVoltage = (float) (voltage) / 1000;
            batteryVoltage.setText("" + floatVoltage + " V");

            Float floatTemperature = (float) (temperature) / 10;
            if (floatTemperature > 45) {
                batteryTemp.setTextColor(Color.RED);
            } else if (floatTemperature <= 45 && floatTemperature > 35) {
                batteryTemp.setTextColor(Color.YELLOW);
            } else {
                batteryTemp.setTextColor(Color.GREEN);
            }
            batteryTemp.setText("" + floatTemperature + " °C");
        }
    }

    /**
     * onDestroy: unregister broadcast receiver
     */
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //TODO: save variable values
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
