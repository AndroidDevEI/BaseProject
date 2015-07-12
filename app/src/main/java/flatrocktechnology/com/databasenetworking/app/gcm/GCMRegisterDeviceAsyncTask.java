package flatrocktechnology.com.databasenetworking.app.gcm;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import flatrocktechnology.com.databasenetworking.utils.ApplicationPreferences;
import flatrocktechnology.com.databasenetworking.utils.Const;
import flatrocktechnology.com.databasenetworking.utils.Utils;


public class GCMRegisterDeviceAsyncTask extends Fragment {


    private static final String TAG = "GCM Register Device";

    private GoogleCloudMessaging mGoogleCloudMessaging;
    private String regId;
    private String mMessage;
    private Activity mActivity;
    private static OnGCMDeviceRegistrationListener mListener;
    private GCMRegisterDevice registerDeviceTask ;
    private Context mContext;

    private ApplicationPreferences applicationPreferences;

    public GCMRegisterDeviceAsyncTask() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        registerDeviceTask = new GCMRegisterDevice();
        registerDeviceTask.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = getActivity();
            mListener = (OnGCMDeviceRegistrationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mContext = getActivity().getApplicationContext();
    }

    private  class GCMRegisterDevice extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute () {
        super.onPreExecute();
            if(mListener!=null){
                mListener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

               String msg = "";
                try {
                    if (mGoogleCloudMessaging == null) {
                        mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(mActivity);
                    }

                    regId = mGoogleCloudMessaging.register(Const.PROJECT_NUMBER);
                    if(regId!=null){


                        mMessage = "Device registered, registration ID=" + regId;
                        Log.i("GCM", msg);

                        // You should send the registration ID to your server over HTTP,
                        // so it can use GCM/HTTP or CCS to send messages to your app.
                        // The request to your server should be authenticated if your app
                        // is using accounts.
                        sendRegistrationIdToBackend();


                        // Store the device registration ID in application preferences.
                        // There is no need to register again. Unless the app version is changed
                        // or the registration Id is lost.
                        storeRegistrationId(mContext, regId);
                    }

                } catch (IOException ex) {
                    mMessage  = "Error GCM exception :" + ex.getMessage();
                }
                return mMessage;
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            if(mListener!=null){

                Log.d(TAG, message);
                mListener.onPostExecute(message);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(mListener!=null){
                mListener.onCancelled();
            }
        }
    }

    /**
     *  Method for storing the registration id locally
     *   after the registration process is completed.
     * @param context - application context
     * @param regId - device registration id
     */
    private void storeRegistrationId(Context context, String regId){

       ApplicationPreferences  applicationPreferences = new ApplicationPreferences(context);
       int appVersion = Utils.getAppVersion(context);
       applicationPreferences.setGCMRegisterInfo(regId, appVersion);
       Log.i(TAG, "Saving regId on app version " + appVersion);
    }


    /**
     * Can be implemented here or from the activity
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app.
     * Depending on the app Networking services implementation we can send the registration id
     * from another class Activity.
     */
    private void sendRegistrationIdToBackend() {
       // Your implementation here.
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        registerDeviceTask.cancel(true);
    }


    /**
     *  Sends callback to the activity for the stages
     *  of the Asynchronous task in progress.
     */
     public interface OnGCMDeviceRegistrationListener {

         void onPreExecute();
         void onPostExecute(String registrationId);
         void onCancelled();
    }
}
