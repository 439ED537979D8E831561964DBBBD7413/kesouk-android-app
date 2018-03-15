package com.tstl.kesouk.Fragments;

/**
 * Created by user on 24-Jan-18.
 */

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tstl.kesouk.Activity.Login_Register_Activity;
import com.tstl.kesouk.Activity.Navigation_Tab_Activity;
import com.tstl.kesouk.Adapter.Category_Adapter;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.Cart;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Products;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.Model.SectionDataModel;
import com.tstl.kesouk.Model.SingleItemModel;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;
import static com.tstl.kesouk.Fragments.Customer_Fragment.cust_category_selected_name;
import static com.tstl.kesouk.Fragments.Customer_Fragment.cust_search_home;
import static com.tstl.kesouk.Fragments.Home_Fragment.home_category_selected_name;
import static com.tstl.kesouk.Fragments.Home_Fragment.search_home;


public class Category_Fragment extends Fragment {

    private RecyclerView listview_Category, products_listview;
    private Typeface mDynoRegular;
    //private Toolbar mToolbar;
    //private TextView mToolbarTitle;
    private ImageView mFilter, mSort, mSettings, mClose;
    private EditText mSearch;
    Category_Adapter browse_category_adapter;
    int sub_category_id, status, product_quantity_type;
    String name, image_url, icon_url, product_name, product_image, product_price, product_price_id, product_price_MarketPrice, product_price_kesoukPrice, product_price_displayname;
    private ArrayList<String> product_pricelist = new ArrayList<>();
    Integer[] Bcatid = {
            R.drawable.prod4,
            R.drawable.prod5,
            R.drawable.prod6


    };
    int temp_search_home = 0, temp_cust_home = 0;
    View view;
    private int mPosition = -1;
    DB db;
    private TextView mB_lable, mBrowse_label, mLow, mHigh, mAlpha, mToolbarTitle, item_count;


    ImageView mHL_Check, mAO_Check, mLH_Check;
    LinearLayout HL_Layout, LH_Layout, AO_Layout;
    public static Spinner spinner;
    Dialog dialog;
    private List<Browse_Category> browse_categoryList;
    private List<Products> horizontal_category_list;


    public ArrayList<Browse_Category> list_browse_products;
    public ArrayList<Recipe> receipe_productQtyList;
    Object spinner_Selected_text;
    ArrayList<Browse_Category> browse_category_list = new ArrayList<>();
    int search_option = 0, horizontal_category_option = 0;
    public static int sort_Option = 0;
    String searchText, product_selling_price, product_qty_name, product_random_id = "", is_express_delivery = "";
    int currentpage = 1, lastpage = 0;
    int product_discount;
    //  ImageView img_back,settings,search;
    String search_word1 = "";
    public ArrayList<String> quantityList;
    public ArrayList<String> pricelist;
    public ArrayList<String> actual_pricelist;
    String similar_category, similar_subcategory, similar_id, total_amount, quanity_with_name;

    public static Category_Fragment newInstance() {
        Category_Fragment fragment = new Category_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_list_main, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        products_listview = (RecyclerView) view.findViewById(R.id.product_listing);
        // mSearch = (EditText) view.findViewById(R.id.search_new);


        //  mToolbar = (Toolbar) view.findViewById(R.id.logintoolbar);

        //  img_back = (ImageView) view.findViewById(R.id.img);
        // settings = (ImageView) view.findViewById(R.id.settings);
        // search = (ImageView) view.findViewById(R.id.search);
        // settings.setVisibility(View.GONE);
        // search.setVisibility(View.GONE);
        // mFilter = (ImageView) view.findViewById(R.id.filter);
        //  mSort = (ImageView) view.findViewById(R.id.sort);
        //mClose = (ImageView) view.findViewById(R.id.close_img);
//


        db = new DB(getActivity());

        //  mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        item_count = (TextView) view.findViewById(R.id.item_count);

        //  mToolbarTitle.setVisibility(View.VISIBLE);


        //    ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setFont();

        /*mToolbar.setNavigationIcon(R.drawable.back_button);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                Intent intent=new Intent(getActivity(), Navigation_Tab_Activity.class);
                getActivity().startActivity(intent);

            }
        });*/
       /* img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.getAllLogin().size() == 1) {


                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        System.exit(0);
                    }

                } else {
                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    } else {
                        Intent intent = new Intent(getActivity(), Login_Register_Activity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }

                }

            }
        });*/
        Bundle bundle1 = this.getArguments();
        if (bundle1 != null) {

            search_word1 = bundle1.getString("name");
            search.setVisibility(View.GONE);
            toolbar_title.setVisibility(View.VISIBLE);
            toolbar_title.setText(search_word1);


        }
        if (search_home == 1) {
            Bundle bundle2 = this.getArguments();
            if (bundle2 != null) {

                search_word1 = bundle2.getString("search");
                search.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(search_word1.toUpperCase());


            }
        } else if (cust_search_home == 1) {
            Bundle bundle2 = this.getArguments();
            if (bundle2 != null) {

                search_word1 = bundle2.getString("search");
                search.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(search_word1.toUpperCase());


            }
        }

        getQuantityType();


    /*    mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                    search_option = 1;
                    horizontal_category_option = 0;
                    searchText = mSearch.getText().toString();
                    getProductList(searchText);
                    spinner.setSelection(0);
                    handled = true;
                }
                return handled;
            }
        });*/



   /*     mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_option = 0;
                mSearch.setText("");

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                products_listview.setLayoutManager(mLayoutManager);

                browse_category_adapter = new Browse_Category_Adapter(getActivity(), list_browse_products, 2);
                //Horizontal_Category_list();
                Log.e("product_list_if", String.valueOf(list_browse_products));

                products_listview.setAdapter(null);

                browse_category_adapter.notifyDataSetChanged();


            }
        });
*/

     /*
        mSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(getActivity());
                dialog.setTitle("Select ...");
                dialog.setContentView(R.layout.filters_layout);
                dialog.show();

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.TOP | Gravity.RIGHT;
                wlp.x = 60;   //x position
                wlp.y = 60;   //y position
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);


                mHL_Check = (ImageView) dialog.findViewById(R.id.HL_tick);
                mAO_Check = (ImageView) dialog.findViewById(R.id.AO_tick);
                mLH_Check = (ImageView) dialog.findViewById(R.id.LH_tick);
                HL_Layout = (LinearLayout) dialog.findViewById(R.id.HL_layout);
                LH_Layout = (LinearLayout) dialog.findViewById(R.id.LH_layout);
                AO_Layout = (LinearLayout) dialog.findViewById(R.id.AO_layout);

                mLow = (TextView) dialog.findViewById(R.id.lh);
                mHigh = (TextView) dialog.findViewById(R.id.HL);
                mAlpha = (TextView) dialog.findViewById(R.id.ao);
                mLow.setTypeface(mDynoRegular);
                mHigh.setTypeface(mDynoRegular);
                mAlpha.setTypeface(mDynoRegular);

                if (sort_Option == 1) {
                    mAO_Check.setVisibility(View.GONE);
                    mHL_Check.setVisibility(View.GONE);
                    mLH_Check.setVisibility(View.VISIBLE);
                } else if (sort_Option == 2) {
                    mAO_Check.setVisibility(View.GONE);
                    mHL_Check.setVisibility(View.VISIBLE);
                    mLH_Check.setVisibility(View.GONE);
                } else if (sort_Option == 3) {
                    mAO_Check.setVisibility(View.VISIBLE);
                    mHL_Check.setVisibility(View.GONE);
                    mLH_Check.setVisibility(View.GONE);

                } else {
                    mAO_Check.setVisibility(View.GONE);
                    mHL_Check.setVisibility(View.GONE);
                    mLH_Check.setVisibility(View.GONE);

                }

                LH_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAO_Check.setVisibility(View.GONE);
                        mHL_Check.setVisibility(View.GONE);
                        mLH_Check.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                        sort_Option = 1;  //filter option with low to high selected

                        Log.e("lowtohigh", String.valueOf(sort_Option));

                        if (spinner.getSelectedItem() != null) {
                            getProductList(spinner_Selected_text.toString());
                        } else if (horizontal_category_option == 1 && mSearch.getText().toString().equals("")) {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }

                            Log.e("horsontal", "horz");
                        } else if (search_option == 1 || !mSearch.getText().toString().equals("")) {
                            Log.e("serh", "seacrh");
                            getProductList(mSearch.getText().toString());
                        } else if (search_option == 0) {

                        } else {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }
                            Log.e("Home_act", "activity");

                        }

                    }
                });
                HL_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAO_Check.setVisibility(View.GONE);
                        mHL_Check.setVisibility(View.VISIBLE);
                        mLH_Check.setVisibility(View.GONE);
                        dialog.dismiss();


                        sort_Option = 2;   //filter option with high to lot selected
                        Log.e("hightolow", String.valueOf(sort_Option));

                        if (spinner.getSelectedItem() != null) {
                            getProductList(spinner_Selected_text.toString());
                        } else if (horizontal_category_option == 1 && mSearch.getText().toString().equals("")) {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }
                        } else if (search_option == 1 || !mSearch.getText().toString().equals("")) {
                            getProductList(mSearch.getText().toString());
                        } else if (search_option == 0) {

                        } else {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }
                            Log.e("Home_act", "activity");

                        }

                    }
                });


                AO_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAO_Check.setVisibility(View.VISIBLE);
                        mHL_Check.setVisibility(View.GONE);
                        mLH_Check.setVisibility(View.GONE);
                        dialog.dismiss();


                        sort_Option = 3;  //filter option with alpha selected
                        Log.e("alpha", String.valueOf(sort_Option));

                        if (spinner.getSelectedItem() != null) {
                            getProductList(spinner_Selected_text.toString());
                        } else if (horizontal_category_option == 1 && mSearch.getText().toString().equals("")) {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }
                        } else if (search_option == 1 || !mSearch.getText().toString().equals("")) {
                            getProductList(mSearch.getText().toString());
                        } else if (search_option == 0) {

                        } else {
                            if (db.getAllLogin().size() == 1) {
                                getProductList(category_selected_name);
                            } else {
                                getProductList(home_category_selected_name);
                            }
                            Log.e("Home_act", "activity");

                        }
                    }
                });


            }
        });*/

        // Spinner element
        //  Spinner spinner = (Spinner) findViewById(R.id.spinner);


       /* if (db.getAllLogin().size() == 1) {
            if (category_flag == 1) {


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    // int myInt = bundle.getInt("position",defaultvale);

                    mPosition = bundle.getInt("position");
                    Log.i("position", String.valueOf(mPosition));
                    spinner.setSelection(mPosition + 1);
                }


            }
        } else {
            if (home_category_flag == 1) {


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    // int myInt = bundle.getInt("position",defaultvale);

                    mPosition = bundle.getInt("position");
                    Log.i("position", String.valueOf(mPosition));
                    spinner.setSelection(mPosition + 1);
                }


//Extract the data…

            }
        }

        if (db.getAllLogin().size() == 1) {
            if (home_frag == 2) {
                category_flag = 0;


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    // int myInt = bundle.getInt("position",defaultvale);

                    mPosition = bundle.getInt("position");
                    Log.i("position", String.valueOf(mPosition));
                    spinner.setSelection(mPosition + 1);
                }


            } else {

                if (category_flag == 2) {


                    Bundle bundle = this.getArguments();
                    if (bundle != null) {
                        // int myInt = bundle.getInt("position",defaultvale);

                        mPosition = bundle.getInt("position");
                        Log.i("position", String.valueOf(mPosition));
                        spinner.setSelection(mPosition + 1);
                    }

                } else if (home_category_flag == 2) {


                    Bundle bundle = this.getArguments();
                    if (bundle != null) {
                        // int myInt = bundle.getInt("position",defaultvale);

                        mPosition = bundle.getInt("position");
                        Log.i("position", String.valueOf(mPosition));
                        spinner.setSelection(mPosition + 1);
                    }

                }
            }


        } else {
            if (home_home_frag == 2) {
                home_category_flag = 0;


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    // int myInt = bundle.getInt("position",defaultvale);

                    mPosition = bundle.getInt("position");
                    Log.i("position", String.valueOf(mPosition));
                    spinner.setSelection(mPosition + 1);
                }


            } else {

                if (category_flag == 2) {


                    Bundle bundle = this.getArguments();
                    if (bundle != null) {
                        // int myInt = bundle.getInt("position",defaultvale);

                        mPosition = bundle.getInt("position");
                        Log.i("position", String.valueOf(mPosition));
                        spinner.setSelection(mPosition + 1);
                    }

                } else if (home_category_flag == 2) {


                    Bundle bundle = this.getArguments();
                    if (bundle != null) {
                        // int myInt = bundle.getInt("position",defaultvale);

                        mPosition = bundle.getInt("position");
                        Log.i("position", String.valueOf(mPosition));
                        spinner.setSelection(mPosition + 1);
                    }

                }
            }





        }*/

     /*   if (search_home == 1) {
            if (db.getAllLogin().size() == 0) {
                if (search_word1.equals("") || search_word1.equals("null") || search_word1.equals(null)) {

                    getProductList(home_category_selected_name);
                } else {
                    getProductList(search_word1);

                }

            } else {
                if (search_word1.equals("") || search_word1.equals("null") || search_word1.equals(null)) {

                    getProductList(cust_category_selected_name);
                } else {
                    getProductList(search_word1);

                }


            }
        }
        else
            if (cust_search_home==1)
            {
                if (search_word1.equals("") || search_word1.equals("null") || search_word1.equals(null)) {

                    getProductList(cust_category_selected_name);
                } else {
                    getProductList(search_word1);

                }
            }
*/
        return view;
    }


    private void getQuantityType() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_QUANTITY_TYPE, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("get_quan_type", String.valueOf(response));

                        try {

                            sort_Option = 0;
                           /* if (db.getAllLogin().size() == 1) {
                                getProductList(cust_category_selected_name);

                            }
                            else
                            {
                                getProductList(home_category_selected_name);
                            }*/

                            if (db.getAllLogin().size() == 1) {
                                if (cust_search_home == 1) {
                                    getProductList(search_word1);
                                    temp_search_home = 1;
                                } else {
                                    getProductList(cust_category_selected_name);

                                }
                            } else {
                                if (search_home == 1) {
                                    getProductList(search_word1);
                                    temp_cust_home = 1;
                                } else {
                                    getProductList(home_category_selected_name);

                                }
                            }

                            if (spinner.getSelectedItemPosition() == 0) {

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(getActivity().getApplicationContext(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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


    private void getProductList(String product_search_name) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> params = new HashMap<String, String>();

/*

        Log.e("search_keyword", product_search_name);
        Log.e("per_page", "10");
        Log.e("page", String.valueOf(currentpage));
*/


        params.put("search_keyword", product_search_name);

        params.put("per_page", "10");
        params.put("page", "1");//curentpage
        params.put("cartcheck", "0");
        params.put("customer_id", "0");

        if (sort_Option == 1) {
            params.put("relevancefilter", "LH");
            Log.e("relevancefilter", "LH");
        } else if (sort_Option == 2) {
            params.put("relevancefilter", "HL");
            Log.e("relevancefilter", "HL");
        } else if (sort_Option == 3) {
            params.put("relevancefilter", "AL");
            Log.e("relevancefilter", "AL");
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.GET_PRODUCT_LIST, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("product_list", String.valueOf(object));


                            String status = object.getString("status");
                            if (status.equals("Success")) {

                                JSONObject JObject = new JSONObject(String.valueOf(object));
                                JSONObject JObject1 = JObject.getJSONObject("data");
                                currentpage = JObject1.getInt("current_page");
                                lastpage = JObject1.getInt("last_page");
                                JSONArray DataArray = JObject1.getJSONArray("data");
                                Log.e("data", "data");
                                Browse_Category browse_category = null;
                                Recipe recipe = null;

                                if (DataArray.length() != 0) {
                                    item_count.setText(DataArray.length() + " Items");
                                    list_browse_products = new ArrayList<>();
                                    receipe_productQtyList = new ArrayList<>();
                                    for (int i = 0; i < DataArray.length(); i++) {
                                        Log.e("datacount", String.valueOf(DataArray.length()));
                                        JSONObject jsonObject = DataArray.getJSONObject(i);
                                        product_name = jsonObject.getString("product_name");
                                        product_image = jsonObject.getString("display_image");
                                        product_random_id = jsonObject.getString("product_random_id");
                                        product_price = jsonObject.getString("price");
                                        product_quantity_type = jsonObject.getInt("quantity_type");
                                        if (jsonObject.has("is_express_delivery") && !jsonObject.isNull("is_express_delivery")) {
                                            is_express_delivery = jsonObject.getString("is_express_delivery");

                                        }
                                        int id = jsonObject.getInt("id");
                                        // int category_id = jsonObject.getInt("category");
                                        product_selling_price = jsonObject.getString("actual_selling_amount");
                                        product_discount = jsonObject.getInt("discount");
                                        float rating = jsonObject.getInt("rating_star");
                                        similar_category = jsonObject.getString("category");
                                        similar_subcategory = jsonObject.getString("sub_category");
                                        similar_id = jsonObject.getString("id");


                                        browse_category = new Browse_Category();
                                        browse_category.setProd_name(product_name);
                                        browse_category.setDukanPrice(product_price);
                                        browse_category.setProduct_image(product_image);
                                        browse_category.setProduct_random_id(product_random_id);
                                        browse_category.setProduct_quantity_type(product_quantity_type);
                                        browse_category.setProduct_id(id);
                                        //browse_category.setCategoryId(category_id);
                                        browse_category.setProduct_selling_price(product_selling_price);
                                        browse_category.setProduct_discount(product_discount);
                                        browse_category.setRatingStar(rating);
                                        if (jsonObject.has("is_express_delivery")) {
                                            browse_category.setIs_express_delivery(is_express_delivery);

                                        }
                                        browse_category.setSimilar_category(similar_category);
                                        browse_category.setSimilar_subcategory(similar_subcategory);
                                        browse_category.setSimilar_id(similar_id);
                                        recipe = new Recipe();


                                        JSONArray product_price = jsonObject.getJSONArray("product_price");
                                        //list_browse_products.add(id);
                                        quantityList = new ArrayList<>();
                                        pricelist = new ArrayList<>();
                                        actual_pricelist = new ArrayList<>();
                                        if (product_price.length() != 0) {
                                            for (int j = 0; j < product_price.length(); j++) {
                                                Log.e("datacountPP", String.valueOf(product_price.length()));
                                                JSONObject jsonObject1 = product_price.getJSONObject(j);
                                                product_price_id = jsonObject1.getString("id");
                                                product_price_MarketPrice = jsonObject1.getString("price");
                                                product_price_kesoukPrice = jsonObject1.getString("actual_selling_amount");
                                                product_qty_name = jsonObject1.getString("quantity");
                                                recipe.setProduct_priceId(product_price_id);
                                                recipe.setQuantity_Marketprice(product_price_MarketPrice);
                                                recipe.setQuanity_kesoukPrice(product_price_kesoukPrice);
                                                recipe.setProduct_price_qty_name(product_qty_name);

                                                quantityList.add(jsonObject1.getString("quantity").toString());
                                                pricelist.add(jsonObject1.getString("price").toString());
                                                actual_pricelist.add(jsonObject1.getString("actual_selling_amount").toString());


                                            }
                                            browse_category.setProduct_priceId(product_price_id);
                                            Log.e("quantityList", String.valueOf(quantityList));
                                            recipe.setQuantityListPojo(quantityList);
                                            recipe.setActualListPojo(actual_pricelist);
                                            recipe.setPriceListPojo(pricelist);
                                            receipe_productQtyList.add(recipe);
                                            Log.e("setQuantityListPojo", String.valueOf(recipe.getQuantityListPojo()));

                                        } else {

                                        }


                                        JSONObject jsonObject1 = jsonObject.getJSONObject("product_quantity");
                                        product_qty_name = jsonObject1.getString("display_name");
                                        browse_category.setProduct_qty_name(product_qty_name);
                                        Log.e("product_qty_name", String.valueOf(browse_category.getProduct_qty_name()));


                                        Log.e("product_name", browse_category.getProd_name());
                                        Log.e("product_image", browse_category.getProduct_image());
                                        Log.e("product_price", browse_category.getDukanPrice());
                                        Log.e("product_quantity_type", String.valueOf(browse_category.getProduct_quantity_type()));

                                        Log.e("browse", "browse");
                                        Log.e("browse", list_browse_products.toString());
                                        list_browse_products.add(browse_category);
                                        Log.e("product_list_adapter", String.valueOf(list_browse_products));

                                    }
                                    //search_home = 0;
                                    // cust_search_home=0;
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                                    products_listview.setLayoutManager(mLayoutManager);
                                    browse_category_adapter = new Category_Adapter(getActivity(), list_browse_products, receipe_productQtyList, 1, quantityList, search_word1);
                                    //Horizontal_Category_list();
                                    Log.e("product_list_if", String.valueOf(list_browse_products));


                                    products_listview.setAdapter(browse_category_adapter);

                                    browse_category_adapter.notifyDataSetChanged();

                                } else

                                {

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                                    products_listview.setLayoutManager(mLayoutManager);

                                    browse_category_adapter = new Category_Adapter(getActivity(), list_browse_products, receipe_productQtyList, 2, quantityList, search_word1);
                                    //Horizontal_Category_list();
                                    Log.e("product_list_if", String.valueOf(list_browse_products));

                                    products_listview.setAdapter(null);

                                    browse_category_adapter.notifyDataSetChanged();
                                }


                            } else {
                                String reason = object.getString("reason");
                                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();

                            }

                            // setAdapter();


                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("catch", e.getLocalizedMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("verify_otp_error", "error" + volleyError);
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(getActivity(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                VolleyLog.d("responseError", "Error: " + volleyError);

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                return header;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);

    }


    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto_Regular.ttf");

        // mToolbarTitle.setTypeface(mDynoRegular);
        item_count.setTypeface(mDynoRegular);
    }


}
