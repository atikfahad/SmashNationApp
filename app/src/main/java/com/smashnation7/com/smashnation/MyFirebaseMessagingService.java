package com.smashnation7.com.smashnation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("NotificationList", Context.MODE_PRIVATE);
        int latest20 = preferences.getInt("latest20", 0);
        SharedPreferences.Editor editor = preferences.edit();
        Intent intent = new Intent(this, MainActivity.class);
        if(remoteMessage.getData().size() > 0){
            String message = remoteMessage.getData().get("url");
            String messageHead = "";
            if(remoteMessage.getData().get("title") != null)
                messageHead = remoteMessage.getData().get("title");
            Bundle bundle = new Bundle();
            bundle.putString("url", message);
            bundle.putString("title", messageHead);
            intent.putExtras(bundle);
            editor.putString(Integer.toString(++latest20), message);
            editor.putString(Integer.toString(latest20) + "title", messageHead);
            editor.putInt("latest20", latest20);
            editor.commit();
        }


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default");
        notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }
}
