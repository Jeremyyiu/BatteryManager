package com.example.jeremy.controller;


import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.controller.controller.AudioController;
import com.example.jeremy.controller.utils.Preferences;
import com.example.jeremy.controller.view.GeofencesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.jeremy.controller.JnaBatteryManagerApplication.getAppContext;
import static com.example.jeremy.controller.utils.Constants.SILENT_MODE;

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
    @BindView(R.id.batteryPlugged_text2)
    TextView batteryPlugged;
    @BindView(R.id.batteryProgressBar)
    ProgressBar batteryProgressBar;
    @BindView(R.id.batteryCurrentValue)
    TextView batteryCurrentValue;
 /*   @BindView(R.id.batteryTotal)
    TextView batteryTotal;
    @BindView(R.id.batteryCurrent)
    TextView batteryCurrent;*/
    @BindView(R.id.lowBatMuteText)
    TextView lowBatMute;
    @BindView(R.id.lowBatMuteSwitch)
    SwitchCompat lowBatMuteSwitch;
    @BindView(R.id.lowBatBtlSwitch)
    SwitchCompat lowBatBtlSwitch;
    @BindView(R.id.lowWifiTrigSwitch)
    SwitchCompat lowBatWifiSwitch;
    @BindView(R.id.nightModeText)
    TextView nightModeText;


    private Context mContext;
    private Unbinder unbinder;
    private AudioController audioController = null;

    // Filters for broadcast receiver
    IntentFilter intentFilter = null;
    BroadcastReceiver mReceiver;
    BatteryService batteryService;
    Bundle extras = null;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        unbinder = ButterKnife.bind(this, view);
        batteryService = new BatteryService();
        audioController = AudioController.getInstance(mContext);
        preferences =
                PreferenceManager.getDefaultSharedPreferences(getAppContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lowBatWifiSwitch.setChecked(preferences.getBoolean(Preferences.WIFI_LOW_BAT_TRIGGER, false));
        lowBatBtlSwitch.setChecked(preferences.getBoolean(Preferences.BLUETOOTH_LOW_BAT_TRIGGER, false));
        lowBatMuteSwitch.setChecked(preferences.getBoolean(Preferences.SILENT_LOW_BAT_TRIGGER, false));
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
            int plugged = extras.getInt("plugged");

            batteryTech.setText(technology);

            batteryProgressBar.setProgress((int) (batteryPct * 100));
            batteryCurrentValue.setText("" + (int) (batteryPct * 100));

            batteryHealth.setTextColor(health.equals("Good") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red));
            batteryHealth.setText(health);

            Float floatVoltage = (float) (voltage) / 1000;
            batteryVoltage.setText("" + floatVoltage + " V");

            Float floatTemperature = (float) (temperature) / 10;
            if (floatTemperature > 45) {
                batteryTemp.setTextColor(getResources().getColor(R.color.red));
            } else if (floatTemperature <= 45 && floatTemperature > 35) {
                batteryTemp.setTextColor(getResources().getColor(R.color.yellow));
            } else {
                batteryTemp.setTextColor(getResources().getColor(R.color.green));
            }
            batteryTemp.setText("" + floatTemperature + " °C");

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    batteryPlugged.setText("AC");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    batteryPlugged.setText("USB");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    batteryPlugged.setText("Wireless");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                default:
                    batteryPlugged.setText("Not Plugged");
                    batteryPlugged.setTextColor(Color.LTGRAY);
                    break;
            }
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getBatteryCurrent();
            }*/
        }
    }

/*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getBatteryCurrent() {
        BatteryManager mBatteryManager = (BatteryManager) getActivity().getSystemService(Context.BATTERY_SERVICE);
        int avgCurrent = 0, currentNow = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (mBatteryManager != null) {
                avgCurrent = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
                currentNow = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
            }
        }

        batteryTotal.setText("Avg charge current: " + avgCurrent + " mAh");
        batteryCurrent.setText("Charge Current: " + currentNow + " mAh");
    }*/

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
                    plugged_result = extras.getInt("plugged");
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
                    edit.execute(plugged_result, health_result, scale_result, level_result, temperature_result, voltage_result, plugged_result);
                    editTechItem.execute(technology_result);
                }
            }
        };
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    public double getBatteryCapacity() {
        Object mPowerProfile_ = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
            return batteryCapacity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
                    plugged = params[6];
                }

            });
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            batteryProgressBar.setProgress((int) (batteryPct * 100));
            batteryCurrentValue.setText("" + (int) (batteryPct * 100));

            batteryHealth.setTextColor(health.equals("Good") ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red));
            batteryHealth.setText(health);

            Float floatVoltage = (float) (voltage) / 1000;
            batteryVoltage.setText("" + floatVoltage + " V");

            Float floatTemperature = (float) (temperature) / 10;
            if (floatTemperature > 45) {
                batteryTemp.setTextColor(getResources().getColor(R.color.red));
            } else if (floatTemperature <= 45 && floatTemperature > 35) {
                batteryTemp.setTextColor(getResources().getColor(R.color.yellow));
            } else {
                batteryTemp.setTextColor(getResources().getColor(R.color.green));
            }
            batteryTemp.setText("" + floatTemperature + " °C");

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    batteryPlugged.setText("AC");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    batteryPlugged.setText("USB");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    batteryPlugged.setText("Wireless");
                    batteryPlugged.setTextColor(getResources().getColor(R.color.green));
                    break;
                default:
                    batteryPlugged.setText("Not Plugged");
                    batteryPlugged.setTextColor(Color.LTGRAY);
                    break;
            }

        /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getBatteryCurrent();
            }*/
        }
    }

    public void showGeofencingFrag(View view) {
        Intent i = new Intent(getActivity(), GeofencesActivity.class);
        startActivity(i);
    }

    @OnCheckedChanged(R.id.lowBatBtlSwitch)
    public void bluetoothLowBatTrigger(boolean checked) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Preferences.BLUETOOTH_LOW_BAT_TRIGGER, checked);
        editor.apply();
    }

    @OnCheckedChanged(R.id.lowWifiTrigSwitch)
    public void wifiLowBatTrigger(boolean checked) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Preferences.WIFI_LOW_BAT_TRIGGER, checked);
        editor.apply();
    }

    @OnCheckedChanged(R.id.lowBatMuteSwitch)
    public void muteLowBatTrigger(boolean checked) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Preferences.SILENT_LOW_BAT_TRIGGER, checked);
        editor.apply();
    }

    @OnClick({R.id.nightModeText})
    public void nightMode() {
        Intent i = new Intent(mContext, com.example.jeremy.controller.Preferences.class);
        startActivity(i);
    }

    @OnClick({R.id.lowBatMuteText})
    public void setPhoneToSilent(View view) {
        audioController.mutePhone(SILENT_MODE);
    }

    public int getCharge() {
        BatteryManager batteryManager = (BatteryManager) getActivity().getSystemService(Context.BATTERY_SERVICE);
        int energy = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            energy = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        }
        return energy;
    }

    private void showPowerUsageSummary() {
        try {
            Intent powerUsageSummary = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            Context context = getContext();
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(powerUsageSummary, 0);
            if (resolveInfo != null) {
                startActivity(powerUsageSummary);
            }

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Battery");
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
