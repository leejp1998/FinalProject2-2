package todo.Receiver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import todo.IntentService.MyNewIntentService;

/**
 * Created by User on 2018-05-05.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);

        // show toast
        //Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
    }
}
