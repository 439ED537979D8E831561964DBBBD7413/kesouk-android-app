package com.tstl.kesouk.Activity;

/**
 * Created by user on 21-Feb-18.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Login_Credentials;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Activity.CheckoutScreen1.CheckoutAddress;


/**
 * Created by user on 30-Nov-17.
 */

public class MyAddress_Activity extends AppCompatActivity {
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    RecyclerView recycler_view;
    DB db;
    String firstName = "", lastname = "", email = "", countrycode = "", mobilnumber = "", gender = "", age = "", country = "", city = "", district = "", location_text = "", profile_image = "",house_no="",area="";
    String first_name, last_name;
    String id_add;
    public static int edit_img_click = 0;
    public static int add_addr_click = 0;
    private ImageView backBtn,settings,search;
    public static String delvy_pickup;
    int status_code,default_address,address_position;
    public ArrayList<Login_Credentials> login_credentials = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_address_activity);
        db = new DB(this);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        // recycler_view.setNestedScrollingEnabled(false);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);


        setSupportActionBar(mToolbar);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("CHOOSE ADDRESS");
        mToolbarTitle.setVisibility(View.VISIBLE);
        backBtn = (ImageView) findViewById(R.id.img);


        settings = (ImageView) findViewById(R.id.settings);
        search = (ImageView) findViewById(R.id.search);
        search.setVisibility(View.GONE);

        setFont();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Extract the data…
            delvy_pickup = bundle.getString("assignOrdelivery");
            status_code = bundle.getInt("status");
        }
        getUserAddress();


    }

    private void getUserAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(MyAddress_Activity.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        RequestQueue queue = Volley.newRequestQueue(MyAddress_Activity.this);
        Log.e("url",Constants.GET_USER_ADDRESS+db.getAllLogin().get(0));
        StringRequest jsObjRequest = new StringRequest(
                Request.Method.GET, Constants.GET_USER_ADDRESS+db.getAllLogin().get(0),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        Login_Credentials login = null;

                        Log.e("get_current_user", String.valueOf(response));
                        try {
                            login_credentials = new ArrayList<>();
                            JSONObject JObject = new JSONObject(String.valueOf(response));


                            JSONArray jsonArray = JObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    default_address = jsonObject1.getInt("default_address");
                                    login = new Login_Credentials();
                                    if (default_address==1)
                                    {
                                        Log.e("postion", String.valueOf(i));
                                        address_position=1;

                                        location_text = jsonObject1.getString("location");
                                        country = jsonObject1.getString("country");
                                        city = jsonObject1.getString("city");
                                        district = jsonObject1.getString("district");
                                        id_add = jsonObject1.getString("id");
                                        first_name = jsonObject1.getString("first_name");
                                        last_name = jsonObject1.getString("last_name");
                                        default_address = jsonObject1.getInt("default_address");
                                        String contact_no = jsonObject1.getString("contact_no");
                                        String country_code = jsonObject1.getString("country_code");

                                        String nickname = jsonObject1.getString("nick_name");
                                        String complex = jsonObject1.getString("complex");
                                        String streetname = jsonObject1.getString("street_name");
                                        String landmark = jsonObject1.getString("land_mark");

                                        house_no = jsonObject1.getString("house_no");
                                        area = jsonObject1.getString("area");

                                        login.setFirstname(first_name);
                                        login.setLastname(last_name);
                                        login.setAddId(id_add);
                                        login.setLocation(location_text);
                                        login.setCountry(country);
                                        login.setCity(city);
                                        login.setDistrict(district);
                                        login.setHouse(house_no);
                                        login.setArea(area);
                                        login.setContact(contact_no);
                                        login.setCountry_code(country_code);
                                        login.setNickName(nickname);
                                        login.setComplex(complex);
                                        login.setStreetName(streetname);
                                        login.setLandMark(landmark);
                                        login.setDefaultAddress(default_address);


                                        login.setDefault_address(address_position);
                                        login_credentials.add(login);
                                    }
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    login = new Login_Credentials();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    default_address = jsonObject1.getInt("default_address");
                                    if (default_address==0)
                                    {
                                        location_text = jsonObject1.getString("location");
                                        country = jsonObject1.getString("country");
                                        city = jsonObject1.getString("city");
                                        district = jsonObject1.getString("district");
                                        id_add = jsonObject1.getString("id");
                                        first_name = jsonObject1.getString("first_name");
                                        last_name = jsonObject1.getString("last_name");
                                        default_address = jsonObject1.getInt("default_address");
                                        String contact_no = jsonObject1.getString("contact_no");
                                        String country_code = jsonObject1.getString("country_code");
                                        house_no = jsonObject1.getString("house_no");
                                        area = jsonObject1.getString("area");
                                        String nickname = jsonObject1.getString("nick_name");
                                        String complex = jsonObject1.getString("complex");
                                        String streetname = jsonObject1.getString("street_name");
                                        String landmark = jsonObject1.getString("land_mark");

                                        login.setFirstname(first_name);
                                        login.setLastname(last_name);
                                        login.setAddId(id_add);
                                        login.setLocation(location_text);
                                        login.setCountry(country);
                                        login.setCity(city);
                                        login.setDistrict(district);
                                        login.setContact(contact_no);
                                        login.setCountry_code(country_code);
                                        login.setHouse(house_no);
                                        login.setArea(area);
                                        login.setNickName(nickname);
                                        login.setComplex(complex);
                                        login.setStreetName(streetname);
                                        login.setLandMark(landmark);
                                        login.setDefaultAddress(default_address);

                                        login_credentials.add(login);
                                    }

                                }

                                Log.e("addid", id_add);
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(MyAddress_Activity.this, login_credentials);
                                recycler_view.setLayoutManager(new LinearLayoutManager(MyAddress_Activity.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Log.e("Err",e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.cancel();
                Log.e("Err", String.valueOf(volleyError));
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(MyAddress_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MyAddress_Activity.this, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<String, String>();
               // String title = String.valueOf(db.getCookie());
               // String role = title.substring(1, title.length() - 1);
              //  Log.e("cookie", role);
                header.put("Content-Type", "application/json; charset=utf-8");
               // header.put("Authorization", "Bearer " + role);
                return header;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);

    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        public ArrayList<Login_Credentials> login_credentialsArrayList;
        private Typeface mDynoRegular;
        public ArrayList<Float> subtotal_list = new ArrayList<>();
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_FOOTER = 1;
        private static final int TYPE_ITEM = 2;
        private int lastSelectedPosition = -1;
        public String address_id;
        int selectedPosition=-1;
        int onClick=0;

        public RecyclerViewAdapter(Context context, ArrayList<Login_Credentials> login_credentialses) {
            this.mContext = context;
            this.login_credentialsArrayList = login_credentialses;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                //Inflating recycle view item layout
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_item, parent, false);
                return new ItemViewHolder(itemView);
            } else if (viewType == TYPE_HEADER) {
                //Inflating header view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_header, parent, false);
                return new HeaderViewHolder(itemView);
            } else if (viewType == TYPE_FOOTER) {
                //Inflating footer view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_address_footer, parent, false);
                return new FooterViewHolder(itemView);
            } else return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                headerHolder.address_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_addr_click = 1;
                        Intent intent = new Intent(MyAddress_Activity.this, Add_New_Address_Activity.class);
                        startActivity(intent);

                    }
                });

            } else if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                FooterViewHolder footerHolder = (FooterViewHolder) holder;
                footerHolder.mContinue.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                      /*  if (lastSelectedPosition == -1) {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(My_Address_Activity.this);
                            alertDialogBuilder.setMessage("Select a Address to Deliver");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            arg0.dismiss();
                                        }
                                    });

                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {


                            RequestQueue queue = Volley.newRequestQueue(My_Address_Activity.this);
                            Map<String, String> params = new HashMap<String, String>();
                            final ProgressDialog progressDialog = new ProgressDialog(My_Address_Activity.this);
                            progressDialog.setMessage("Please wait ...");
                            progressDialog.show();
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
                            tv1.setTextSize(20);
                            tv1.setTypeface(mDynoRegular);
                            tv1.setText("Please wait ...");


                            //  params.put("customer_id",db.getAllLogin().get(0));
                            JSONArray array = new JSONArray();
                            JSONObject obj;
                            for (int i = 0; i < Order_details_Activity.cart_priceList.size(); i++) {
                                obj = new JSONObject();
                                try {
                                    obj.put("actual_selling_amount", "0");
                                    obj.put("discount", "0");
                                    obj.put("finalamount", "0");
                                    obj.put("finalsaveamount", "0");
                                    obj.put("price_id", Order_details_Activity.cart_priceList.get(i).getCart_price_id());
                                    obj.put("product_id", Order_details_Activity.cart_priceList.get(i).getCart_productId());
                                    obj.put("quantity", Order_details_Activity.cart_priceList.get(i).getCart_quantity());
                                    obj.put("price", Order_details_Activity.cartArrayList.get(i).getMarketPrice());
                                    obj.put("quantity_type_id", Order_details_Activity.cartArrayList.get(i).getQuantity_type());
                                    obj.put("saved_amount", "0");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                array.put(obj);
                            }


                            // params.put("products", String.valueOf(array));
                            JSONObject obj1 = new JSONObject();
                            try {
                                obj1.put("deliverytype", delvy_pickup);
                                obj1.put("payment_type", "cod");
                                obj1.put("addressselection", address_id);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //  params.put("order", String.valueOf(obj1));
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("customer_id", db.getAllLogin().get(0));
                                jsonObject.put("products", array);
                                jsonObject.put("order", obj1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("json_placeorder", jsonObject.toString());

                            JsonObjectRequest jsonObjReq = null;
                            try {
                                jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                        Constants.PLACE_ORDER, new JSONObject(jsonObject.toString()),
                                        new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject object) {
                                                try {
                                                    Log.e("place_order", String.valueOf(object));


                                                    String status = object.getString("status");

                                                    if (status.equals("Success")) {

                                                        progressDialog.cancel();
                                                        String order_id = object.getString("order_id");


                                                        Intent intent = new Intent(My_Address_Activity.this, Payment_Gateway_Activity.class);
                                                        Bundle mBundle = new Bundle();
                                                        mBundle.putString("order_id", order_id);
                                                        intent.putExtras(mBundle);
                                                        startActivity(intent);



                                                    } else {
                                                        String reason = object.getString("reason");
                                                        Toast.makeText(My_Address_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                                            Toast.makeText(My_Address_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                                        } else
                                            Toast.makeText(My_Address_Activity.this.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                                        VolleyLog.e("responseError" + volleyError);

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
                                        String title = String.valueOf(db.getCookie());
                                        String role = title.substring(1, title.length() - 1);
                                        Log.e("cookie", role);
                                        header.put("Content-Type", "application/json; charset=utf-8");
                                        header.put("Authorization", "Bearer " + role);
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
*/

                    }
                });

            } else if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
               // login_credentialsArrayList.get(address_position);
                if(position==1)
                {
                    itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_listview));

                }else {
                    itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_unselected));
                }
                if (onClick==1)
                {
                    if (position > 0) {

                    if (lastSelectedPosition == position) {
                        //itemViewHolder.relativeLayout.setBackgroundColor(getResources().getColor(R.color.app_bg));
                        itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_listview));
                    } else {
                        itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_unselected));
                    }
                }
                }

                /*if(selectedPosition==position)
                {
                    itemViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
                }

                else{
                    itemViewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));

                }
*/
                if (position > 0) {
                   // itemViewHolder .relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_selector));


                        address_id = login_credentialsArrayList.get(position - 1).getAddId();

                    final Login_Credentials login_credentials = login_credentialsArrayList.get(position - 1);
                    itemViewHolder.address_field.setText(login_credentials.getFirstname() + " " + login_credentials.getLastname() + "\n" + login_credentials.getHouse() + "\n" + login_credentials.getCity() + "\n" + login_credentials.getArea());
                    itemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edit_img_click = 1;
                            CheckoutAddress=0;
                            Intent intent = new Intent(MyAddress_Activity.this, Add_Update_Address_Activity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("one", login_credentials.getNickName());
                            mBundle.putString("two", login_credentials.getFirstname());
                            mBundle.putString("three", login_credentials.getLastname());
                            mBundle.putString("four", login_credentials.getContact());
                            mBundle.putString("five", login_credentials.getCity());
                            mBundle.putString("six", login_credentials.getHouse());
                            mBundle.putString("seven", login_credentials.getComplex());
                            mBundle.putString("eight", login_credentials.getArea());
                            mBundle.putString("nine", login_credentials.getStreetName());
                            mBundle.putString("ten", login_credentials.getLandMark());
                            mBundle.putInt("default", login_credentials.getDefaultAddress());
                            mBundle.putString("id", login_credentials.getAddId());
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        }
                    });
                   /* itemViewHolder.selectionState.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("addid",login_credentials.getAddId());
                            address_id=login_credentials.getAddId();
                        }
                    });*/

                }
            }
        }

        @Override
        public int getItemCount() {

            return login_credentialsArrayList.size() + 2;//Add 2 more size to array list for Header and Fo        }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == login_credentialsArrayList.size() + 1) {
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

/*

        @Override
        public int getItemCount() {
            return cartArrayList.size() + 2;
        }

*/

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            LinearLayout address_layout;
            TextView myAddr;

            public HeaderViewHolder(View view) {
                super(view);
                address_layout = (LinearLayout) view.findViewById(R.id.address_layout);
                myAddr = (TextView) view.findViewById(R.id.add_new_addr);
                myAddr.setTypeface(mDynoRegular);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {
            Button mContinue;

            public FooterViewHolder(View view) {
                super(view);
                mContinue = (Button) view.findViewById(R.id.continue_btn);
                mContinue.setTypeface(mDynoRegular);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView mEdit;
            TextView address_field;
            RelativeLayout relativeLayout;

            public ItemViewHolder( View view) {
                super(view);
                mEdit = (ImageView) view.findViewById(R.id.edit);
                address_field = (TextView) view.findViewById(R.id.address);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_item);
                address_field.setTypeface(mDynoRegular);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick=1;
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                });

            }

        }


    }

    private void setFont() {
        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mToolbarTitle.setTypeface(mDynoRegular);

    }

}
