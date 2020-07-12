package com.example.bloodbank.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.bloodbank.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";
    String ADMIN_CHANEL_ID = "admin_chanel_id";
    NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        int notificationId = new Random ().nextInt ( 60000 );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri ( RingtoneManager.TYPE_NOTIFICATION );
        notificationManager = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder ( this, ADMIN_CHANEL_ID )
                        .setSmallIcon ( R.mipmap.ic_launcher )
                        .setContentTitle ( remoteMessage.getData ().get ( "title" ) )
                        .setStyle ( new NotificationCompat.BigPictureStyle ()
                                .setSummaryText ( remoteMessage.getData ().get ( "message" ) ) )
                        .setContentText ( remoteMessage.getData ().get ( "message" ) )
                        .setAutoCancel ( true )
                        .setSound ( defaultSoundUri );

        notificationManager.notify ( notificationId, notificationBuilder.build () );
    }

    @Override
    public void onNewToken(String token) {
        Log.d ( TAG, "Refreshed token: " + token );

//        SaveData((Activity) getApplicationContext(), FIREBASE_TOKEN, token);
    }
}
