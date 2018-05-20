package com.example.jeremy.controller;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment class for graphs
 */
public class GraphsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //showPowerUsageSummary();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_geofencing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showPowerUsageSummary() {
        try {
            Intent powerUsageSummary = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            Context context = getContext();
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(powerUsageSummary, 0);
            if (resolveInfo != null) {
                startActivity(powerUsageSummary);
            } else {
                //blah blah
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
