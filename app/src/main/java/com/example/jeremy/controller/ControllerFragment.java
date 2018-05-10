package com.example.jeremy.controller;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.DisplayController;
import com.example.jeremy.controller.controller.GPSController;
import com.example.jeremy.controller.controller.NetworkController;
import com.gc.materialdesign.views.Slider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.example.jeremy.controller.controller.DisplayController.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED;


/**
 * Fragment class for controller
 */
@RuntimePermissions
public class ControllerFragment extends Fragment {
    //Network
    @BindView(R.id.wifiSwitch)
    SwitchCompat wifiSwitch;
    @BindView(R.id.wifiText)
    TextView wifiText;

    @BindView(R.id.mobileDataSwitch)
    SwitchCompat mobileDataSwitch;
    @BindView(R.id.mobile_data_text)
    TextView mobileDataText;

    @BindView(R.id.flightModeSwitch)
    SwitchCompat flightModeSwitch;
    @BindView(R.id.flightModeText)
    TextView flightModeText;

    @BindView(R.id.hotspotSwitch)
    SwitchCompat hotspotSwitch;
    @BindView(R.id.hotspotText)
    TextView hotspotText;

    @BindView(R.id.dataUsageText)
    TextView dataUsageText;

    //GPS
    @BindView(R.id.gpsSwitch)
    SwitchCompat gpsSwitch;
    @BindView(R.id.gpsText)
    TextView gpsText;

    //Bluetooth
    @BindView(R.id.bluetoothSwitch)
    SwitchCompat bluetoothSwitch;
    @BindView(R.id.bluetoothText)
    TextView bluetoothText;

    //Display
    @BindView(R.id.brightnessSlider)
    Slider brightnessSlider;
    @BindView(R.id.brightnessText)
    TextView brightnessText;
    @BindView(R.id.autoBrightnessSwitch)
    SwitchCompat autoBrightnessSwitch;
    @BindView(R.id.monochromeSwitch)
    SwitchCompat monochromeSwitch;

    GPSController gpsController = null;
    NetworkController networkController = null;
    BluetoothController bluetoothController = null;
    DisplayController displayController = null;

    private Context mContext;
    private Unbinder unbinder;

    // Filters for broadcast receiver
    IntentFilter intentFilter = null;
    BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_controller, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        gpsController = new GPSController(mContext);
        networkController = new NetworkController(mContext);
        bluetoothController = new BluetoothController(mContext);
        displayController = new DisplayController(mContext, getActivity());

        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControllerItems();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void initControllerItems() {
        initNetworkItems();
        initBluetoothItems();
        initGpsItems();
        initDisplayItems();

        Intent i = new Intent(this.getActivity(), ControllerService.class);
        getActivity().startService(i);

        // Set and register broadcast receiver
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.jeremy.controller.controllerservice");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int wifi_result, tooth_result, data_result, plane_result, gps_result, hotspot_result;
                wifi_result = intent.getIntExtra("wifi_state", 0);
                data_result = intent.getIntExtra("data_state", 0);
                plane_result = intent.getIntExtra("plane_state", 0);
                gps_result = intent.getIntExtra("gps_state", 0);
                tooth_result = intent.getIntExtra("tooth_state", 0);
                hotspot_result = intent.getIntExtra("hotspot_state", 0);
                editControllerItems edit = new editControllerItems();
                //wifi,tooth,mobile data
                Log.d("debug info", "receive signal " + wifi_result);
                edit.execute(wifi_result, data_result, tooth_result, plane_result, hotspot_result, gps_result);
            }
        };
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    public void initNetworkItems() {
        initNetworkSwitch();
    }

    private void initNetworkSwitch() {
        wifiSwitch.setChecked(networkController.isWifiConnected());
        mobileDataSwitch.setChecked(networkController.isMobileNetworkConnected());
        flightModeSwitch.setChecked(networkController.isAirplaneModeOn(mContext));
        //TODO: hotspot
    }

    @OnClick({R.id.wifiText, R.id.wifi_icon})
    public void wifiSettings(View view) {
        networkController.wifiToggleSettings();
    }

    @OnCheckedChanged(R.id.wifiSwitch)
    public void wifiSwitchToggle(boolean checked) {
        networkController.toggleWiFi(checked);
    }

    @OnClick({R.id.flightModeRow})
    public void flightModeSettings(View view) {
        networkController.flightModeTogglePermission();
    }

    @OnClick({R.id.hotspotRow})
    public void hotspotSettings(View view) {
        networkController.hotspotTogglePermission();
    }

    @OnClick({R.id.dataUsageRow, R.id.mobileDataRow})
    public void dataUsageSettings(View view) {
        networkController.dataUsageSettings();
    }

    private void initBluetoothItems() {
        initBluetoothSwitch();
    }

    public void initBluetoothSwitch() {
        if (bluetoothController.isBluetoothOn()) {
            bluetoothSwitch.setChecked(true);
        }
    }

    public void initMonoChromeSwitch() {
        if (Settings.Secure.getInt(mContext.getContentResolver(), ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED, 0) == 0) {
            monochromeSwitch.setChecked(false);
        } else {
            monochromeSwitch.setChecked(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnCheckedChanged(R.id.monochromeSwitch)
    public void monochromeSwitchToggle(boolean checked) {
        //Checks if this app can modify system settings
        boolean canWriteSettings = mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == PackageManager.PERMISSION_GRANTED;

        if (canWriteSettings) {
            if (checked) {
                displayController.toggleMonochrome(1, mContext.getApplicationContext().getContentResolver());
            } else {
                displayController.toggleMonochrome(0, mContext.getApplicationContext().getContentResolver());
            }
        } else {
//If currently cant modify system settings, app will ask for permission
            displayController.showRootWorkaroundInstructions(mContext.getApplicationContext());
            monochromeSwitch.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.autoBrightnessSwitch)
    public void autoBrightnessSwitchToggle(boolean checked) {
        if(autoBrightnessSwitch.isChecked()) {
            displayController.setBrightnessToAuto();
        } else {
            displayController.setBrightnessToManual();
        }
    }

    @OnCheckedChanged(R.id.bluetoothSwitch)
    public void bluetoothSwitchToggle(boolean checked) {
        bluetoothController.toggleBluetooth(checked);
    }

    @OnClick({R.id.bluetoothText, R.id.bluetooth_icon})
    public void bluetoothSettings(View view) {
        bluetoothController.bluetoothSettings();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initDisplayItems() {
        displayController.init();
        //initBrightnessAdjuster();
        initMonoChromeSwitch();
    }

    public void initGpsItems() {
        initGpsSwitch();
    }

    private void initGpsSwitch() {
        boolean gpsStatus = gpsController.isGPSOn();
        gpsSwitch.setChecked(gpsStatus);
    }

    @OnClick({R.id.gpsRow})
    public void dataRoamingSettings(View view) {
        gpsController.toggleGPS();
    }

    /**
     * editControllerItems: Async task for toggling the switches
     */
    public class editControllerItems extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(final Integer... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < params.length; i++) {
                        switch (i) {
                            case 0:
                                if (params[i] == 1) {
                                    wifiSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    wifiSwitch.setChecked(false);
                                }
                                break;
                            case 1:
                                if (params[i] == 1) {
                                    mobileDataSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    mobileDataSwitch.setChecked(false);
                                }
                                break;
                            case 2:
                                if (params[i] == 1) {
                                    bluetoothSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    bluetoothSwitch.setChecked(false);
                                }
                                break;
                            case 3:
                                if (params[i] == 1) {
                                    flightModeSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    flightModeSwitch.setChecked(false);
                                }
                                break;
                            case 4:
                                if (params[i] == 1) {
                                    hotspotSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    hotspotSwitch.setChecked(false);
                                }
                                break;
                            case 5:
                                if (params[i] == 1) {
                                    gpsSwitch.setChecked(true);
                                } else if (params[i] == -1) {
                                    gpsSwitch.setChecked(false);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

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

/**
 * @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * // NOTE: delegate the permission handling to generated method
 * ControllerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
 * }
 **/
}
