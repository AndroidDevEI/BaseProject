package flatrocktechnology.com.databasenetworking.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;


public class DatabaseManager {

    static private DatabaseManager instance;

    static public void init(Context context){

        if(null == instance){

            instance = new DatabaseManager(context);
        }
    }

    static public DatabaseManager getInstance(){
        return instance;
    }

    private DatabaseHelper helper;

    private DatabaseManager(Context context){
        helper = new DatabaseHelper(context);
    }

    private DatabaseHelper getHelper(){
        return helper;
    }


    public Dao<Category, Integer> getCategoryDaoInstance(){return getHelper().getCategoryDao();}
    public Dao<CategoryItem,Integer> getCategoryItemsDaoInstance(){return  getHelper().getCategoryItemsDao();}
}
