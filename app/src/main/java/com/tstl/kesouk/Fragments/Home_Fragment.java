package com.tstl.kesouk.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by user on 18-Jan-18.
 */


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tstl.kesouk.Activity.Login_Activity;
import com.tstl.kesouk.Activity.MyAddress_Activity;
import com.tstl.kesouk.Activity.Profile_Activity;
import com.tstl.kesouk.Adapter.ExpandableListAdapter1;
import com.tstl.kesouk.Adapter.Recipes_Adapter;
import com.tstl.kesouk.Adapter.SectionListDataAdapter;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Home_Products;
import com.tstl.kesouk.Model.IOnBackPressed;
import com.tstl.kesouk.Model.Products;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.Model.RecyclerItemListener;
import com.tstl.kesouk.Model.SectionDataModel;
import com.tstl.kesouk.Model.SingleItemModel;
import com.tstl.kesouk.Activity.SplashActivity;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;

public class Home_Fragment extends Fragment implements IOnBackPressed, SearchView.OnQueryTextListener,  BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    private Typeface mDynoRegular;
   // private Toolbar mToolbar;
    private TextView menu1, menu2;
    // SearchView searchView;
    public static NavigationView navigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private RelativeLayout fragmentLayout;
    //EditText search;
    public static int listview_onclick, home_home_frag = 0;
    public String caption_letter;
    ExpandableListAdapter1 mMenuAdapter;
    ExpandableListView expandableList;
    List<String> listDataHeader;
    DrawerLayout drawer;
    ArrayList<Products> listHeader = new ArrayList<>();
    ArrayList<SectionDataModel> section_name_list = new ArrayList<>();
    Dialog dialog;
    TextView mShare, mCancel, mFacebook, mTwitter, mInstagram, mSnapchat, mFoodrecipes_sections;
    public static int home_onclick=0;

    public static ArrayList<Products> listChild;
    LinkedHashMap<String, List<Products>> listDataChild = new LinkedHashMap<String, List<Products>>();
    Products products = Products.getInstance();
    int sub_category_id, status, product_quantity_type;
    String name, image_url, icon_url, product_name, product_image, product_discount,product_random_id,product_price_kesoukPrice;    private SliderLayout imageSlider;
    View.OnClickListener myOnClickListener;
    private List<Products> horizontal_category_list;
    String product_qty,product_qty_name,productRandomId="";
    //Category_Adapter horizontal_category_adapter;
    public static List<String> home_categories_list = new ArrayList<String>();
    public static List<String> home_categories_image_list = new ArrayList<String>();
    public static List<SingleItemModel> deals_list = new ArrayList<SingleItemModel>();
    public ArrayList<Recipe> recipeArrayList;
    public static String home_category_selected_name;
    public static int home_category_flag = 0;
    int banner_count;
    public static int categoryOrproduct = 0;
    public static List<String> banner_image_list = new ArrayList<String>();
    View view;
    ArrayList<SingleItemModel> singleItem = null;
    boolean doubleBackToExitPressedOnce = false;
    int ad_status = 0, id;
    public static int home_signin_backpress = 0;
  String name_category, image_url_category, transaction_with, section_name, product_price_id,category,sub_category,product_price_MarketPrice;


   // private TextView mToolbarTitle;
    RecyclerView my_recycler_view;
    ArrayList<String> imageList = new ArrayList<>();
    RecyclerView foodrecyclerView;
    public static int search_home=0;

    Integer[] Bcatid = {
            R.drawable.prod4,
            R.drawable.prod5,
            R.drawable.prod6


    };
    RelativeLayout relativeLayout;
    String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png"
    };

    ArrayList<SectionDataModel> allSampleData;

    public static Home_Fragment newInstance() {
        Home_Fragment fragment = new Home_Fragment();
        return fragment;
    }

    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.nav_drawer_home_activity, container, false);
        imageSlider = (SliderLayout) view.findViewById(R.id.slider);
        foodrecyclerView = (RecyclerView) view.findViewById(R.id.receipe_list);
        mFoodrecipes_sections = (TextView) view.findViewById(R.id.food_recipes);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.Deals_layout);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        foodrecyclerView.setHasFixedSize(true);
        relativeLayout.setVisibility(View.GONE);
        search_home=0;

        getBannerImage();

        getSections();


        //  enableExpandableList();
        //search = (EditText) view.findViewById(R.id.search_new);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.deals_list);

      //  mToolbar = (Toolbar) view.findViewById(R.id.logintoolbar);
      //  mToolbar.setBackgroundColor(getResources().getColor(R.color.dark_green));


       // ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        setFont();
        search.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.GONE);

       // drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);


      //  mActionBarDrawerToggle = new ActionBarDrawerToggle(
       //         getActivity(), drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

       // drawer.setDrawerListener(mActionBarDrawerToggle);

      //  mActionBarDrawerToggle.syncState();

       // navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    search_home = 1;
                    home_category_flag = 5;
                    home_home_frag = 5;

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

        if (SplashActivity.isNetworkAvailable(getActivity())) {
            // getCategorySubcategory();

        } else {
            Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();

        }


       /* if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
       /* if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableList.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expandableList.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }
*/


        return view;
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void enableExpandableList() {

       // expandableList = (ExpandableListView) view.findViewById(R.id.left_drawer);



      /*  expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                home_category_selected_name=home_categories_list.get(groupPosition);
                Log.e("catg_name",home_category_selected_name);
                home_category_flag=2;
                drawer.closeDrawers();


                Browse_Category_Fragment newFragment = new Browse_Category_Fragment();
                Bundle args = new Bundle();
                args.putInt("position", groupPosition);
                newFragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_fragment,newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });
*/
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
               /* Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
*/
            }
        });
        // Listview on child click listener
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                // Temporary code:

                // till here
              /*  Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //  menuItem.setChecked(true);
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        if (charText.length() == 0) {

        } else {


        }

    }

    private void getCategorySubcategory() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GETCATEGORY, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("get_category", String.valueOf(response));
                        Products expertFAQ = null;
                        try {
                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            JSONArray DataArray = JObject.getJSONArray("data");
                            // JSONArray subCategoryArray = JObject.getJSONArray("subcategory");
                            //  if (subCategoryArray.length() != 0) {
                            if (DataArray.length() != 0) {
                                for (int i = 0; i < DataArray.length(); i++) {
                                    listChild = new ArrayList<>();

                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    name = jsonObject.getString("name");
                                    image_url = jsonObject.getString("image_url");
                                    icon_url = jsonObject.getString("icon_url");
                                    home_categories_list.add(name);
                                    home_categories_image_list.add(image_url);
                                    Log.e("image_url", image_url);

                                    expertFAQ = new Products();
                                    expertFAQ.setHeader_Name(name);
                                    expertFAQ.setCategory_icon(icon_url);
                                    expertFAQ.setCategory_image(image_url);
                                    listChild.add(expertFAQ);

                                    if (expertFAQ != null) {
                                        listHeader.add(expertFAQ);
                                        listDataChild.put(expertFAQ.getHeader_Name(), listChild);
                                    }
                                }
                            }
                            setAdapter();
                            //   }
                            //getQuantityType();
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

    public void setAdapter() {
        mMenuAdapter = new ExpandableListAdapter1(getActivity(), listHeader, listDataChild);
        expandableList.setAdapter(mMenuAdapter);
        mMenuAdapter.notifyDataSetChanged();
        for (int i = 0; i < listChild.size(); i++) {
            Log.e("child list", String.valueOf(listChild.get(i).getSubcategoryName()));
        }

    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto_Regular.ttf");
       // search.setTypeface(mDynoRegular);
        mFoodrecipes_sections.setTypeface(mDynoRegular);
    }

    @Override
    public boolean onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        if (doubleBackToExitPressedOnce) {
            getActivity().finish();
        }
*/
        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(getActivity(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else {

            return true;
        }*/
        return true;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

      *//*  getMenuInflater().inflate(R.menu.item, menu);
        return true;*//*
        super.onCreateOptionsMenu(menu, inflater);


        mToolbar.inflateMenu(R.menu.item);
        mToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       *//* int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);*//*

       *//* if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*//*

        switch (item.getItemId()) {
            case R.id.my_acc:
               *//* Intent my_acc = new Intent(getActivity(), Profile_Activity.class);
                startActivity(my_acc);*//*
                break;
            case R.id.my_orders:
               *//* Intent my_addr = new Intent(getActivity(), MyAddress_Activity.class);
                startActivity(my_addr);*//*
                break;
            case R.id.contact:

                break;
            case R.id.about:

                break;
            case R.id.terms:

                break;
            case R.id.change_pass:


                break;
            case R.id.signin:

                home_signin_backpress = 1;
                Intent intent2 = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }

        return true;
    }
*/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        filter(text);
        return false;
    }

  /*  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        int id = item.getItemId();
        switch (id) {
          *//*  case R.id.nav_item1:
                navigationView.getMenu().getItem(0).setChecked(true);
                Notification_Fragment notification_fragment = new Notification_Fragment();
                Toast.makeText(getActivity(), "No Sub-Categories Added", Toast.LENGTH_LONG).show();


                return true;
            *//*
            default:
                return true;
        }

    }*/

    public void open() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("No Sub-Categories Added");
        alertDialogBuilder.setCancelable(false);


        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        alertDialog.dismiss();
                    }
                });

        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));
    }

    @Override
    public void onStop() {
        /*// make sure to call stopAutoCycle() on the slider before activity
        or fragment is destroyed*/
        imageSlider.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
     /*   Toast.makeText(this, slider.getBundle().get("extra") + "",
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private void getSections() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_SECTIONS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("get_sections", String.valueOf(response));
                        relativeLayout.setVisibility(View.VISIBLE);

                        try {

                            JSONObject JObject = new JSONObject(String.valueOf(response));

                            JSONArray DataArray = JObject.getJSONArray("data");
                            SectionDataModel dm = null;
                            ArrayList<SingleItemModel> singleItemList = null;
                            SingleItemModel singleItemModel = null;

                            if (DataArray.length() != 0) {


                                singleItemList = new ArrayList<SingleItemModel>();
                                allSampleData = new ArrayList<SectionDataModel>();

                                for (int i = 0; i < DataArray.length(); i++) {
                                    dm = new SectionDataModel();
                                    banner_count = DataArray.length();
                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    transaction_with = jsonObject.getString("transaction_with");
                                    section_name = jsonObject.getString("section_name");
                                    ad_status = jsonObject.getInt("addstatus");
                                    // section_name_list.add(section_name);
                                    Log.e("section_name", section_name);
                                    Log.e("transaction_with", transaction_with);
                                    String test = section_name;
                                    caption_letter = test.substring(0, 1);
                                    Log.e("caption_letter", String.valueOf(caption_letter));
                                    dm.setHeaderTitle(section_name);
                                    dm.setTransaction(transaction_with);
                                    dm.setCaption(caption_letter);
                                    dm.setAdStatus(ad_status);


                                    if (jsonObject.has("categorys")) {
                                        categoryOrproduct = 1;
                                        JSONArray category_subcategory = jsonObject.getJSONArray("categorys");

                                        if (category_subcategory.length() != 0) {
                                            singleItem = new ArrayList<SingleItemModel>();
                                            Log.e("category_subcategory", String.valueOf(category_subcategory.length()));
                                            for (int j = 0; j < category_subcategory.length(); j++) {
                                                singleItemModel = new SingleItemModel();
                                                JSONObject jsonObject1 = category_subcategory.getJSONObject(j);
                                                name_category = jsonObject1.getString("name");
                                                image_url_category = jsonObject1.getString("image_url");
                                                id = jsonObject1.getInt("id");
                                                singleItemModel.setId(id);
                                                singleItemModel.setName(name_category);
                                                Log.e("name_category", name_category);
                                                Log.e("image_url_category", image_url_category);
                                                singleItem.add(new SingleItemModel(name_category, image_url_category, id, null, 1,productRandomId));                                                deals_list.add(singleItemModel);

                                                Log.e("sections", "sectoims");


                                            }

                                            dm.setAllItemsInSection(singleItem);

                                        }
                                        allSampleData.add(dm);
                                        Log.e("list", String.valueOf(allSampleData));
                                        if (categoryOrproduct == 1) {
                                            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData);

                                            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                            my_recycler_view.setNestedScrollingEnabled(false);
                                            my_recycler_view.setAdapter(adapter);
                                        }

                                    }


                                    if (jsonObject.has("products")) {
                                        categoryOrproduct = 2;

                                        JSONArray product_subcategory = jsonObject.getJSONArray("products");
                                        if (product_subcategory.length() != 0) {
                                            Log.e("product_subcategory", String.valueOf(product_subcategory.length()));
                                            for (int j = 0; j < product_subcategory.length(); j++) {
                                                singleItemModel = new SingleItemModel();
                                                JSONObject jsonObject1 = product_subcategory.getJSONObject(j);
                                                name_category = jsonObject1.getString("product_name");
                                                image_url_category = jsonObject1.getString("display_image");
                                                int discount_price = jsonObject1.getInt("discount");
                                                product_discount = jsonObject1.getString("actual_selling_amount");

                                                product_random_id = jsonObject1.getString("product_random_id");
                                                category = jsonObject1.getString("category");
                                                sub_category = jsonObject1.getString("sub_category");


                                                singleItemModel.setProductRandomId(product_random_id);
                                                int id = jsonObject1.getInt("id");
                                               // int category = jsonObject1.getInt("category");
                                                singleItemModel.setId(id);
                                                singleItemModel.setDiscount_price(discount_price);


                                                JSONArray product_price = jsonObject1.getJSONArray("product_price");
                                                //list_browse_products.add(id);
                                                if (product_price.length() != 0) {
                                                    for (int k = 0; k < product_price.length(); k++) {
                                                        Log.e("datacount", String.valueOf(product_price.length()));
                                                        JSONObject jsonObject2 = product_price.getJSONObject(k);
                                                        product_price_id = jsonObject2.getString("id");
                                                        Log.e("product_price_id", product_price_id);


                                                    }
                                                    singleItemModel.setProduct_price_id(product_price_id);
                                                    Log.e("setProduct_priceId", String.valueOf(singleItemModel.getProduct_price_id()));
                                                }

                                                SingleItemModel singleItem = new SingleItemModel(name_category, image_url_category, id, null, 2,product_random_id);
                                                //singleItem.setCategory(category);
                                                singleItemList.add(singleItem);


                                                Log.e("sections", "sectoims");
                                                dm.setAllItemsInSection(singleItemList);
                                            }
                                            allSampleData.add(dm);

                                        }
                                        if (categoryOrproduct == 2) {
                                            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData);

                                            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                            my_recycler_view.setAdapter(adapter);
                                        }
                                    }

                                }





                            }
                            getRecipeTypeCategory();
                        } catch (Exception e) {
                            // Log.i("Err", e.getLocalizedMessage());
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

    private void getRecipeTypeCategory() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_RECIPETYPE_CATEGORY, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("getRecipeTypeCategory", String.valueOf(response));


                        try {


                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            JSONArray DataArray = JObject.getJSONArray("data");
                            Recipe recipe = null;
                            recipeArrayList = new ArrayList<Recipe>();
                            if (DataArray.length() != 0) {
                                for (int i = 0; i < DataArray.length(); i++) {

                                    banner_count = DataArray.length();
                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    String food_name = jsonObject.getString("name");
                                    String food_url = jsonObject.getString("image_url");
                                    int id = jsonObject.getInt("id");

                                    recipe = new Recipe();

                                    recipe.setTitle(food_name);
                                    recipe.setImage(food_url);
                                    recipe.setRecipeTypeId(id);
                                    recipeArrayList.add(recipe);


                                }
                            } else {
                                Toast.makeText(getActivity(), "No Recipes Sections ! ", Toast.LENGTH_LONG).show();

                            }


                            Recipes_Adapter adapter = new Recipes_Adapter(getActivity(), recipeArrayList);

                            foodrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            foodrecyclerView.setNestedScrollingEnabled(false);
                            foodrecyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.i("Err", e.getLocalizedMessage());
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

    private void getBannerImage() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_BANNER_IMAGE, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("get_banner_img", String.valueOf(response));

                        try {

                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            JSONArray DataArray = JObject.getJSONArray("data");
                            Home_Products home_products = null;
                            if (DataArray.length() != 0) {
                                for (int i = 0; i < DataArray.length(); i++) {

                                    banner_count = DataArray.length();
                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    image_url = jsonObject.getString("image_url");
                                    banner_image_list.add(Constants.BANNER_IMAGES + image_url);
                                    Log.e("image_url", Constants.BANNER_IMAGES + image_url);
                                    Log.e("image_list", String.valueOf(banner_image_list));

                                    home_products = new Home_Products();

                                    home_products.setImage_Banner(Constants.BANNER_IMAGES + image_url);

                                    HashMap<String, String> file_maps = new HashMap<String, String>();

                                    file_maps.put("1", banner_image_list.get(i));
                                    Log.e("maps", banner_image_list.get(i));
                                    Log.e("ms", home_products.getImage_Banner());


/*

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
         file_maps.put("1",R.drawable.prod1);
            file_maps.put("1",R.drawable.prod2);
*/


                                    for (String name : file_maps.keySet()) {
                                        TextSliderView textSliderView = new TextSliderView(getActivity());
                                        // initialize a SliderLayout
                                        textSliderView
                                                .image(file_maps.get(name))
                                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                                .setOnSliderClickListener(Home_Fragment.this);

                                        //add your extra information
                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle()
                                                .putString("extra", name);

                                        imageSlider.addSlider(textSliderView);
                                        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                        //imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                        imageSlider.setCustomAnimation(new DescriptionAnimation());
                                        imageSlider.setDuration(2000);
                                        imageSlider.addOnPageChangeListener((Home_Fragment.this));


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

    public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

        private ArrayList<SectionDataModel> dataList;
        private Context mContext;
        private Typeface mDynoRegular;

        public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_screen_list_item, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(final ItemRowHolder itemRowHolder, int i) {

            final String sectionName = dataList.get(i).getHeaderTitle();
            final int ad_status = dataList.get(i).getAdStatus();
            final String transaction = dataList.get(i).getTransaction();

            final ArrayList<SingleItemModel> singleSectionItems = dataList.get(i).getAllItemsInSection();

            itemRowHolder.itemTitle.setText(sectionName);
            //itemRowHolder.itemCaption.setText(caption);

            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, transaction, singleSectionItems);

            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
            if (ad_status == 0) {
                itemRowHolder.slider.setVisibility(View.VISIBLE);
            } else {
                itemRowHolder.slider.setVisibility(View.GONE);


            }

            if (transaction.equals("category")) {
                itemRowHolder.viewall.setVisibility(View.VISIBLE);
                itemRowHolder.viewall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Home_Fragment.home_home_frag = 2;
                        Home_Fragment.home_category_selected_name = singleSectionItems.get(0).getName();


                        Category_Fragment newFragment = new Category_Fragment();

                        Bundle args = new Bundle();
                        args.putInt("position", 0);

                        newFragment.setArguments(args);


                        FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rldContainer, newFragment);
                        transaction.addToBackStack("Some String");
                        transaction.commit();


                    }
                });


            }else
            {
                itemRowHolder.viewall.setVisibility(View.GONE);
            }

/*
        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();



            }
        });*/


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/

            itemRowHolder.recycler_view_list.addOnItemTouchListener(new RecyclerItemListener(mContext, itemRowHolder.recycler_view_list,
                    new RecyclerItemListener.RecyclerTouchListener() {
                        public void onClickItem(View v, int position) {


                            if (transaction.equals("category")) {
                                Log.i("Message", transaction);
                                Home_Fragment.home_home_frag = 2;
                                Home_Fragment.home_category_selected_name = singleSectionItems.get(position).getName();
                                String name = singleSectionItems.get(position).getName();
                                Category_Fragment newFragment = new Category_Fragment();
                                Bundle args = new Bundle();
                                args.putInt("position", position);
                                args.putString("name", name);
                                newFragment.setArguments(args);

                                FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.rldContainer, newFragment);
                                transaction.addToBackStack("Some String");
                                transaction.commit();

                            } else if (transaction.equals("product")) {

                                home_onclick=1;
                                Product_Description_Fragment newFragment = new Product_Description_Fragment();
                                Bundle args = new Bundle();
                                /*args.putInt("position", position);
                                args.putString("product_random_id", singleSectionItems.get(position).getProductRandomId());
                                args.putString("category", singleSectionItems.get(position).getProductCategory());
                                args.putString("subcategory", singleSectionItems.get(position).getProductSubcategory());
                                args.putString("id", singleSectionItems.get(position).getProductId());
                                args.putString("qty_name", dataList.get(position).getAllItemsInSection().get(position).getProductQtyName());
                                args.putString("price", singleSectionItems.get(position).getProductActualamount());
                                args.putString("qty", singleSectionItems.get(position).getProductQuantity());*/
                                args.putString("search_word",singleSectionItems.get(position).getProduct_random_id());
                                // args.putString("amount","RlPqzeihOlGMlJcsVROF6NzjYTONopOlfJQTrm6x19_7");
                                // args.putString("search_word","RlPqzeihOlGMlJcsVROF6NzjYTONopOlfJQTrm6x19_7");
                               /* args.putString("product_price_id", singleSectionItems.get(position).getProductId());
                                args.putInt("product_id", singleSectionItems.get(position).getId());
                                args.putString("price_product_id", singleSectionItems.get(position).getProductId());

                                int prod = singleSectionItems.get(position).getDiscount_price();
                                if (prod > 0) {
                                    args.putString("other_price", singleSectionItems.get(position).getProductPrice());

                                } else {
                                    args.putString("other_price", "empty");

                                }*/
                                newFragment.setArguments(args);

                                Log.e("args",args.toString());

                                FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.rldContainer, newFragment);
                                transaction.addToBackStack("Some String");
                                transaction.commit();

                            }
                        }

                        public void onLongClickItem(View v, int position) {
                            System.out.println("On Long Click Item interface");
                        }
                    }));

        }

        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView itemTitle, viewall;

            protected RecyclerView recycler_view_list;
            protected ImageView slider;


            public ItemRowHolder(View view) {
                super(view);

                this.itemTitle = (TextView) view.findViewById(R.id.deals);
                this.viewall = (TextView) view.findViewById(R.id.viewall);
                this.slider = (ImageView) view.findViewById(R.id.slider);
                this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto_Regular.ttf");
                itemTitle.setTypeface(mDynoRegular);
                viewall.setTypeface(mDynoRegular);


            }
        }

    }
}





