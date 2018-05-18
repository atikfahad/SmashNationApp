package com.smashnation7.com.smashnation;


import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsFragment extends PreferenceFragmentCompat {
    SwitchPreferenceCompat switchPref;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        switchPref = (SwitchPreferenceCompat) findPreference("switch_preference_1");
        switchPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isChecked = (Boolean) newValue;
                if(isChecked){
                    FirebaseMessaging.getInstance().subscribeToTopic("url");
                    Toast.makeText(getContext(), "You are subscribed", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("url");
                    Toast.makeText(getContext(), "Your subscription is cancelled", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

}
