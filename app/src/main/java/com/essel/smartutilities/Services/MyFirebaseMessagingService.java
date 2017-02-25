package com.essel.smartutilities.Services;

import android.util.Log;

import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.NotificationCard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by hp on 10/21/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.i(TAG, "onMessageReceived: "+remoteMessage.getData().get("title"));
        NotificationCard notificationCard =new NotificationCard();
        notificationCard.message=remoteMessage.getNotification().getBody();
        notificationCard.title=remoteMessage.getNotification().getTitle();
//        notificationCard.date= "20-08-1994";
        notificationCard.is_readed="false";
        DatabaseManager.saveNotification(this,notificationCard);

    }

}