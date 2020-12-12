package com.example.ourapplication_kohl_roux_m.ui.Settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.ourapplication_kohl_roux_m.R;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
