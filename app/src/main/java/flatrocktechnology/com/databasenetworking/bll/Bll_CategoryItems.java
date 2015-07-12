package flatrocktechnology.com.databasenetworking.bll;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import flatrocktechnology.com.databasenetworking.database.DatabaseManager;
import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;


public class Bll_CategoryItems {

    private static final String TAG = Bll_Category.class.getSimpleName();


    public static CategoryItem createRecord(CategoryItem categoryItem){

        if(categoryItem !=null) {
            try {
                DatabaseManager.getInstance().getCategoryItemsDaoInstance().create(categoryItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(TAG, "Creating null object");
        }
        return categoryItem;
    }


    public static void updateRecord (CategoryItem categoryItem){
        if(categoryItem !=null){

            try {
                DatabaseManager.getInstance().getCategoryItemsDaoInstance().update(categoryItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteRecord(CategoryItem categoryItem){
        if(categoryItem != null){
            try {
                DatabaseManager.getInstance().getCategoryItemsDaoInstance().delete(categoryItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<CategoryItem> getRecordsByCategory(Category category){
        List<CategoryItem> categoryItems = null;
        if(category!=null) {
            try {
                categoryItems =  DatabaseManager.getInstance().getCategoryItemsDaoInstance().queryForEq(CategoryItem.COL_CATEGORY_NAME, category);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoryItems;
    }

    public static List<CategoryItem> getAllRecords(){
        List<CategoryItem> categoryItems = null;

        try {
           categoryItems = DatabaseManager.getInstance().getCategoryItemsDaoInstance().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryItems;
    }

    public static void createRecords(List<CategoryItem> mEntries){
        for (int i = 0; i <mEntries.size() ; i++) {
            Bll_CategoryItems.createRecord(mEntries.get(i));
        }
    }
}
