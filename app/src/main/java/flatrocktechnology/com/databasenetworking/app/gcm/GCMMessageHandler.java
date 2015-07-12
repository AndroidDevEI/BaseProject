package flatrocktechnology.com.databasenetworking.app.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import flatrocktechnology.com.databasenetworking.R;
import flatrocktechnology.com.databasenetworking.app.MainActivity;


public class GCMMessageHandler extends IntentService {

    private static final String SERVICE_NAME = "GCMMessageHandler";
    private static final String TAG = "GCMMessageHandler";


    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    private int mNotificationCounter;

    public GCMMessageHandler() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

       GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
       // The getMessageType() intent parameter must be the intent you received
       // in the Broadcast Receiver
       String messageType = gcm.getMessageType(intent);

        if(!extras.isEmpty()){
        /*
         * Filter messages based on message type. Since it is likely that GCM
         * will be extended in the future with new message types, just ignore
         * any message types you're not interested in, or that you don't
         * recognize.
         */

            switch(messageType){

                case GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR:
                    sendNotification("Send error: " +
                            extras.toString());
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_DELETED:
                    sendNotification("Deleted messages on server: " +
                            extras.toString());
                    break;
                case GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE:

                    Log.i(TAG, "Received: " + extras.toString());
                    sendNotification(extras.toString());
                    break;
            }
            // Release the wake lock provided by the WakefulBroadcastReceiver.
            GCMBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent =new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_light)
                        .setContentTitle(getApplicationContext().getString(R.string.text_notification_title))
                       // .setStyle(new NotificationCompat.BigTextStyle()
                         //       .bigText(msg))
                        .setContentText(getApplicationContext().getString(R.string.text_notification_body));

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
