package flatrocktechnology.com.databasenetworking.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class ErrorHandler {


    private static final String TAG= ErrorHandler.class.getSimpleName();
    private static final String ERROR_DETAIL = "detail";


    /** Method for handling network errors
     * @param error    - Volley error response
     * @param context - application context
     * @return - server error status code;
     */
    public static int networkResponseError(VolleyError error, Context context){

        String json = null;
        String jsonErrorCode = null;
        String jsonErrorDetail = null;

        String message = null;
        int responseCode = 0;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            Log.d("Error", String.valueOf(response.statusCode));
            responseCode = response.statusCode;
            switch(response.statusCode){

                case 301:
                    Log.d(TAG, String.valueOf(response.statusCode));
                   break;

                case 302:
                    Log.d(TAG , String.valueOf(response.statusCode));
                   break;

                case 400:


                    break;
                case 409:

                case 500:

                    break;

                default:

                break;
            }
        }

        return responseCode;
    }



    /**
     *  Method extracting the message from the volley status code
     * @param errorResponse - string error response
     * @param key - key for the error object
     * @return
     */
    private static String trimMessage(String errorResponse, String key){
        String trimmedString = null;

        if(errorResponse !=null ) {
            try {
                JSONObject obj = new JSONObject(errorResponse);
                trimmedString = obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return trimmedString;
    }

    /**
     *  Method returning the error message
     * @param error - volley response error
     * @param context - application context
     * @return - returned error message
     */
    public static String getMessage (VolleyError error, Context context ){
        NetworkResponse response = error.networkResponse;

        String json = null;
        String jsonErrorCode = null;
        String jsonErrorDetail = null;

        json = new String(response.data);
        jsonErrorDetail = trimMessage(json, ERROR_DETAIL);


        if(jsonErrorDetail.contains("[\"") && jsonErrorDetail.contains("\"]")){

            jsonErrorDetail = jsonErrorDetail.replace("[\"","");

            jsonErrorDetail = jsonErrorDetail.replace("\"]","");
        }

        return jsonErrorDetail;
    }
}
