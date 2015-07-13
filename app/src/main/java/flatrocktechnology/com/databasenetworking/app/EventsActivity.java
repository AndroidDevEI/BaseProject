package flatrocktechnology.com.databasenetworking.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.NavUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import flatrocktechnology.com.databasenetworking.R;
import flatrocktechnology.com.databasenetworking.bll.Bll_CategoryItems;
import flatrocktechnology.com.databasenetworking.models.Category;
import flatrocktechnology.com.databasenetworking.models.CategoryItem;
import flatrocktechnology.com.databasenetworking.ui.SlidingTabLayout;
import flatrocktechnology.com.databasenetworking.utils.Const;
import flatrocktechnology.com.databasenetworking.utils.ErrorHandler;
import flatrocktechnology.com.databasenetworking.utils.VolleySingleton;

public class EventsActivity extends AppCompatActivity{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    List<CategoryItem> mCategoryItems;

    private Category mCategory;
    private static final String TAG = EventsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mCategory = (Category) getIntent().getBundleExtra(Const.KEY_CATEGORY).getSerializable(Const.KEY_CATEGORY);
        Log.d(TAG, mCategory.getName());

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestNetworkData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<CategoryItem> mCategoryItems;
        public SectionsPagerAdapter(FragmentManager fm, List<CategoryItem> events) {
            super(fm);

            mCategoryItems = events;
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,  mCategoryItems.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mCategoryItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            return mCategoryItems.get(position).getName().toUpperCase(l);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private CategoryItem mCategoryItem;
        private ScrollView mScrollView;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, CategoryItem categoryItem) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(Const.KEY_CATEGORY_ITEM,categoryItem);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mCategoryItem =(CategoryItem) getArguments().getSerializable(Const.KEY_CATEGORY_ITEM);
        }

        @Override
        public void onResume() {
            super.onResume();
               mScrollView.fullScroll(View.FOCUS_UP);

        }

        @Override
        public void onPause() {
            super.onPause();
            mScrollView.fullScroll(View.FOCUS_UP);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
            NetworkImageView imageView = (NetworkImageView) rootView.findViewById(R.id.image_view_event);

            TextView mEventName = (TextView) rootView.findViewById(R.id.tv_event_name);
            TextView mEventTitle = (TextView) rootView.findViewById(R.id.tv_evet_title);
            TextView mEventDesc = (TextView) rootView.findViewById(R.id.tv_event_desc);
            TextView mEventPlaceDesc = (TextView) rootView.findViewById(R.id.tv_event_desc_place);
            TextView mEventTime =(TextView) rootView.findViewById(R.id.tv_event_time);


            mScrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

            if(mCategoryItem !=null) {
                imageView.setImageUrl(mCategoryItem.getEvent_image_url(), VolleySingleton.getImageLoader());
                mEventName.setText(mCategoryItem.getName());
                mEventTitle.setText(mCategoryItem.getEvent_title());
                mEventTime.setText(mCategoryItem.getEvent_time());
                mEventDesc.setText(mCategoryItem.getEvent_description());
                mEventPlaceDesc.setText(mCategoryItem.getEvent_desc_place());
            }
            return rootView;
        }
    }


    /**
     * Sample method for network  String Request.
     *
     */
    private void requestNetworkData( ){
        RequestQueue queue = VolleySingleton.getRequestQueue();

        String url = Const.URL_API_REQUEST_CATEGORY_ITEM;

        StringRequest request = new StringRequest(Request.Method.GET,url,
                responseNetworkDataSuccess(),
                responseError()){
        };
        queue.add(request);
    }

    private Response.Listener<String> responseNetworkDataSuccess() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                mCategoryItems = new ArrayList<>();
                String[] tabs = {null,null,null};
                try {
                    JSONArray jsonArray = new JSONArray( response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        // Get deserialize the response to the corresponding object.
                        CategoryItem categoryItem = new Gson().fromJson( jsonArray.get(i).toString(),CategoryItem.class);
                        categoryItem.setCategoryObj(mCategory);
                        mCategoryItems.add(categoryItem);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mCategoryItems.size()>0) {
                    // Create the adapter that will return a fragment for each of the three
                    // primary sections of the activity.

                    Bll_CategoryItems.createRecords(mCategoryItems);
                    List<CategoryItem> items = Bll_CategoryItems.getRecordsByCategory(mCategory);

                    if(items.size()>0) {

                        // Set section page adapter
                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), items);
                        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tabs);

                        // Set up the ViewPager with the sections adapter.
                        mViewPager = (ViewPager) findViewById(R.id.pager);
                        mViewPager.setAdapter(mSectionsPagerAdapter);
                        mTabs.setViewPager(mViewPager);
                        mTabs.setDistributeEvenly(true);
                    }else{
                        Toast.makeText(MyApplication.getAppContext(),"There are no events for this category", Toast.LENGTH_SHORT).show();
                    }
                   // Bll_CategoryItems.createRecords(mCategoryItems);
                }
            }
        };
    }

    private Response.ErrorListener responseError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String  errorMessage = ErrorHandler.getMessage(error, MyApplication.getAppContext());
                Log.d(TAG, "Error response " + errorMessage);
            }
        };
    }
}
