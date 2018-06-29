package com.example.matah.sligro_app;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by matah on 17/12/15.
 */

public class Notifications extends AppCompatActivity {
    private NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private static final String client_id = "12314";
    private Context c;
    private Intent notif;
    PendingIntent pendingIntent;

    public void MakeNotif(Context context, Activity activity, String map, String name, String body, String ticket)
    {
        c = context;
        notification = new NotificationCompat.Builder(context, client_id);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.ic_hacker_news);
        notification.setTicker(ticket);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(name);
        notification.setContentText(body);
        notif = new Intent(activity,DetailsActivity.class);
        notif.putExtra("url", map);
        pendingIntent = PendingIntent.getActivity(context,0,notif,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(uniqueID, notification.build());
        notification.setContentIntent(pendingIntent);
        mNotifyMgr.notify(uniqueID, notification.build());


    }
}
