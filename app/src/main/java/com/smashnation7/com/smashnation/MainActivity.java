package com.smashnation7.com.smashnation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (isFirstRun) {
            FirebaseMessaging.getInstance().subscribeToTopic("url");
            SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        webView = (WebView) findViewById(R.id.smashWeb);
        isConnected();
        final Button reLoad = (Button) findViewById(R.id.bReload);
        reLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.clearCache(true);
                isConnected();

            }
        });
        Button share = (Button) findViewById(R.id.bShare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String appPackageName = getApplicationContext().getPackageName();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Smash Nation");
                    String sAux = "\nLet Me Recommend You This Application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id="+ appPackageName +" \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose One"));
                } catch(Exception e) {
                }
            }
        });
        Button notificationList = (Button) findViewById(R.id.bNotificationList);
        notificationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowNotifications.class);
                startActivity(intent);
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        MobileAds.initialize(this, "ca-app-pub-8353350959820749~2436165388");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if(getIntent().getExtras() != null){
            String url = getIntent().getExtras().getString("url");
            if(url != null){
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                try {
                    getApplicationContext().startActivity(webIntent);
                }
                catch (Exception e){}
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnected();
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public void isConnected(){
        if(isOnline()){
            webView.loadUrl("https://smashnation7.com");
        }
        else
            Toast.makeText(getApplicationContext(), "You are not connected with network!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
