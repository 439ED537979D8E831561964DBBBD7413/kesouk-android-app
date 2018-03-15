package com.tstl.kesouk.Fragments;

import android.content.Context;
import android.graphics.Paint;
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
import android.widget.Button;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.tstl.kesouk.Activity.CheckoutScreen1;
import com.tstl.kesouk.Activity.Login_Activity;
import com.tstl.kesouk.Activity.Login_Register_Activity;
import com.tstl.kesouk.Activity.MyAddress_Activity;
import com.tstl.kesouk.Activity.Navigation_Tab_Activity;
import com.tstl.kesouk.Activity.Profile_Activity;
import com.tstl.kesouk.Activity.TabMain_Activity;
import com.tstl.kesouk.Adapter.ExpandableListAdapter1;
import com.tstl.kesouk.Adapter.Recipes_Adapter;
import com.tstl.kesouk.Adapter.SectionListDataAdapter;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.Cart;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;

public class Basket_Fragment extends Fragment {
    private Typeface mDynoRegular;
    String name_category, image_url_category, transaction_with, section_name;
    public String caption_letter;
    ExpandableListAdapter1 mMenuAdapter;
    ExpandableListView expandableList;
    DrawerLayout drawer;
    ArrayList<Products> listHeader = new ArrayList<>();
    ArrayList<SectionDataModel> section_name_list = new ArrayList<>();
    ArrayList<SingleItemModel> singleItem = null;
    private TextView mToolbarTitle, item_count,noItemsbasket;
    RecyclerView my_recycler_view;
    RecyclerView recyler_main;
    int cart_id1, quantity;
    public static int cart_backpress = 0;

    ArrayList<SectionDataModel> allSampleData;
    DB db;
    Button mCheckout, noCart;
    View view;
    RelativeLayout items_layout;
    public ArrayList<String> categoryList = new ArrayList<>();
    public ArrayList<Float> subTotalList = new ArrayList<>();
    public ArrayList<String> db_dataid = new ArrayList<>();
    public ArrayList<Integer> categoryListId = new ArrayList<>();
    public ArrayList<Integer> cartIdList = new ArrayList<>();
    public ArrayList<Integer> categoryListString = new ArrayList<>();
    public ArrayList<String> categoryListProductName = new ArrayList<>();
    public static ArrayList<Cart> cartArrayList = null;
    public static ArrayList<Browse_Category> cart_priceList = null;
    String product_name, product_image, product_price_amount, product_selling_price, product_price_id, discount, category, priceKesouk, priceSelling;
    int price, cart_quantity, category_int, productIdPrice, priceQuantity;
    JSONObject jsonObject2;
    float a = 0; int cartcount;
    public static int basket_logout_backpress=0,basket_home_signin_backpress=0;



    public static Basket_Fragment newInstance() {
        Basket_Fragment fragment = new Basket_Fragment();
        return fragment;
    }
    public Basket_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.basket_main, container, false);
        //  img_back = (ImageView) view.findViewById(R.id.img);
        //  settings = (ImageView) view.findViewById(R.id.settings);
        //  search = (ImageView) view.findViewById(R.id.search);
        items_layout = (RelativeLayout) view.findViewById(R.id.filter_layout);
        noCart = (Button) view.findViewById(R.id.no_cart);

        recyler_main = (RecyclerView) view.findViewById(R.id.recycler_main);
        mCheckout = (Button) view.findViewById(R.id.checkout);
        // settings.setVisibility(View.VISIBLE);
        // search.setVisibility(View.GONE);
        // img_back.setVisibility(View.GONE);
        db = new DB(getActivity());
        mCheckout.setVisibility(View.GONE);

        // mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        item_count = (TextView) view.findViewById(R.id.item_count);
        noItemsbasket = (TextView) view.findViewById(R.id.no_items_basket);

        // mToolbarTitle.setVisibility(View.VISIBLE);
        // mToolbarTitle.setText("REVIEW ORDER");
        search.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("REVIEW ORDER");

        getCart();


        // ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        //   mToolbar = (Toolbar) view.findViewById(R.id.logintoolbar);


        setFont();

       /* img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }

            }
        });*/

        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getAllLogin().size() == 1) {
                      Intent intent=new Intent(getActivity(), CheckoutScreen1.class);
                      getActivity().startActivity(intent);
                } else if (db.getAllLogin().size() == 0) {
                    cart_backpress = 1;
                    Intent intent = new Intent(getActivity(), Login_Activity.class);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }


            }
        });

        noCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabMain_Activity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto_Regular.ttf");
        // mToolbarTitle.setTypeface(mDynoRegular);
        item_count.setTypeface(mDynoRegular);
    }


    public void getCart() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> params = new HashMap<String, String>();


        JSONObject object = new JSONObject();
        try {


            if (db.getAllLogin().size() == 1) {
                object.put("customer_id", db.getAllLogin().get(0));

            } else if (db.getAllLogin().size() == 0) {
                object.put("customer_id", "0");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (db.getAllLogin().size() == 1) {
                object.put("localcart", "null");
            } else if (db.getAllLogin().size() == 0) {
                object.put("localcart", new JSONArray(db.getAllData()));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("json", object.toString());

        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.GET_CART, new JSONObject(object.toString()),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.e("addtocart", String.valueOf(object));
                                Cart cart = null;

                                String status = object.getString("status");
                                if (status.equals("Success")) {
                                    JSONObject JObject = new JSONObject(String.valueOf(object));
                                    int cartcount = JObject.getInt("cartcount");
                                    item_count.setText(cartcount + " Items - SH 40");
                                    if (cartcount == 0) {
                                        recyler_main.setVisibility(View.GONE);
                                        mCheckout.setVisibility(View.GONE);
                                        items_layout.setVisibility(View.GONE);
                                        noCart.setVisibility(View.VISIBLE);
                                        noItemsbasket.setVisibility(View.VISIBLE);

                                    } else {
                                        JSONArray DataArray = JObject.getJSONArray("data");
                                        Log.e("data", "data");
                                        SectionDataModel dm = null;
                                        ArrayList<SingleItemModel> singleItemList = null;
                                        SingleItemModel singleItemModel = null;
                                        if (DataArray.length() != 0) {
                                            allSampleData = new ArrayList<SectionDataModel>();
                                            singleItem = new ArrayList<SingleItemModel>();


                                            cartArrayList = new ArrayList<>();
                                            for (int i = 0; i < DataArray.length(); i++) {
                                                dm = new SectionDataModel();
                                                singleItemModel = new SingleItemModel();
                                                Log.e("datacount", String.valueOf(DataArray.length()));
                                                JSONObject jsonObject = DataArray.getJSONObject(i);
                                                product_name = jsonObject.getString("product_name");
                                                product_image = jsonObject.getString("display_image");
                                                discount = jsonObject.getString("discount");
                                                int discount1 = jsonObject.getInt("discount");
                                                category = jsonObject.getString("category");
                                                category_int = jsonObject.getInt("category");
                                                categoryListString.add(category_int);
                                                categoryListId.add(category_int);
                                                int id = jsonObject.getInt("id");
                                                singleItemModel.setId(id);
                                                singleItemModel.setName(product_name);


                                                cart = new Cart();
                                                cart.setProduct_name(product_name);

                                                cart.setImage_url(product_image);
                                                cart.setDiscount(discount);

                                                JSONObject product_category = jsonObject.getJSONObject("product_category");
                                                section_name = product_category.getString("name");
                                                categoryList.add(section_name);


                                                dm.setHeaderTitle(section_name);


                                                JSONArray product_price = jsonObject.getJSONArray("product_price");
                                                //list_browse_products.add(id);
                                                if (product_price.length() != 0) {
                                                    for (int j = 0; j < product_price.length(); j++) {
                                                        Log.e("datacount", String.valueOf(product_price.length()));
                                                        JSONObject jsonObject1 = product_price.getJSONObject(j);
                                                        product_price_amount = jsonObject1.getString("price");
                                                        product_selling_price = jsonObject1.getString("actual_selling_amount");
                                                        price = jsonObject1.getInt("actual_selling_amount");


                                                    }
                                                    cart.setDukanPrice(product_selling_price);
                                                    cart.setActual_amount(price);
                                                    cart.setMarketPrice(product_price_amount);
                                                }


                                                Log.e("product_name", cart.getProduct_name());
                                                Log.e("product_image", cart.getImage_url());
                                                Log.e("product_price", cart.getDukanPrice());
                                                cartArrayList.add(cart);

                                            }


                                            Set<String> set = new LinkedHashSet<>(categoryList);
                                            set.addAll(categoryList);
                                            categoryList.clear();
                                            categoryList.addAll(set);
                                            Log.e("categylist", categoryList.toString());


                                            Set<Integer> set1 = new LinkedHashSet<>(categoryListString);
                                            set1.addAll(categoryListString);
                                            categoryListString.clear();
                                            categoryListString.addAll(set1);


/*

                                            Set<String> set2 = new LinkedHashSet<>(db.getAllData());
                                            set2.addAll(db_dataid);
                                            db_dataid.clear();
                                            db_dataid.addAll(set2);

*/


                                            ArrayList<Integer> arrayList = new ArrayList<Integer>(new LinkedHashSet<Integer>(categoryListString));
                                            Log.e("arralist", String.valueOf(arrayList));

                                            // Collections.sort(categoryListString);

                                            Log.e("categylist", categoryListString.toString());

                                        } else {
                                            Toast.makeText(getActivity(), "No items in your cart", Toast.LENGTH_LONG).show();
                                        }

                                        Browse_Category browse_category = null;
                                        JSONArray CartArray = JObject.getJSONArray("cart");
                                        if (CartArray.length() != 0) {
                                            cart_priceList = new ArrayList<>();
                                            for (int i = 0; i < CartArray.length(); i++) {

                                                Log.e("datacount", String.valueOf(CartArray.length()));
                                                JSONObject jsonObject = CartArray.getJSONObject(i);
                                                product_price_id = jsonObject.getString("price_id");
                                                cart_quantity = jsonObject.getInt("quantity");
                                                int id = jsonObject.getInt("id");
                                                browse_category = new Browse_Category();
                                                browse_category.setCart_price_id(product_price_id);
                                                browse_category.setCart_quantity(cart_quantity);
                                                browse_category.setData_id(id);


                                                Log.e("product_price_cart", browse_category.getCart_price_id());
                                                Log.e("cart_quantity", String.valueOf(browse_category.getCart_quantity()));
                                                cart_priceList.add(browse_category);
                                            }
                                           /* double subTotal = 0;
                                            for(Browse_Category p : cart_priceList) {
                                                int quantity = browse_category.getCart_quantity(p);
                                                subTotal += p.getDukanPrice() * quantity;
                                            }*/

                                            //grossAmount.setText("Subtotal: $" + subTotal);


                                        } else {
                                            Toast.makeText(getActivity(), "No items in your cart", Toast.LENGTH_LONG).show();

                                        }


                                    }


                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();

                                }
                                getCart1();

                               /* Cart_Adapter adapter = new Cart_Adapter(getActivity(), cartArrayList,cart_priceList);

                                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                recycler_view.setAdapter(adapter);*/

                                Log.e("list", String.valueOf(allSampleData));

                                /*RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData,categoryList);

                                recyler_main.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyler_main.setNestedScrollingEnabled(false);
                                recyler_main.setAdapter(adapter);
*/
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


    public void getCart1() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Map<String, String> params = new HashMap<String, String>();


        JSONObject object = new JSONObject();
        try {


            if (db.getAllLogin().size() == 1) {
                object.put("customer_id", db.getAllLogin().get(0));

            } else if (db.getAllLogin().size() == 0) {
                object.put("customer_id", "0");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (db.getAllLogin().size() == 1) {
                object.put("localcart", "null");
            } else if (db.getAllLogin().size() == 0) {
                object.put("localcart", new JSONArray(db.getAllData()));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("json", object.toString());

        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.GET_CART, new JSONObject(object.toString()),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.e("addtocart", String.valueOf(object));
                                Cart cart = null;

                                String status = object.getString("status");
                                if (status.equals("Success")) {
                                    JSONObject JObject = new JSONObject(String.valueOf(object));
                                    cartcount = JObject.getInt("cartcount");

                                    if (cartcount == 0) {
                                        recyler_main.setVisibility(View.GONE);
                                        mCheckout.setVisibility(View.GONE);
                                        items_layout.setVisibility(View.GONE);
                                        noCart.setVisibility(View.VISIBLE);
                                    } else {
                                        mCheckout.setVisibility(View.VISIBLE);
                                        JSONArray DataArray = JObject.getJSONArray("data");
                                        Log.e("data", "data");
                                        SectionDataModel dm = null;
                                        ArrayList<SingleItemModel> singleItemList = null;
                                        SingleItemModel singleItemModel = null;
                                        if (DataArray.length() != 0) {
                                            allSampleData = new ArrayList<SectionDataModel>();
                                            singleItem = new ArrayList<SingleItemModel>();


                                            cartArrayList = new ArrayList<>();
                                            cartIdList = new ArrayList<>();
                                            subTotalList = new ArrayList<>();
                                         /*   for (int i = 0; i < 2; i++) {
                                                dm = new SectionDataModel();
                                                singleItemModel = new SingleItemModel();
                                                Log.e("datacount", String.valueOf(DataArray.length()));
                                                JSONObject jsonObject = DataArray.getJSONObject(i);
                                                product_name = jsonObject.getString("product_name");
                                                product_image = jsonObject.getString("display_image");
                                                discount = jsonObject.getString("discount");
                                                int discount1 = jsonObject.getInt("discount");
                                                category = jsonObject.getString("category");
                                                int id = jsonObject.getInt("id");
                                                singleItemModel.setId(id);
                                                singleItemModel.setName(product_name);*/

/*


                                                cart = new Cart();
                                                cart.setProduct_name(product_name);

                                                cart.setImage_url(product_image);
                                                cart.setDiscount(discount);

                                                JSONObject product_category=jsonObject.getJSONObject("product_category");
                                                section_name = product_category.getString("name");
                                                categoryList.add(section_name);


                                                dm.setHeaderTitle(section_name);



                                                JSONArray product_price = jsonObject.getJSONArray("product_price");
                                                //list_browse_products.add(id);
                                                if (product_price.length() != 0)
                                                {
                                                    for(int j=0;j<product_price.length();j++)
                                                    {
                                                        Log.e("datacount", String.valueOf(product_price.length()));
                                                        JSONObject jsonObject1 = product_price.getJSONObject(j);
                                                        product_price_amount = jsonObject1.getString("price");
                                                        product_selling_price = jsonObject1.getString("actual_selling_amount");
                                                        price = jsonObject1.getInt("actual_selling_amount");


                                                    }
                                                    cart.setDukanPrice(product_selling_price);
                                                    cart.setActual_amount(price);
                                                    cart.setMarketPrice(product_price_amount);
                                                }



                                                Log.e("product_name", cart.getProduct_name());
                                                Log.e("product_image", cart.getImage_url());
                                                Log.e("product_price", cart.getDukanPrice());
                                                cartArrayList.add(cart);
*/
                                            //   }


                                            JSONArray DataArray1 = JObject.getJSONArray("data");
                                            Log.e("catlsitid", categoryListString.toString());


                                            for (int k = 0; k < categoryListString.size(); k++) {
                                                dm = new SectionDataModel();
                                                singleItem = new ArrayList<SingleItemModel>();

                                                for (int j = 0; j < DataArray1.length(); j++) {


                                                    singleItemModel = new SingleItemModel();
                                                    JSONObject jsonObject = DataArray.getJSONObject(j);
                                                    category = jsonObject.getString("category");
                                                    product_name = jsonObject.getString("product_name");
                                                    product_image = jsonObject.getString("display_image");
                                                    discount = jsonObject.getString("discount");
                                                    int discount1 = jsonObject.getInt("discount");
                                                    category_int = jsonObject.getInt("category");
                                                    category = jsonObject.getString("category");
                                                    int id = jsonObject.getInt("id");


                                                    JSONArray product_price = jsonObject.getJSONArray("product_price");
                                                    //list_browse_products.add(id);
                                                    if (product_price.length() != 0) {
                                                        for (int p = 0; p < product_price.length(); p++) {
                                                            Log.e("datacount", String.valueOf(product_price.length()));
                                                            jsonObject2 = product_price.getJSONObject(p);
                                                            product_price_amount = jsonObject2.getString("price");
                                                            product_selling_price = jsonObject2.getString("actual_selling_amount");
                                                            price = jsonObject2.getInt("actual_selling_amount");
                                                            productIdPrice = jsonObject2.getInt("product_id");
                                                            priceQuantity = jsonObject2.getInt("quantity");


                                                        }
                                                        // cart.setDukanPrice(product_selling_price);
                                                        // cart.setActual_amount(price);
                                                        // cart.setMarketPrice(product_price_amount);
                                                    }


                                                    if (category_int == (categoryListString.get(k))) {
                                                        product_name = jsonObject.getString("product_name");
                                                        categoryListProductName.add(product_name);


                                                        Browse_Category browse_category = null;
                                                        JSONArray CartArray = JObject.getJSONArray("cart");
                                                        if (CartArray.length() != 0) {
                                                            cart_priceList = new ArrayList<>();
                                                            for (int i = 0; i < CartArray.length(); i++) {

                                                                Log.e("datacount", String.valueOf(CartArray.length()));
                                                                JSONObject jsonObject1 = CartArray.getJSONObject(i);
                                                                product_price_id = jsonObject1.getString("price_id");
                                                                cart_quantity = jsonObject1.getInt("quantity");
                                                                int product_id = jsonObject1.getInt("product_id");
                                                                int cart_id = jsonObject1.getInt("id");
                                                                browse_category = new Browse_Category();
                                                                browse_category.setCart_price_id(product_price_id);
                                                                browse_category.setCart_quantity(cart_quantity);
                                                                browse_category.setData_id(cart_id);

                                                                if (id == product_id) {
                                                                    cart_id1 = jsonObject1.getInt("id");
                                                                    quantity = jsonObject1.getInt("quantity");
                                                                    cartIdList.add(cart_id1);
                                                                    Log.e("cartIdList", cartIdList.toString());

                                                                    if (product_id == productIdPrice && priceQuantity == 1) {
                                                                        priceKesouk = jsonObject2.getString("actual_selling_amount");
                                                                        priceSelling = jsonObject2.getString("price");
                                                                        Log.e("product_price_cart", priceKesouk);
                                                                        Log.e("priceSelling", priceSelling);


                                                                        String dukan_price = priceKesouk;
                                                                        float price = Float.parseFloat(dukan_price);
                                                                        float Dukanprice = price * quantity;
                                                                        Log.e("subtotal", String.valueOf(Dukanprice));
                                                                        subTotalList.add(Dukanprice);

                                                                        Log.e("subtotal", subTotalList.toString());


                                                                    }
                                                                }


                                                                Log.e("cart_quantity", String.valueOf(browse_category.getCart_quantity()));
                                                                cart_priceList.add(browse_category);
                                                            }
                                           /* double subTotal = 0;
                                            for(Browse_Category p : cart_priceList) {
                                                int quantity = browse_category.getCart_quantity(p);
                                                subTotal += p.getDukanPrice() * quantity;
                                            }*/

                                                            //grossAmount.setText("Subtotal: $" + subTotal);


                                                        } else {
                                                            Toast.makeText(getActivity(), "No items in your cart", Toast.LENGTH_LONG).show();

                                                        }

                                                        singleItem.add(new SingleItemModel(product_name, product_image, discount1, null, cart_id1, quantity, priceKesouk, priceSelling, cartIdList));

                                                    }
                                                }


                                                dm.setAllItemsInSection(singleItem);
                                                allSampleData.add(dm);


                                            }

                                            Log.e("cagyprname", categoryListProductName.toString());
                                            Log.e("cartIdList", cartIdList.toString());

                                        } else {
                                            Toast.makeText(getActivity(), "No items in your cart", Toast.LENGTH_LONG).show();
                                        }


                                    }


                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();

                                }

                               /* Cart_Adapter adapter = new Cart_Adapter(getActivity(), cartArrayList,cart_priceList);

                                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                                recycler_view.setAdapter(adapter);*/

                                Log.e("list", String.valueOf(allSampleData));
                                a=0;
                                for (int i = 0; i < subTotalList.size(); i++) {

                                    a = a + subTotalList.get(i);

                                }
                                Log.e("subtotal", String.valueOf(a));
                                item_count.setText(cartcount + " Items - SH "+a);


                                RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData, categoryList);

                                recyler_main.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                recyler_main.setNestedScrollingEnabled(false);
                                recyler_main.setAdapter(adapter);

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


    public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

        private ArrayList<SectionDataModel> dataList;
        private Context mContext;
        private Typeface mDynoRegular;
        ArrayList<String> categoryList;

        public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList, ArrayList<String> categoryList) {
            this.dataList = dataList;
            this.mContext = context;
            this.categoryList = categoryList;
        }

        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basket_main_item, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(final ItemRowHolder itemRowHolder, int i) {

            final String sectionName = categoryList.get(i);

            final ArrayList<SingleItemModel> singleSectionItems = dataList.get(i).getAllItemsInSection();

            itemRowHolder.itemTitle.setText(sectionName);

            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, "new", singleSectionItems);

            itemRowHolder.recycler_view_list.setHasFixedSize(true);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setStackFromEnd(true);
            itemRowHolder.recycler_view_list.setLayoutManager(linearLayoutManager);
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        }

        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView itemTitle;

            protected RecyclerView recycler_view_list;


            public ItemRowHolder(View view) {
                super(view);

                this.itemTitle = (TextView) view.findViewById(R.id.section_name);
                this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto_Regular.ttf");
                itemTitle.setTypeface(mDynoRegular);


            }
        }

    }


    public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

        private ArrayList<SingleItemModel> itemsList;
        private Context mContext;
        private String transaction;
        int quan_minus;
        public ArrayList<Float> subtotal_list = new ArrayList<>();


        public SectionListDataAdapter(Context context, String transaction, ArrayList<SingleItemModel> itemsList) {
            this.itemsList = itemsList;
            this.mContext = context;
            this.transaction = transaction;
        }

        @Override
        public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basket_list_item, null);
            SingleItemRowHolder mh = new SingleItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(final SingleItemRowHolder holder, final int position) {

            final SingleItemModel singleItem = itemsList.get(position);

            holder.tvTitle.setText(singleItem.getName());
            holder.tag_price.setVisibility(View.VISIBLE);
            holder.tag_price.setText(String.valueOf(singleItem.getId() + " % off"));

            Glide.with(mContext)
                    .load(Constants.PRODUCT_IMAGES + singleItem.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.logo)
                    .into(holder.itemImage);

            String dukan_price = singleItem.getKesoukPrice();
            float price = Float.parseFloat(dukan_price);
            float Dukanprice = price * singleItem.getQuantity();
            holder.kesoukPrice.setText("SH " + String.valueOf(Dukanprice));

            String off_price = singleItem.getSellingPrice();
            float price1 = Float.parseFloat(off_price);
            float Offer_price = price1 * singleItem.getQuantity();
            holder.otherMarketprice.setText("SH " + String.valueOf(Offer_price));

            holder.otherMarketprice.setPaintFlags(holder.otherMarketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.otherMarketprice.setVisibility(View.VISIBLE);


            holder.quantity_count.setText(String.valueOf(singleItem.getQuantity()));


            holder.mPlusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Cart cart = cartArrayList.get(position);
                   /* Browse_Category browse_category = cart_priceList.get(position);

                    float subtotal=0;
                    for (Browse_Category category : cart_priceList)
                    {

                        int quantity = category.getCart_quantity();
                        subtotal += cart.getActual_amount() * quantity;

                    }
*/


                    int quantity = singleItem.getQuantity();
                    holder.quantity_count.setText(String.valueOf(quantity));
                    Log.e("quantitycount", String.valueOf(quantity));


                    if (quantity <= 1) {
                        //  browse_category.setQuantitycart_text(2);


                        int quan = 2;
                        holder.quantity_count.setText(String.valueOf(quan));

                        String kesoukPrice = singleItem.getKesoukPrice();
                        float price = Float.parseFloat(kesoukPrice);
                        float kesoukPrice1 = price * quan;
                        holder.kesoukPrice.setText("SH " + String.valueOf(kesoukPrice1));
                        subtotal_list.add(kesoukPrice1);

                        String off_price = singleItem.getSellingPrice();
                        float price1 = Float.parseFloat(off_price);
                        float Offer_price = price1 * quan;
                        holder.otherMarketprice.setText("SH " + String.valueOf(Offer_price));

                        holder.otherMarketprice.setPaintFlags(holder.otherMarketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        //singleItem[0] = itemsList.set(position, singleItem[0]);
                        updateList1();

                        RequestQueue queue = Volley.newRequestQueue(mContext);
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("id", String.valueOf(singleItem.getCatgOrProd()));
                        params.put("quantity", String.valueOf(quan));
                        Log.e("id", String.valueOf(singleItem.getCatgOrProd()));
                        Log.e("quantity", String.valueOf(quan));

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                Constants.CART_QUANTITY, new JSONObject(params),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject object) {
                                        try {
                                            Log.e("change_cart", String.valueOf(object));


                                            String status = object.getString("status");
                                            if (status.equals("Success")) {
                                                // getUpdatedCart();
                                                getCart();
                                            } else {
                                                String reason = object.getString("reason");
                                                Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                                } else
                                    Toast.makeText(mContext.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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


                    } else {
                        holder.mMinusImage.setVisibility(View.VISIBLE);
                        int quan = quantity + 1;
                        // browse_category.setQuantitycart_text(quan);
                        holder.quantity_count.setText(String.valueOf(quan));
                        Log.e("quantitycount", String.valueOf(quan));


                        String kesoukPrice = singleItem.getKesoukPrice();
                        float price = Float.parseFloat(kesoukPrice);
                        float Dukanprice = price * quan;
                        holder.kesoukPrice.setText("SH " + String.valueOf(Dukanprice));
                        Log.e("kesoukprice", "SH " + String.valueOf(Dukanprice));
                        subtotal_list.add(Dukanprice);

                        String off_price = singleItem.getSellingPrice();
                        float price1 = Float.parseFloat(off_price);
                        float Offer_price = price1 * quan;
                        holder.otherMarketprice.setText("SH " + String.valueOf(Offer_price));

                        holder.otherMarketprice.setPaintFlags(holder.otherMarketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        // singleItem = itemsList.set(position, singleItem);
                        updateList1();

                        RequestQueue queue = Volley.newRequestQueue(mContext);
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("id", String.valueOf(singleItem.getCatgOrProd()));
                        params.put("quantity", String.valueOf(quan));
                        Log.e("id", String.valueOf(singleItem.getCatgOrProd()));
                        Log.e("quantity", String.valueOf(quan));

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                Constants.CART_QUANTITY, new JSONObject(params),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject object) {
                                        try {
                                            Log.e("change_cart", String.valueOf(object));


                                            String status = object.getString("status");
                                            if (status.equals("Success")) {
                                                //getUpdatedCart();
                                                getCart();
                                            } else {
                                                String reason = object.getString("reason");
                                                Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                                } else
                                    Toast.makeText(mContext.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
                    Log.e("plus", "clicked");


                }
            });


            holder.mMinusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (quantity <= 1) {

                    } else {

                        //  Browse_Category browse_category = cart_priceList.get(position);
                        // Cart cart = cartArrayList.get(position);
/*
                        float subtotal=0;
                        for (Browse_Category category : cart_priceList)
                        {

                            int quantity = category.getCart_quantity();
                            subtotal += cart.getActual_amount() * quantity;

                        }*/


                        Log.e("minus", "clicked");


                        int quantity = singleItem.getQuantity();
                        holder.quantity_count.setText(String.valueOf(singleItem.getQuantity()));

                        if (quantity <= 1) {
                            holder.mMinusImage.setVisibility(View.VISIBLE);
                        } else {
                            holder.mMinusImage.setVisibility(View.VISIBLE);
                            quan_minus = quantity - 1;
                            // browse_category.setQuantitycart_text(quan);
                            holder.quantity_count.setText(String.valueOf(quan_minus));

                            String dukan_price = singleItem.getKesoukPrice();
                            float price = Float.parseFloat(dukan_price);
                            float Dukanprice = price * quan_minus;
                            holder.kesoukPrice.setText("$ " + String.valueOf(Dukanprice));
                            subtotal_list.add(Dukanprice);

                            String off_price = singleItem.getSellingPrice();
                            float price1 = Float.parseFloat(off_price);
                            float Offer_price = price1 * quan_minus;
                            holder.otherMarketprice.setText("$ " + String.valueOf(Offer_price));

                            holder.otherMarketprice.setPaintFlags(holder.otherMarketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }


                        RequestQueue queue = Volley.newRequestQueue(mContext);
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("id", String.valueOf(singleItem.getCatgOrProd()));
                        params.put("quantity", String.valueOf(quan_minus));
                        Log.e("id", String.valueOf(singleItem.getCatgOrProd()));
                        Log.e("quantity", String.valueOf(quan_minus));

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                Constants.CART_QUANTITY, new JSONObject(params),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject object) {
                                        try {
                                            Log.e("minus_cart", String.valueOf(object));


                                            String status = object.getString("status");
                                            if (status.equals("Success")) {
                                                //getUpdatedCart();
                                                getCart();
                                            } else {
                                                String reason = object.getString("reason");
                                                Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                                } else
                                    Toast.makeText(mContext.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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

                }
            });


            holder.mRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("cagrprod", String.valueOf(singleItem.getCatgOrProd()));
                    ArrayList<String> db_data = db.getAllData();

                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    Map<String, String> params = new HashMap<String, String>();
                    if (db.getAllLogin().size() == 1) {
                        params.put("id", String.valueOf(singleItem.getCatgOrProd()));
                        Log.e("id", String.valueOf(singleItem.getCatgOrProd()));
                    } else if (db.getAllLogin().size() == 0) {
                        params.put("id", String.valueOf(singleItem.getCatgOrProd()));
                        Log.e("id", String.valueOf(singleItem.getCatgOrProd()));
                    }


                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            Constants.REMOVE_CART, new JSONObject(params),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject object) {
                                    try {
                                        Log.e("remove_cart", String.valueOf(object));


                                        String status = object.getString("status");
                                        if (status.equals("Success")) {


                                            if (db.getAllLogin().size() == 1) {

                                            } else if (db.getAllLogin().size() == 0) {
                                                db.remove(String.valueOf(singleItem.getCatgOrProd()));
                                                /*db.remove(db.getAllData().get(position));
                                                Log.e("fetch_removed_data", String.valueOf(db.getAllData()));*/
                                                Log.e("fetch_removed_data", String.valueOf(singleItem.getCatgOrProd()));
                                            }

                                            Toast.makeText(mContext, "Item removed successfully !", Toast.LENGTH_LONG).show();

                                            //getUpdatedCart();
                                            getCart();

                                        } else {
                                            String reason = object.getString("reason");
                                            Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                            } else
                                Toast.makeText(mContext.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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


        }

        @Override
        public int getItemCount() {
            return (null != itemsList ? itemsList.size() : 0);
        }

        public class SingleItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView tvTitle, tag_price, otherMarketprice, kesoukPrice, quantity_count;

            protected ImageView itemImage, mRemove, mPlusImage, mMinusImage;
            private Typeface mDynoRegular;


            public SingleItemRowHolder(View view) {
                super(view);

                this.tvTitle = (TextView) view.findViewById(R.id.tv_prodname);
                this.quantity_count = (TextView) view.findViewById(R.id.count_cart);
                this.itemImage = (ImageView) view.findViewById(R.id.imageView);
                this.mPlusImage = (ImageView) view.findViewById(R.id.plusimg);
                this.mMinusImage = (ImageView) view.findViewById(R.id.minusimg);
                this.mRemove = (ImageView) view.findViewById(R.id.remove);
                this.tag_price = (TextView) view.findViewById(R.id.tag_price);
                this.otherMarketprice = (TextView) view.findViewById(R.id.off_amount);
                this.kesoukPrice = (TextView) view.findViewById(R.id.price);

                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto_Regular.ttf");
                tvTitle.setTypeface(mDynoRegular);
                tag_price.setTypeface(mDynoRegular);
                otherMarketprice.setTypeface(mDynoRegular);
                kesoukPrice.setTypeface(mDynoRegular);
                quantity_count.setTypeface(mDynoRegular);

            }

        }

        private void updateList1() {
            notifyDataSetChanged();
        }
    }




}

