package flatrocktechnology.com.databasenetworking.models;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class CategoryItem implements Serializable {

    public static final String COL_CATEGORY_NAME  = "category_name";

    @Expose
    @DatabaseField (generatedId = true)
    int id;
    @Expose
    @DatabaseField
    String name;

    @Expose
    @DatabaseField
    String event_title;

    @Expose
    @DatabaseField
    String event_time;

    @Expose
    @DatabaseField
    String event_description;
    @Expose
    @DatabaseField
    String event_desc_place;

    @Expose
    @DatabaseField
    String event_desc_time;

    @Expose
    @DatabaseField
    String event_image_url;


    @DatabaseField (foreignAutoRefresh = true, foreign = true, foreignAutoCreate = true, columnName = COL_CATEGORY_NAME)
    Category categoryObj;


    @Expose
    int category;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_desc_place() {
        return event_desc_place;
    }

    public void setEvent_desc_place(String event_desc_place) {
        this.event_desc_place = event_desc_place;
    }

    public String getEvent_desc_time() {
        return event_desc_time;
    }

    public void setEvent_desc_time(String event_desc_time) {
        this.event_desc_time = event_desc_time;
    }

    public String getEvent_image_url() {
        return event_image_url;
    }

    public void setEvent_image_url(String event_image_url) {
        this.event_image_url = event_image_url;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }


   // @DatabaseField (foreign = true ,columnName = COL_CATEGORY_NAME)
  //  Category category;
   public Category getCategoryObj() {
       return categoryObj;
   }

    public void setCategoryObj(Category categoryObj) {
        this.categoryObj = categoryObj;
    }




}
