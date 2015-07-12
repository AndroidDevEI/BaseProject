package flatrocktechnology.com.databasenetworking.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "UserPref";

    // Key for the received Id from GCM registration
    public static final String KEY_REG_ID= "registration_id";

    // Current app version
    public static final String KEY_APP_VERSION = "app_version";


    public ApplicationPreferences(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setGCMRegisterInfo(String gcmRegId, int appVersion){

        editor.putString(KEY_REG_ID, gcmRegId);
        editor.putInt(KEY_APP_VERSION, appVersion);
        editor.commit();
    }

    public String getGCMRegisterId(){
        String registerId = pref.getString(KEY_REG_ID,null);
        return registerId;
    }

    public int getAppVersion(){
        int appVersion= pref.getInt(KEY_APP_VERSION,0);
        return appVersion;
    }

     /**
     * Clear SharedPreferences
     */
    public void clear(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }
}

