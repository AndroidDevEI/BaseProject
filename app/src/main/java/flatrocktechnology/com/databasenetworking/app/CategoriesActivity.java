package flatrocktechnology.com.databasenetworking.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flatrocktechnology.com.databasenetworking.R;
import flatrocktechnology.com.databasenetworking.app.adapters.AdapterCategories;
import flatrocktechnology.com.databasenetworking.bll.Bll_Category;
import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;
import flatrocktechnology.com.databasenetworking.utils.Const;
import flatrocktechnology.com.databasenetworking.utils.ErrorHandler;
import flatrocktechnology.com.databasenetworking.utils.Utils;
import flatrocktechnology.com.databasenetworking.utils.VolleySingleton;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static final String TAG = CategoriesActivity.class.getSimpleName();

    private AdapterCategories mAdapter;
    private ListView mListView;
    private List<Category> mCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking);
        registerViews();
        //Request the desired data
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        requestNetworkData();

    }

    private void registerViews(){

      //  mListView = (ListView) findViewById(R.id.list_view_categories);
       // mListView.setOnItemClickListener(this);

    }

    /**
     * Sample method for network  String Request.
     *
     */
    private void requestNetworkData( ){
        RequestQueue queue = VolleySingleton.getRequestQueue();

        String url = Const.URL_API_REQUEST_CATEGORY;

        StringRequest request = new StringRequest(Request.Method.GET,url,
                responseNetworkDataSuccess(),
                responseError()){
        };
        queue.add(request);
    }



    /**
     * Sample method for network  String Request.
     * @param param - example parameter
     */
    private void requestNetworkData(final String headerParam, final String param ){
        RequestQueue queue = VolleySingleton.getRequestQueue();

        String url = Const.URL_API_REQUEST_CATEGORY;

        StringRequest request = new StringRequest(Request.Method.POST,url,
                responseNetworkDataSuccess(),
                responseError()){

            // Setup request header parameters
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String > headerParams = new HashMap<>();
                headerParams.put(Const.PARAM_DATA, headerParam);
                return headerParams;
            }
            // Setup the request parameters
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String > params = new HashMap<>();
                params.put(Const.PARAM_DATA, param);

                return params;
            }
        };
        queue.add(request);
    }


    private Response.Listener<String> responseNetworkDataSuccess() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                mCategories = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray( response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        // Get deserialize the response to the corresponding object.
                        Category  category = new Gson().fromJson( jsonArray.get(i).toString(),Category.class);
                        mCategories.add(category);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mCategories.size()>0) {

                      addRowView(mCategories);
//                    mAdapter = new AdapterCategories(NetworkingActivity.this, R.layout.row_layout_categories, mCategories);
//                    mListView.setAdapter(mAdapter);
                  //  Bll_Category.createCategories(mCategories);
                   // List<Category> mEntries = Bll_Category.getAllRecords();
                  //  mAdapter = new AdapterCategories(CategoriesActivity.this, R.layout.row_layout_categories, mEntries);
                  //  mListView.setAdapter(mAdapter);
                }
             }
        };
    }


    private void addRowView(final List<Category> mData){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int elementHeight = (height/mData.size());
        for (int i = 0; i <mData.size() ; i++) {
            Category mCategory = mData.get(i);

            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.row_layout_categories, null);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();


                        Category category = mData.get(id);

                            Bundle args = new Bundle();
                    args.putSerializable(Const.KEY_CATEGORY, category);

                    Intent intent = new Intent(MyApplication.getAppContext(), EventsActivity.class);
                    intent.putExtra(Const.KEY_CATEGORY, args);
                    startActivity(intent);
                }
            });
            TextView mCategoryName = (TextView) v.findViewById(R.id.text_view_category_title);
            TextView mCategoryDescription = (TextView) v.findViewById(R.id.text_view_category_desc);
            NetworkImageView mCategoryThumb = (NetworkImageView) v.findViewById(R.id.image_view_category);

            mCategoryName.setText(mCategory.getName());

            mCategoryDescription.setText(mCategory.getDescription());

            if(mCategory.getImage()!=null) {
                if (!mCategory.getImage().isEmpty()){
                  mCategoryThumb.setImageUrl(mCategory.getImage(), VolleySingleton.getImageLoader());
                }
            }

            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.container);
            v.setId(i);
            insertPoint.addView(v, 1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, elementHeight));

        }


// fill in any details dynamically here



// insert into main view


    }
    /**
     * Sample method for json object request.
     * @param categoryItem - object model
     * @param headerParam - if required from service header parameter
     */
    private void requestNetworkData(final String headerParam, CategoryItem categoryItem){

        RequestQueue queue = VolleySingleton.getRequestQueue();

       // String url = Const.URL_API_REQUEST;
        String url = null;

        if(categoryItem!= null){
            String json = Utils.getJsonString(categoryItem);
            Log.d(TAG , "Generated JSON " +  json);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, json);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                    responseSuccess(),
                    responseError()
                    ){
                // Setup request header parameters
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String > headerParams = new HashMap<>();
                    headerParams.put(Const.PARAM_DATA, headerParam);
                    return headerParams;
                }
            };
            queue.add(request);
        }
    }

    private Response.Listener<JSONObject> responseSuccess() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Response: " + response.toString());

                Category category = new Gson().fromJson(response.toString(),Category.class);

            }
        };
    }

    private Response.ErrorListener responseError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String  errorMessage = ErrorHandler.getMessage(error,MyApplication.getAppContext());
                Log.d(TAG, "Error response " + errorMessage);
            }
        };
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, ((Category)mListView.getItemAtPosition(position)).getName());
        Intent intent = new Intent(MyApplication.getAppContext(), EventsActivity.class);
        startActivity(intent);
    }
}
