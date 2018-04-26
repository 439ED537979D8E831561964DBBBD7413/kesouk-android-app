package com.tstl.kesouk.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tstl.kesouk.Activity.TabMain_Activity;
import com.tstl.kesouk.Adapter.ExpandableListAdapter1;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.Model.SectionDataModel;
import com.tstl.kesouk.Model.SingleItemModel;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;
import static com.tstl.kesouk.Fragments.Customer_Fragment.cust_search_home;

/**
 * Created by user on 19-Apr-18.
 */

public class Category_Tab_Fragment extends Fragment {
    private Typeface mDynoRegular;
    EditText mSearch;
    ExpandableListView expandableList;
    ExpandableListAdapter1 mMenuAdapter;
    View view;
    RecyclerView recyclerView;
    ArrayList<SingleItemModel> singleItem;
    String name_category, image_url_category;
    int id;
    TextView shop;
    public static int searchCategory=0;

    public static Category_Tab_Fragment newInstance() {
        Category_Tab_Fragment fragment = new Category_Tab_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_tab_main, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mSearch = (EditText) view.findViewById(R.id.search_new);
        shop = (TextView) view.findViewById(R.id.shop);
        search.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("CATEGORIES");
        getSections();
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager =
                            (InputMethodManager) getActivity().
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    searchCategory = 1;

                    Category_Fragment newFragment = new Category_Fragment();

                    Bundle args = new Bundle();
                    args.putString("search", search.getText().toString());
                    newFragment.setArguments(args);


                    FragmentTransaction transaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.rldContainer, newFragment);
                    transaction.addToBackStack("Some String");
                    transaction.commit();
                    search.setText("");

                    handled = true;
                }
                return handled;
            }
        });

        //enableExpandableList();
        setFont();
       /* DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expandableList.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }*/
        return view;
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto_Regular.ttf");
        mSearch.setTypeface(mDynoRegular);
    }


    private void getSections() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_SECTIONS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("get_sections", String.valueOf(response));

                        try {

                            JSONObject JObject = new JSONObject(String.valueOf(response));

                            JSONArray DataArray = JObject.getJSONArray("data");
                            ArrayList<Browse_Category> browse_categoryArrayList = null;
                            Browse_Category browse_category = null;

                            if (DataArray.length() != 0) {

                                for (int i = 0; i < DataArray.length(); i++) {

                                    JSONObject jsonObject = DataArray.getJSONObject(i);


                                    if (jsonObject.has("categorys")) {
                                        JSONArray category_subcategory = jsonObject.getJSONArray("categorys");

                                        if (category_subcategory.length() != 0) {
                                            browse_categoryArrayList = new ArrayList<Browse_Category>();
                                            Log.e("category_subcategory", String.valueOf(category_subcategory.length()));
                                            for (int j = 0; j < category_subcategory.length(); j++) {
                                                browse_category = new Browse_Category();
                                                JSONObject jsonObject1 = category_subcategory.getJSONObject(j);
                                                name_category = jsonObject1.getString("name");
                                                image_url_category = jsonObject1.getString("icon_url");
                                                id = jsonObject1.getInt("id");
                                                browse_category.setProduct_id(id);
                                                browse_category.setProd_name(name_category);
                                                browse_category.setProduct_image(image_url_category);

                                                browse_categoryArrayList.add(browse_category);


                                            }

                                        }

                                        Log.e("browse_categoryArrayList", browse_categoryArrayList.toString());

                                        CategoryTab_Adapter adapter = new CategoryTab_Adapter(getActivity(), browse_categoryArrayList);

                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                        recyclerView.setAdapter(adapter);

                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(getActivity(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(
                    NetworkResponse networkResponse) {
                // TODO Auto-generated method stub
                Map<String, String> responseHeaders = networkResponse.headers;
                return super.parseNetworkResponse(networkResponse);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);

    }


    public class CategoryTab_Adapter extends RecyclerView.Adapter<CategoryTab_Adapter.MyViewHolder> {

        private Context mContext;
        public ArrayList<Browse_Category> browse_categoryArrayList;
        private Typeface mDynoRegular;
        DB db;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            RelativeLayout itemLayout;
            ImageView iconImage;

            public MyViewHolder(View view) {
                super(view);

                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(),
                        "font/Roboto_Regular.ttf");

                title = (TextView) view.findViewById(R.id.submenu1);
                itemLayout = (RelativeLayout) view.findViewById(R.id.linear);
                iconImage = (ImageView) view.findViewById(R.id.prod_image);

                db = new DB(mContext);
                title.setTypeface(mDynoRegular);

            }
        }

        public CategoryTab_Adapter(Context mContext, ArrayList<Browse_Category> browse_categoryArrayList) {
            this.mContext = mContext;
            this.browse_categoryArrayList = browse_categoryArrayList;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_tab_listitem, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Browse_Category browse_category = browse_categoryArrayList.get(position);
            holder.title.setText(browse_category.getProd_name());
            if (browse_category.getProduct_image().equals("null"))

            {

            } else {
                Glide.with(mContext)
                        .load(Constants.CATEGORY_IMAGES + browse_category.getProduct_image())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.logo)
                        .into(holder.iconImage);
            }
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Customer_Fragment.cust_home_frag = 2;
                    searchCategory = 1;
                    Customer_Fragment.cust_category_selected_name = browse_categoryArrayList.get(position).getProd_name();
                    String name = browse_categoryArrayList.get(position).getProd_name();
                    Category_Fragment newFragment = new Category_Fragment();
                    Bundle args = new Bundle();
                   // args.putInt("position", position);
                    args.putString("search", name);
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.rldContainer, newFragment);
                    transaction.addToBackStack("Some String");
                    transaction.commit();
                }
            });

        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return browse_categoryArrayList.size();
        }

    }

}
