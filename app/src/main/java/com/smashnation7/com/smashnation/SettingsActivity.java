package com.smashnation7.com.smashnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_PREF_EXAMPLE_SWITCH = "switch_preference_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
