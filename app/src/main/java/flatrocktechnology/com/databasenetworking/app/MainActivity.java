package flatrocktechnology.com.databasenetworking.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import flatrocktechnology.com.databasenetworking.R;
import flatrocktechnology.com.databasenetworking.app.gcm.GCMRegisterDeviceAsyncTask;
import flatrocktechnology.com.databasenetworking.utils.ApplicationPreferences;
import flatrocktechnology.com.databasenetworking.utils.Const;
import flatrocktechnology.com.databasenetworking.utils.Utils;


public class MainActivity extends AppCompatActivity implements  GCMRegisterDeviceAsyncTask.OnGCMDeviceRegistrationListener{


    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private GoogleCloudMessaging googleCloudMessaging;
    private String deviceRegistrationId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

//        if(checkPlayServices()){
//            googleCloudMessaging = GoogleCloudMessaging.getInstance(this);
//            deviceRegistrationId = getRegistrationId(MyApplication.getAppContext());
//            if(deviceRegistrationId.isEmpty()){
//                registerInBackground();
//            }
//        }
    }

    /**
     * Initialize  the layout views
     */
    private void initializeViews() {

    }

    /**
     * Sets all required listeners for the views
     */
    private void setListeners(){

    }

    /**
     * Using headless fragment with asynchronous task for device registration process.
     */
    public void registerInBackground(){
        android.app.FragmentManager fm = getFragmentManager();
        GCMRegisterDeviceAsyncTask gcmRegisterDeviceAsyncTask = (GCMRegisterDeviceAsyncTask) fm.findFragmentByTag(Const.TASK_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (gcmRegisterDeviceAsyncTask == null) {
            gcmRegisterDeviceAsyncTask = new GCMRegisterDeviceAsyncTask();
            fm.beginTransaction().add(gcmRegisterDeviceAsyncTask, Const.TASK_FRAGMENT).commit();
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else {
                Log.i(TAG, "This device is not supported.");

            }
            return false;
        }
        return true;
    }

    /**
     *  Method for extracting the stored device registration id.
     *
     * @param context - application context
     * @return - stored registration id or empty string if not present.
     */
    private String getRegistrationId(Context context){

        ApplicationPreferences applicationPreferences = new ApplicationPreferences(context);
        String registrationId = applicationPreferences.getGCMRegisterId();
        if(registrationId!=null) {
            if (registrationId.isEmpty()){
                Log.i(TAG, "Registration not found");
                return "";
            }
            Log.d(TAG, registrationId);
        }

        // Check if the app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = applicationPreferences.getAppVersion();
        int currentVersion = Utils.getAppVersion(context);

        if(registeredVersion != currentVersion){
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Async task callback.
     */
    @Override
    public void onPreExecute() {

    }
    /**
     * Async task callback.
     */
    @Override
    public void onPostExecute(String registrationId) {
        //send the registrationId ot the backend
    }
    /**
     * Async task callback.
     */
    @Override
    public void onCancelled() {

    }



}
