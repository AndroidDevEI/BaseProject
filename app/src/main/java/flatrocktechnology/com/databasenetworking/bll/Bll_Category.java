package flatrocktechnology.com.databasenetworking.bll;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import flatrocktechnology.com.databasenetworking.database.DatabaseManager;
import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;


public class Bll_Category {

    private static final String TAG = Bll_Category.class.getSimpleName();

    public static Category createRecord(Category category){

        try {
            category.setId(0);
            int row = DatabaseManager.getInstance().getCategoryDaoInstance().create(category);

            Log.d(TAG, "Created row :" + row);

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return  category;
    }

    public static void updateRecord(Category category){
        try {
            DatabaseManager.getInstance().getCategoryDaoInstance().update(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteRecord(Category category){

        try {
            DatabaseManager.getInstance().getCategoryDaoInstance().delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Category getRecordById (int recordId){
        Category category = null;

        try {
             category = DatabaseManager.getInstance().getCategoryDaoInstance().queryForId(recordId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


    public static List<Category> getAllRecords(){

        List<Category> categories = null;

        try {
            categories = DatabaseManager.getInstance().getCategoryDaoInstance().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static List<Category> createCategories(List<Category> mEntries ){

        for (int i = 0; i <mEntries.size() ; i++) {
            createRecord(mEntries.get(i));
        }
        return mEntries;
    }


}
