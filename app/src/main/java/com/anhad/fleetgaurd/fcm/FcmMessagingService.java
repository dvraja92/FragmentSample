package com.anhad.fleetgaurd.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anhad.fleetgaurd.R;
import com.anhad.fleetgaurd.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nikita Choudhary on 31/1/17.
 * Company Name: DecipherZoneSoftwares
 * URL: www.decipherzone.com
 */
public class FcmMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FcmMessagingService";
    private NotificationManager mNotificationManager;
    private int notificationIdOne = 111;
    private int numMessagesOne = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

     // Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
          //  Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String msg = remoteMessage.getData().toString();
            try {
                JSONObject jsonObject = new JSONObject(msg);
                JSONObject alertJsonObject = jsonObject.getJSONObject("alert");
                String message = alertJsonObject.getString("message");
                String complainNumber = alertJsonObject.getString("complain_number");

                sendNotification("Complain no. "+complainNumber,message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
          //  Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

          //  sendNotification(remoteMessage.getNotification().getBody());
        }



    }

      private void sendNotification(String messageBody, String message) {



          int requestID = (int) System.currentTimeMillis();
          Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
          NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
          mBuilder.setContentTitle("Fleet Guard");
          mBuilder.setContentText(messageBody);
          mBuilder.setSubText(message);
          mBuilder.setNumber(++numMessagesOne);

          mBuilder.setSmallIcon(R.mipmap.ic_launcher);
          mBuilder.setSound(soundUri);
          mBuilder.setColor(Color.parseColor("#E31E25"));

          mBuilder.setVibrate(new long[] { 1000, 1000});

          Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

          resultIntent.putExtra("notificationId", notificationIdOne);
          //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
          //  stackBuilder.addParentStack(MyMessages.class);
          //  stackBuilder.addNextIntent(resultIntent);
          PendingIntent resultPendingIntent = PendingIntent.getActivity(this,requestID,
                  resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
          mBuilder.setContentIntent(resultPendingIntent);
          mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          mNotificationManager.notify(notificationIdOne, mBuilder.build());



      }
}
