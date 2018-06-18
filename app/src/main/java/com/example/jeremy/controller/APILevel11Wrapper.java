/*
 * Copyright 2013 Thomas Hoffmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jeremy.controller;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.widget.NumberPicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
abstract class APILevel11Wrapper {

    static void showNumberPicker(final Context c, final SharedPreferences prefs, final Preference p, final int summary, final int min, final int max, final String title, final String setting, final int def, final boolean changeTitle) {
        if (c == null) return;
        final NumberPicker np = new NumberPicker(c);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(prefs.getInt(setting, def));
        new AlertDialog.Builder(c).setTitle(title).setView(np)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        np.clearFocus();
                        prefs.edit().putInt(setting, np.getValue()).apply();
                        if (changeTitle) p.setTitle(c.getString(summary, np.getValue()));
                        else p.setSummary(c.getString(summary, np.getValue()));
                    }
                }).create().show();
    }

}
