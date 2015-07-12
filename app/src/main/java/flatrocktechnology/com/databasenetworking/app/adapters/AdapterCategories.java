package flatrocktechnology.com.databasenetworking.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import flatrocktechnology.com.databasenetworking.R;
import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.utils.VolleySingleton;

/**
 * Created by Fuji on 11.7.2015 г..
 */
public class AdapterCategories extends ArrayAdapter<Category> {

   List<Category> mData= null;
   Context  mContext = null;
   LayoutInflater mInflater;


    public AdapterCategories(Context context, int resource,List<Category> entries) {
        super(context, resource);

        mContext = context;
        mData = entries;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Category getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getPosition(Category item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Category mCategory = getItem(position);

        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_layout_categories,parent,false);

            holder.mCategoryName = (TextView) convertView.findViewById(R.id.text_view_category_title);
            holder.mCategoryDescription = (TextView) convertView.findViewById(R.id.text_view_category_desc);
            holder.mCategoryThumb = (NetworkImageView) convertView.findViewById(R.id.image_view_category);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if(mCategory!=null) {

            holder.mCategoryName.setText(mCategory.getName());

            holder.mCategoryDescription.setText(mCategory.getDescription());

            if(mCategory.getImage()!=null) {
                if (!mCategory.getImage().isEmpty()){
                    holder.mCategoryThumb.setImageUrl(mCategory.getImage(), VolleySingleton.getImageLoader());
                }
            }
        }
        return convertView;
    }

    public static class ViewHolder{

        TextView mCategoryName;
        TextView mCategoryDescription;
        NetworkImageView mCategoryThumb;
    }

}
