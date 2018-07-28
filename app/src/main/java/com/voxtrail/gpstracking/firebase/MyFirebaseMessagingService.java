package com.voxtrail.gpstracking.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.SplashActivtiy;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;

import org.json.JSONObject;

/**
 * Created by sunil on 18-08-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            String notification = remoteMessage.getData().toString();
            String success = remoteMessage.getData().get("success");
            String result = remoteMessage.getData().get("result");
            String title = remoteMessage.getData().get("title");
            String description = remoteMessage.getData().get("description");
            String type = remoteMessage.getData().get("type");

            Log.d(TagUtils.getTag(), "notification:-" + notification);
            Log.d(TagUtils.getTag(), "success:-" + success);
            Log.d(TagUtils.getTag(), "result:-" + result);
            Log.d(TagUtils.getTag(), "type:-" + type);
            if(Pref.GetBooleanPref(getApplicationContext(),StringUtils.IS_LOGIN,false)){
                checkType(type, result);
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        }
//        }
    }


    public void checkType(String type, String result) {
        try {
            Log.d(TagUtils.getTag(), "type:-" + type);
            if(Pref.GetBooleanPref(getApplicationContext(),StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION,false)) {
                sendPostNotification(type, result);
            }
            updateChatActivity(getApplicationContext(),type,result);
//            switch (type) {
//                case "post-generated":
//                    sendPostNotification("Kaajneeti",type, result);
//                    break;
//                case "complaint-tagged":
//                    sendPostNotification("Kaajneeti",type, result);
//                    break;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendPostNotification(String type, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);

            Log.d(TagUtils.getTag(),"notification message:-"+jsonObject.optString("msg"));
            Log.d(TagUtils.getTag(),"notification type:-"+type);

            Intent intent = new Intent(this, SplashActivtiy.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", type);
            intent.putExtra("data", data);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(type)
                    .setContentText(jsonObject.optString("msg"));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateChatActivity(Context context, String type, String message) {
//        Intent intent = new Intent(StringUtils.UPDATE_NOTIFICATION);
//
//        //put whatever data you want to send, if any
//        intent.putExtra("type", type);
//        intent.putExtra("data", message);
//
//        //send broadcast
//        context.sendBroadcast(intent);
    }


}