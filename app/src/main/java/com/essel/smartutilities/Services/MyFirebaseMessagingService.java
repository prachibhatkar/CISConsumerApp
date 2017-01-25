package com.essel.smartutilities.Services;

import android.util.Log;

import com.essel.smartutilities.utility.SharedPrefManager;
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
//        if (remoteMessage.getFrom().equals("/topics/" + "info"))
//            Log.i(TAG, "dataaaaaaaaaaaaaaaaaaaaaa: " + remoteMessage.getData().get("title"));
//        Log.i(TAG, "context_typeeeeeeeeeeeeeeeeeeeeeeeee: " + remoteMessage.getData().get("content_text"));
        //sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content_text"));
// TODO: Handle FCM messages here.
// If the application is in the foreground handle both data and notification messages here.
// Also if you intend on generating your own notifications as a result of a received FCM
// message, here is where that should be initiated.
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        Log.i(TAG, "onMessageReceived: "+remoteMessage.getData().get("title"));
        SharedPrefManager.saveValue(this,SharedPrefManager.FCM_PREF,remoteMessage.getData().get("title") +" "+remoteMessage.getNotification().getBody()+" "+remoteMessage.getSentTime());


        //Toast.makeText(this,remoteMessage.getNotification().getBody(),Toast.LENGTH_SHORT).show();

    }


//    private void sendNotification(String messageTitle, String messageBody) {
//        Intent intent = new Intent(this, ActivityLoginLanding.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long[] pattern = {500, 500, 500, 500, 500};
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.happyimage)
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setVibrate(pattern)
//                .setLights(Color.BLUE, 1, 1)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}