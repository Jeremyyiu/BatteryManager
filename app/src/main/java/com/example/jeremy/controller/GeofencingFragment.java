package com.example.jeremy.controller;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeremy.controller.model.CustomProfile;
import com.example.jeremy.controller.view.GeofencesActivity;
import com.example.jeremy.controller.view.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment class for graphs
 */
public class GeofencingFragment extends Fragment {

    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.geofencingText)
    TextView geofencingText;

    @BindView(R.id.geofencingSettingsText)
    TextView geofencingSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_geofencingsection, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @OnClick({R.id.geofencingText})
    public void geofencingLaunch() {
        Intent i = new Intent(mContext, GeofencesActivity.class);
        startActivity(i);

    }
    @OnClick({R.id.geofencingSettingsText})
    public void geofencingSettingLaunch() {
        Intent i = new Intent(mContext, SettingsActivity.class);
        startActivity(i);
    }
}
