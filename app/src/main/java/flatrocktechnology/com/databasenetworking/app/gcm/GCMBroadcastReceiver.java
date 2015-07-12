package flatrocktechnology.com.databasenetworking.app.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by emilivanov on 3/5/15.
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
   private int mNotificationCounter;
    private static final String TAG = "GCMBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {


        // Explicitly specify that GCMMessageHandler will handle the intent
        ComponentName componentName = new ComponentName(context.getPackageName(), GCMMessageHandler.class.getName());

        Log.d(TAG, "Entered!");
        // Start the service, keeping the device awake while it is launching

        startWakefulService(context, (intent.setComponent(componentName)));
        setResultCode(Activity.RESULT_OK);
    }
}
