package com.tstl.kesouk.Fragments;

/**
 * Created by user on 24-Jan-18.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.tstl.kesouk.Activity.Login_Activity;
import com.tstl.kesouk.Activity.Login_Register_Activity;
import com.tstl.kesouk.Activity.MyAddress_Activity;
import com.tstl.kesouk.Activity.Navigation_Tab_Activity;
import com.tstl.kesouk.Activity.Profile_Activity;
import com.tstl.kesouk.Activity.TabMain_Activity;
import com.tstl.kesouk.Adapter.Category_Adapter;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.Cart;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Products;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;
import static com.tstl.kesouk.Fragments.Home_Fragment.home_category_selected_name;
import static com.tstl.kesouk.Fragments.Home_Fragment.home_onclick;


public class Favorites_Fragment extends Fragment {

    private RecyclerView listview_Category, products_listview;
    private Typeface mDynoRegular;
    //private TextView mToolbarTitle;
    private ImageView mFilter, mSort, mSettings, mClose;
    private EditText mSearch;
    Category_Adapter browse_category_adapter;
    int sub_category_id, status, product_quantity_type;
    String name, image_url, product_discount1, product_name, product_image, product_price, product_price_id, product_price_MarketPrice, product_price_kesoukPrice, product_price_displayname;
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
    String product_price_amount, product_selling_price, product_qty_name, product_random_id = "", is_express_delivery = "";
    int currentpage = 1, lastpage = 0;
    int product_discount;
    String search_word = "";
    public ArrayList<String> quantityList;
    public ArrayList<String> pricelist;
    public ArrayList<String> actual_pricelist;
    String similar_category, similar_subcategory, similar_id, total_amount, quanity_with_name;
    TextView no_wishlist;
    Button mExplore;
    ArrayList<Recipe> cartArrayList = null;
    ArrayList<Browse_Category> cart_priceList = null;
    RelativeLayout filter_layout;
    public static int fav_logout_backpress = 0, fav_home_signin_backpress = 0;

    public static Favorites_Fragment newInstance() {
        Favorites_Fragment fragment = new Favorites_Fragment();
        return fragment;
    }

    public Favorites_Fragment() {
        // Required empty public constructor
    }
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wishlist_main, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        products_listview = (RecyclerView) view.findViewById(R.id.product_listing);
        // mSearch = (EditText) view.findViewById(R.id.search_new);


        //  mToolbar = (Toolbar) view.findViewById(R.id.logintoolbar);
        filter_layout = (RelativeLayout) view.findViewById(R.id.filter_layout);

        // img_back = (ImageView) view.findViewById(R.id.img);
        // settings = (ImageView) view.findViewById(R.id.settings);
        // search = (ImageView) view.findViewById(R.id.search);
        //   img_back.setVisibility(View.GONE);
      /*  settings.setVisibility(View.GONE);
        search.setVisibility(View.GONE);*/
        // mFilter = (ImageView) view.findViewById(R.id.filter);
        //  mSort = (ImageView) view.findViewById(R.id.sort);
        //mClose = (ImageView) view.findViewById(R.id.close_img);
//


        db = new DB(getActivity());

        //   mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        no_wishlist = (TextView) view.findViewById(R.id.no_items);
        mExplore = (Button) view.findViewById(R.id.explore);
        item_count = (TextView) view.findViewById(R.id.item_count);

        //  mToolbarTitle.setVisibility(View.VISIBLE);
        //  mToolbarTitle.setText("FAVOURITES");
        search.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("FAVOURITES");

        mExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabMain_Activity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //  ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
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
     /*   img_back.setOnClickListener(new View.OnClickListener() {
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

            search_word = bundle1.getString("name");
            mToolbarTitle.setText(search_word);

        }
        getWishList();


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


        }

        if (search_home == 1) {
            if (db.getAllLogin().size() == 0) {
                if (search_word.equals("") || search_word.equals("null") || search_word.equals(null)) {

                    getProductList(home_category_selected_name);
                } else {
                    getProductList(search_word);

                }

            } else {
                if (search_word.equals("") || search_word.equals("null") || search_word.equals(null)) {

                    getProductList(category_selected_name);
                } else {
                    getProductList(search_word);

                }


            }
        }*/


        return view;
    }

    public void getWishList() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> params = new HashMap<String, String>();


        JSONObject object = new JSONObject();
        try {

            if (db.getAllLogin().size() == 1) {
                object.put("customer_id", db.getAllLogin().get(0));

            } else if (db.getAllLogin().size() == 0) {
                object.put("customer_id", "0");

            } else if (db.getAllLogin().size() == 2) {
                object.put("customer_id", db.getAllLogin().get(1));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {

            if (db.getAllLogin().size() == 1) {
                object.put("localwish", "null");

            } else if (db.getAllLogin().size() == 0) {
                object.put("localwish", new JSONArray(db.getAllWishlist()));

            } else if (db.getAllLogin().size() == 2) {
                object.put("localwish", new JSONArray(db.getAllWishlist()));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("json", object.toString());

        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.GETWISHLIST, new JSONObject(object.toString()),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.e("get_wishlist", String.valueOf(object));
                                Recipe recipe = null;
                                Browse_Category browse_category = null;
                                receipe_productQtyList = new ArrayList<>();
                                list_browse_products = new ArrayList<>();


                                String status = object.getString("status");
                                if (status.equals("Success")) {
                                    JSONObject JObject = new JSONObject(String.valueOf(object));
                                    int cartcount = JObject.getInt("cartcount");
                                    item_count.setText(String.valueOf(cartcount) + " Items");
                                    if (cartcount == 0) {
                                        products_listview.setVisibility(View.GONE);
                                        no_wishlist.setVisibility(View.VISIBLE);
                                        mExplore.setVisibility(View.VISIBLE);
                                        filter_layout.setVisibility(View.GONE);
                                    } else {
                                        JSONArray DataArray = JObject.getJSONArray("data");
                                        for (int i = 0; i < DataArray.length(); i++) {
                                            Log.e("datacount", String.valueOf(DataArray.length()));
                                            JSONObject jsonObject = DataArray.getJSONObject(i);
                                            product_name = jsonObject.getString("product_name");
                                            product_image = jsonObject.getString("display_image");
                                            product_random_id = jsonObject.getString("product_random_id");
                                            product_price = jsonObject.getString("price");
                                            product_quantity_type = jsonObject.getInt("quantity_type");
                                            is_express_delivery = jsonObject.getString("is_express_delivery");
                                            int id = jsonObject.getInt("id");
                                            browse_category = new Browse_Category();

                                            if (jsonObject.has("category")) {
                                                String nullcheck = jsonObject.getString("category");
                                                if (nullcheck.equals("null")) {

                                                } else {
                                                    int category_id = jsonObject.getInt("category");
                                                    browse_category.setCategoryId(category_id);
                                                }
                                            }

                                            if (jsonObject.has("actual_selling_amount")) {
                                                String nullcheck = jsonObject.getString("actual_selling_amount");
                                                if (nullcheck.equals("null")) {

                                                } else {
                                                    product_selling_price = jsonObject.getString("actual_selling_amount");
                                                    browse_category.setProduct_selling_price(product_selling_price);

                                                }
                                            }

                                            product_discount = jsonObject.getInt("discount");
                                            float rating = jsonObject.getInt("rating_star");
                                            similar_category = jsonObject.getString("category");
                                            similar_subcategory = jsonObject.getString("sub_category");
                                            similar_id = jsonObject.getString("id");


                                            browse_category.setProd_name(product_name);
                                            browse_category.setDukanPrice(product_price);
                                            browse_category.setProduct_image(product_image);
                                            browse_category.setProduct_random_id(product_random_id);
                                            browse_category.setProduct_quantity_type(product_quantity_type);
                                            browse_category.setProduct_id(id);

                                            browse_category.setProduct_discount(product_discount);
                                            browse_category.setRatingStar(rating);
                                            browse_category.setIs_express_delivery(is_express_delivery);
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

                                            JSONArray CartArray = JObject.getJSONArray("cart");
                                            if (CartArray.length() != 0) {
                                                cart_priceList = new ArrayList<>();
                                                for (int j = 0; j < CartArray.length(); j++) {

                                                    Log.e("datacount", String.valueOf(CartArray.length()));
                                                    JSONObject jsonObject2 = CartArray.getJSONObject(j);

                                                    int id1 = jsonObject2.getInt("id");
                                                    browse_category = new Browse_Category();
                                                    browse_category.setData_id(id1);

                                                    cart_priceList.add(browse_category);
                                                }


                                            } else {
                                                Toast.makeText(getActivity(), "No items in WishList", Toast.LENGTH_LONG).show();

                                            }

                                        }

                                    }


                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();

                                }


                                Favorites_Adapter adapter = new Favorites_Adapter(getActivity(), list_browse_products, receipe_productQtyList, cart_priceList);

                                products_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                products_listview.setAdapter(adapter);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    Log.e("verify_otp_error", "error" + volleyError);
                    if (volleyError instanceof TimeoutError) {
                        Toast.makeText(getActivity(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                    VolleyLog.d("responseError", "Error: " + volleyError);

                }
            }) {

              /*  @Override
                public String getBodyContentType() {
                    return "application/json; charset=UTF-8";


                }*/

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        mExplore.setTypeface(mDynoRegular);
        no_wishlist.setTypeface(mDynoRegular);
    }

    public class Favorites_Adapter extends RecyclerView.Adapter<Favorites_Adapter.MyViewHolder> {

        private Context mContext;
        public ArrayList<Browse_Category> browse_categoryArrayList, cartPricelist;
        public ArrayList<Recipe> recipeArrayList;
        private ArrayList<String> arrayList;
        private int check;
        private Typeface mDynoRegular;
        DB db;
        private HashMap<Integer, Integer> selectedItem = new HashMap<Integer, Integer>();
        private HashMap<Integer, String> totalPrices = new HashMap<Integer, String>();
        private String total_amount = null, quanity_with_name, kgs, search_word;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, marketprice, kesoukPrice, no_prod, tagprice, spinner_text;
            public ImageView prod_image, mExpress;
            public Spinner spinner;
            public CardView cardView;
            public ImageView toggleButton;
            Button mAddtoCArt;
            RatingBar ratingBar;

            // public CircleImageView circleImageView;

            public MyViewHolder(View view) {
                super(view);

                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(),
                        "font/Roboto_Regular.ttf");

                title = (TextView) view.findViewById(R.id.tv_prodname);
                tagprice = (TextView) view.findViewById(R.id.tag_price);
                marketprice = (TextView) view.findViewById(R.id.off_amount);
                kesoukPrice = (TextView) view.findViewById(R.id.price);
                mExpress = (ImageView) view.findViewById(R.id.bike);
                prod_image = (ImageView) view.findViewById(R.id.imageView);
                //circleImageView = (CircleImageView) view.findViewById(R.id.circleView);
                toggleButton = (ImageView) view.findViewById(R.id.fav);
                spinner = (Spinner) view.findViewById(R.id.spinner);
                cardView = (CardView) view.findViewById(R.id.card_view);
                mAddtoCArt = (Button) view.findViewById(R.id.add_to_cart);
                ratingBar = (RatingBar) view.findViewById(R.id.ratingbar_Small);

                mAddtoCArt.setVisibility(View.GONE);
                marketprice.setPaintFlags(marketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                db = new DB(mContext);
                title.setTypeface(mDynoRegular);
                marketprice.setTypeface(mDynoRegular);
                kesoukPrice.setTypeface(mDynoRegular);
                mAddtoCArt.setTypeface(mDynoRegular);
                tagprice.setTypeface(mDynoRegular);
            }
        }

        public Favorites_Adapter(Context mContext, ArrayList<Browse_Category> browse_categoryArrayList, ArrayList<Recipe> receipe_productQtyList, ArrayList<Browse_Category> cart_priceList) {
            this.mContext = mContext;
            this.browse_categoryArrayList = browse_categoryArrayList;
            this.recipeArrayList = receipe_productQtyList;
            this.arrayList = quantityList;
            this.cartPricelist = cart_priceList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favorites_listing, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Browse_Category browse_category = browse_categoryArrayList.get(position);
            final Recipe recipe = recipeArrayList.get(position);


            holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));


            holder.ratingBar.setTag("rating:" + position);


            final ArrayAdapter<String> quatity = new ArrayAdapter<String>(mContext, R.layout.spinner_item, recipe.getQuantityListPojo());
            quatity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(quatity);
            if (selectedItem.get(position) != null) {
                //This should call after setAdapter
                holder.spinner.setSelection(selectedItem.get(position));
            }

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long arg3) {
                    // selected item in the list
                    String item = recipe.getQuantityListPojo() + browse_category.getProduct_qty_name();
                    Log.e("item", item);


                    String state = arg0.getItemAtPosition(i).toString() + browse_category.getProduct_qty_name();
                    holder.spinner_text = (TextView) arg1.findViewById(R.id.item);
                    holder.spinner_text.setText(state);

                    // ((TextView)arg1. findViewById(R.id.item)).setText(state);
                    Log.e("position_spinner", recipe.getQuantityListPojo().get(i));
                    Log.e("and", state);
                    kgs = recipe.getQuantityListPojo().get(i);

                    String express = browse_category.getIs_express_delivery();
                    if (express.equals("1")) {
                        holder.mExpress.setVisibility(View.VISIBLE);
                    } else {
                        holder.mExpress.setVisibility(View.GONE);
                    }

                    quanity_with_name = arg0.getItemAtPosition(i).toString() + browse_category.getProduct_qty_name();

                    int prod = browse_category.getProduct_discount();
                    if (prod > 0) {
                        holder.kesoukPrice.setText("SH " + recipe.getActualListPojo().get(i));
                        holder.marketprice.setText("SH " + recipe.getPriceListPojo().get(i));
                        holder.tagprice.setVisibility(View.VISIBLE);

                        holder.tagprice.setText(browse_category.getProduct_discount() + " % off");
                        holder.marketprice.setPaintFlags(holder.marketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                       /* if(browse_category.getIs_express_delivery().equals("null"))
                        {
                            holder.mExpress.setVisibility(View.GONE);
                        }*/


                    } else {
                        holder.marketprice.setVisibility(View.GONE);
                        holder.kesoukPrice.setText("SH " + recipe.getActualListPojo().get(i));
                        // holder.tagprice.setVisibility(View.GONE);
                    }
                    total_amount = "SH " + recipe.getActualListPojo().get(i);


                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }

            });


            holder.title.setText(browse_category.getProd_name());
            // holder.ratingBar.setRating(browse_category.getRatingStar());
            holder.ratingBar.setRating(3);


            // loading album cover using Glide library
            //Glide.with(mContext).load(Constants.PRODUCT_IMAGES + browse_category.getProduct_image()).into(holder.prod_image);
            Glide.with(mContext).load(Constants.PRODUCT_IMAGES + browse_category.getProduct_image()).placeholder(R.drawable.logo).into(holder.prod_image);


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    home_onclick = 2;
                    Product_Description_Fragment newFragment = new Product_Description_Fragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    args.putString("product_random_id", browse_category.getProduct_random_id());
                    args.putString("category", browse_category.getSimilar_category());
                    args.putString("subcategory", browse_category.getSimilar_subcategory());
                    args.putString("id", browse_category.getSimilar_id());
                    args.putString("qty_name", browse_category.getProduct_qty_name());
                    args.putString("price", holder.kesoukPrice.getText().toString());
                    args.putString("qty", holder.spinner_text.getText().toString());
                    args.putString("search_word", search_word);
                    args.putString("product_price_id", recipe.getProduct_priceId());
                    args.putInt("product_id", browse_category.getProduct_id());
                    args.putString("price_product_id", browse_category.getProduct_priceId());
                    int prod = browse_category.getProduct_discount();
                    if (prod > 0) {
                        args.putString("other_price", holder.marketprice.getText().toString());

                    } else {
                        args.putString("other_price", "empty");

                    }
                    args.putString("addtocart", "Add");
                    args.putInt("favorite", 1);

                    Browse_Category browse_category = cartPricelist.get(position);
                    if (db.getAllLogin().size() == 1) {
                        args.putString("wishcheckId", String.valueOf(browse_category.getData_id()));

                    } else if (db.getAllLogin().size() == 0) {
                        args.putString("wishcheckId", String.valueOf(db.getAllWishlist().get(position)));
                    } else if (db.getAllLogin().size() == 2) {
                        args.putString("wishcheckId", String.valueOf(db.getAllWishlist().get(position)));

                    }


                    newFragment.setArguments(args);
                    Log.e("args", args.toString());
                    FragmentTransaction transaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.rldContainer, newFragment);
                    transaction.addToBackStack("Some String");
                    transaction.commit();


                }
            });

            holder.toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    Map<String, String> params = new HashMap<String, String>();
                    Browse_Category browse_category = cartPricelist.get(position);

                    if (db.getAllLogin().size() == 1) {
                        params.put("id", String.valueOf(browse_category.getData_id()));
                        Log.e("id", String.valueOf(browse_category.getData_id()));
                    } else if (db.getAllLogin().size() == 0) {
                        params.put("id", String.valueOf(db.getAllWishlist().get(position)));
                        Log.e("id", String.valueOf(db.getAllWishlist().get(position)));
                    } else if (db.getAllLogin().size() == 2) {
                        params.put("id", String.valueOf(db.getAllWishlist().get(position)));
                        Log.e("id", String.valueOf(db.getAllWishlist().get(position)));

                    }


                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            Constants.REMOVE_WISHLIST, new JSONObject(params),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject object) {
                                    try {
                                        Log.e("remove_wishlist", String.valueOf(object));


                                        String status = object.getString("status");
                                        if (status.equals("Success")) {


                                            if (db.getAllLogin().size() == 1) {

                                            } else if (db.getAllLogin().size() == 0) {
                                                db.remove_wishlist(db.getAllWishlist().get(position));
                                                Log.e("fetch_removed_data", String.valueOf(db.getAllWishlist()));
                                            }


                                            getWishList();
                                        } else {
                                            String reason = object.getString("reason");
                                            Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();

                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Log.e("verify_otp_error", "error" + volleyError);
                            if (volleyError instanceof TimeoutError) {
                                Toast.makeText(getActivity(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getActivity(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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

            });
            holder.mAddtoCArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

            /*    RequestQueue queue = Volley.newRequestQueue(mContext);
                Map<String, String> params = new HashMap<String, String>();

                Log.e("customer_id ","0");
                Log.e("product_id ", String.valueOf((browse_category.getProduct_id())));
                Log.e("quantity ","1");
                Log.e("price_id ", String.valueOf(browse_category.getProduct_priceId()));


                if(db.getAllLogin().size()==1)
                {
                    params.put("customer_id", db.getAllLogin().get(0));

                }
                else if(db.getAllLogin().size()==0)
                {
                    params.put("customer_id", "0");

                }
                params.put("product_id", String.valueOf((browse_category.getProduct_id())));
                params.put("quantity", "1");
                params.put("price_id", String.valueOf(browse_category.getProduct_priceId()));




                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        Constants.ADDTOCART, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject object) {
                                try {
                                    Log.e("addtocart", String.valueOf(object));


                                    String status=object.getString("status");
                                    if(status.equals("Success"))
                                    {
                                        int  data_id = object.getInt("data");
                                        String  data_id1 = object.getString("data");
                                        Log.e("data_id", String.valueOf(data_id));

                                        if(db.getAllLogin().size()==1)
                                        {

                                        }
                                        else if(db.getAllLogin().size()==0)
                                        {
                                            db.insert(data_id1);
                                            Log.e("fetch", String.valueOf(db.getAllData()));
                                        }


                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(mContext);
                                        builder.setTitle("Message");
                                        builder.setMessage(browse_category.getProd_name() +" Added to Cart !");

                                        String positiveText = "Ok";
                                        builder.setPositiveButton(positiveText,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        String negativeText = mContext.getString(android.R.string.cancel);
                                        builder.setNegativeButton(negativeText,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // negative button logic
                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog dialog = builder.create();
                                        // display dialog
                                        dialog.show();
                                    }
                                    else
                                    {
                                        String reason=object.getString("reason");
                                        Toast.makeText(mContext, reason ,Toast.LENGTH_LONG).show();

                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Log.e("verify_otp_error", "error" + volleyError);
                        if (volleyError instanceof TimeoutError) {
                            Toast.makeText(mContext, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(mContext, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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

*/
                }
            });


        }

        private void setRatingStarColor(Drawable drawable, @ColorInt int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                DrawableCompat.setTint(drawable, color);
            } else {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }

        private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final MyViewHolder holder, final int position) {
            return new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                    // Filled stars
                    setRatingStarColor(stars.getDrawable(2), ContextCompat.getColor(getActivity(), R.color.rating_star));
                    // Half filled stars
                    setRatingStarColor(stars.getDrawable(1), ContextCompat.getColor(getActivity(), R.color.rating_star));
                    // Empty stars
                    setRatingStarColor(stars.getDrawable(0), ContextCompat.getColor(getActivity(), R.color.tab_line));


                    try {
                        Drawable progressDrawable = ratingBar.getProgressDrawable();
                        if (progressDrawable != null) {
                            DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getActivity(), R.color.rating_star));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
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


/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

      */
/*  getMenuInflater().inflate(R.menu.item, menu);
        return true;*//*

        super.onCreateOptionsMenu(menu, inflater);
        if(db.getAllLogin().size()==0) {
            mToolbar.inflateMenu(R.menu.item);
        }
        else
        {
            mToolbar.inflateMenu(R.menu.cust_item);
        }

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
        if(db.getAllLogin().size()==1)
        {
            switch (item.getItemId()) {
                case R.id.my_acc:
                    Intent my_acc = new Intent(getActivity(), Profile_Activity.class);
                    startActivity(my_acc);
                    break;
                case R.id.my_orders:
                    Intent my_addr = new Intent(getActivity(), MyAddress_Activity.class);
                    startActivity(my_addr);
                    break;
                case R.id.contact:

                    break;
                case R.id.about:

                    break;
                case R.id.terms:

                    break;
                case R.id.change_pass:


                    break;
                case R.id.logout:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("Are you sure you want to Logout?");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    db.removeLogin();
                                    fav_logout_backpress=1;
                                    Intent intent2 = new Intent(getActivity(), Login_Activity.class);
                                    intent2.addCategory(Intent.CATEGORY_HOME);
                                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent2);
                                    getActivity().finish();

                                }
                            });
                    alertDialogBuilder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();


                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));
                    ;

                    break;

                default:
                    break;
        }


        }
        else
        {
            switch (item.getItemId()) {
                case R.id.my_acc:
               */
/* Intent my_acc = new Intent(getActivity(), Profile_Activity.class);
                startActivity(my_acc);*//*

                    break;
                case R.id.my_orders:
               */
/* Intent my_addr = new Intent(getActivity(), MyAddress_Activity.class);
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

                    fav_home_signin_backpress=1;
                    Intent intent2 = new Intent(getActivity(), Login_Activity.class);
                    startActivity(intent2);
                    break;

                default:
                    break;
            }

            return true;

        }

        return true;
    }
*/

}
