package flatrocktechnology.com.databasenetworking.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Utils {

    /**
     *  Method for the current application version
     * @param context - application contex
     * @return - returns the app  version
     */
    public static int getAppVersion(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionCode;
    }

    /**
     *  Method for generating json string from object model
     * @param object - the serializable object
     * @return - json string
     */
    public static String getJsonString(Object object) {
        // Before converting to GSON check value of id
        Gson gson = null;

        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        return gson.toJson(object);
    }

}
