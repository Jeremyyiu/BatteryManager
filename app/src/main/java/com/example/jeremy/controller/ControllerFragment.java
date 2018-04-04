package com.example.jeremy.controller;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeremy.controller.controller.BluetoothController;
import com.example.jeremy.controller.controller.DisplayController;
import com.example.jeremy.controller.controller.GPSController;
import com.example.jeremy.controller.controller.NetworkController;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


/**
 * Fragment class for controller
 */
@RuntimePermissions
public class ControllerFragment extends Fragment {

    GPSController gpsController = null;
    NetworkController networkController = null;
    BluetoothController bluetoothController = null;
    DisplayController displayController = null;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        gpsController = new GPSController(this.getActivity(), mContext);
        networkController = new NetworkController(this.getActivity(), mContext);
        bluetoothController = new BluetoothController(this.getActivity());
        displayController = new DisplayController(this.getActivity(), mContext);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controller, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControllerItems();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void initControllerItems() {
        networkController.initNetworkItems();
        bluetoothController.initBluetoothItems();
        gpsController.initGpsItems();
        displayController.initDisplayItems();
    }

/**
 * @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * // NOTE: delegate the permission handling to generated method
 * ControllerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
 * }
 **/


}
