package com.example.jeremy.wifitoggler;

    import android.annotation.TargetApi;
    import android.content.Context;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.net.wifi.WifiManager;
    import android.os.Build;
    import android.os.Bundle;
    import android.provider.Settings;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        initSwitches();
    }

    private void initSwitches() {
        SwitchCompat wifiSwitch = (SwitchCompat) findViewById(R.id.wifiSwitch);
        SwitchCompat mobileNetworkSwitch = (SwitchCompat) findViewById(R.id.mobileNetworkSwitch);
        SwitchCompat airplaneNetworkSwitch = (SwitchCompat) findViewById(R.id.airplaneModeSwitch);

        wifiSwitch.setChecked(isWifiConnected());
        mobileNetworkSwitch.setChecked(isMobileNetworkConnected());
        airplaneNetworkSwitch.setChecked(isAirplaneModeOn(context));
    }

    //https://stackoverflow.com/questions/18735370/connectivitymanager-null-pointer
    private boolean isWifiConnected() {
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiInfo != null) {
            return wifiInfo.isConnected();
        }
        return false;
    }

    private boolean isMobileNetworkConnected() {
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(mobileNetworkInfo != null) {
            return mobileNetworkInfo.isConnected();
        }
        return false;
    }


    /** https://stackoverflow.com/questions/4319212/how-can-one-detect-airplane-mode-on-android */
    /** Flight mode still allows you to connect to wifi but not mobile networks */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings({"deprecation"})
    public static boolean isAirplaneModeOn(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        /* API 17 and above */
            return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } else {
        /* below */
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        }
    }

    /** TODO: Add flight mode listener */

    /** TODO: Mobile hotspot, grps - type of data connection **/
}