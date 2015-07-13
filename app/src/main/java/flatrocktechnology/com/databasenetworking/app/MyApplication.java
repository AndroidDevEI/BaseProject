package flatrocktechnology.com.databasenetworking.app;

import android.app.Application;
import android.content.Context;

import flatrocktechnology.com.databasenetworking.database.DatabaseManager;
import flatrocktechnology.com.databasenetworking.database.HelperFactory;
import flatrocktechnology.com.databasenetworking.utils.VolleySingleton;


public class MyApplication extends Application {

    private static MyApplication mInstance;

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        try {
            // Initialize singletons
            VolleySingleton.init(this);
            DatabaseManager.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setAppContext(getApplicationContext());
    }

    public static MyApplication getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
