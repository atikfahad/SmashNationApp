package com.smashnation7.com.smashnation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity {
    Boolean switchState;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchState = getSharedPreferences("switch_state_save", MODE_PRIVATE)
                .getBoolean("switch_state", true);

        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setChecked(switchState);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    FirebaseMessaging.getInstance().subscribeToTopic("url");
                    FirebaseMessaging.getInstance().subscribeToTopic("news");
                    Toast.makeText(getApplicationContext(), "You are subscribed", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("url");
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
                    Toast.makeText(getApplicationContext(), "Your subscription is cancelled", Toast.LENGTH_SHORT).show();
                }
                switchState = b;
                SharedPreferences.Editor editor = getSharedPreferences("switch_state_save", MODE_PRIVATE).edit();
                editor.putBoolean("switch_state", switchState);
                editor.commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
