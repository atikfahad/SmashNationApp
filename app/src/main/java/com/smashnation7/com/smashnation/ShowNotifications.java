package com.smashnation7.com.smashnation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowNotifications extends AppCompatActivity {
    ArrayList<String> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifications);
        notificationList = new ArrayList<String>();
        if(getSharedPreferences("NotificationList", MODE_PRIVATE) != null) {
            SharedPreferences preferences = getSharedPreferences("NotificationList", MODE_PRIVATE);
            int totalList = preferences.getInt("latest20", 0);
            if(totalList == 0)
                notificationList.add("No Notifications yet!");
            for (int i = totalList; i > 0; i--) {
                Log.d("Ekhane Paowar Kotha", preferences.getString(Integer.toString(i) + "title", ""));
                notificationList.add(preferences.getString(Integer.toString(i) + "title", ""));
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listitem, notificationList);
        ListView listView = (ListView) findViewById(R.id.listNotifications);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(getSharedPreferences("NotificationList", MODE_PRIVATE) != null){
                    SharedPreferences preferences = getSharedPreferences("NotificationList", MODE_PRIVATE);
                    int totalList = preferences.getInt("latest20", 0);
                    if(totalList != 0){
                        int current = totalList - i;
                        String getUrl = preferences.getString(Integer.toString(current), "");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", getUrl);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

            }
        });
    }
}
