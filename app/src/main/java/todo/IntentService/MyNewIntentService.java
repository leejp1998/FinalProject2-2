package todo.IntentService;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.xinliao.finalproject.MainActivity;
import com.example.xinliao.finalproject.R;

import todo.activity.SeeAllScheduleActivity;

/**
 * Created by User on 2018-05-05.
 */

public class MyNewIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;
    public MyNewIntentService(){
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        // show notification
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Don't Miss Due");
        builder.setContentText("Your to-do list today is here");
        builder.setSmallIcon(R.drawable.prompt_shape);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
