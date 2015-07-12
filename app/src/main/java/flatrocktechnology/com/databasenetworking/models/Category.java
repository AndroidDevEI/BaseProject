package flatrocktechnology.com.databasenetworking.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Fuji on 14.6.2015 ?..
 */

@DatabaseTable
public class Category implements Serializable {

    @Expose(deserialize = false)
    @DatabaseField (generatedId = true)
    int id;

    @Expose
    @DatabaseField
    String name;

    @Expose
    @DatabaseField
    String description;

    @Expose
    @DatabaseField
    String image;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
