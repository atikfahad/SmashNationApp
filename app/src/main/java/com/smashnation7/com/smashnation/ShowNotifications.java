package com.smashnation7.com.smashnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowNotifications extends AppCompatActivity {
    String[] mobileArray = {"Android 7", "Blackberry", "iPhone 7", "Sadeeb"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifications);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem,mobileArray);
        ListView listView = (ListView) findViewById(R.id.listNotifications);
        listView.setAdapter(adapter);
    }
}
