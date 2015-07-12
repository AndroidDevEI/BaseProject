package flatrocktechnology.com.databasenetworking.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;


/** DatabaseHelper class used to create, upgrade project's database.
 *
 *
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();


    public DatabaseHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    /**
     * Database access object used to interact with the database.
     */
    private Dao<Category, Integer> categoryDao = null;
    private Dao<CategoryItem,Integer> categoryItemsDao = null;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, CategoryItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, CategoryItem.class, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //Use if table update is needed
         /* List<String> allSql = new ArrayList<String>();
        switch(oldVersion)
        {
            case 1:
                //allSql.add("alter table AdData add column `new_col` VARCHAR");
                //allSql.add("alter table AdData add column `new_col2` VARCHAR");
        }
        for (String sql : allSql) {
            database.execSQL(sql);
        }*/
    }


    /**
     * Returns an instance of the data access object
     * corresponding to the passed model
     * @return
     * @throws SQLException
     */
    public Dao<Category, Integer> getCategoryDao(){
        if(categoryDao==null){

            try {
                categoryDao = getDao(Category.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoryDao;
    }
    public Dao<CategoryItem, Integer> getCategoryItemsDao(){
        if(categoryItemsDao == null){

            try {
                categoryItemsDao = getDao(CategoryItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoryItemsDao;
    }

    @Override
    public void close() {
       super.close();
       categoryDao = null;
    }
}
