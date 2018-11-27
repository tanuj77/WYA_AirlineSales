package com.ccs.bankebihari.wycairlinesales;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by CCS79 on 25-05-2018.
 */

public class FcmMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(this);
        notificationCompatBuilder.setContentTitle(title);
        notificationCompatBuilder.setContentText(text);
        notificationCompatBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationCompatBuilder.setAutoCancel(true);
        notificationCompatBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationCompatBuilder.build());
    }
}
