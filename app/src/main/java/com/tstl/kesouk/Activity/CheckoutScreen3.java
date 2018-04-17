package com.tstl.kesouk.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.Volley;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Activity.CheckoutScreen2.a;
import static com.tstl.kesouk.Activity.CheckoutScreen1.def_id;
import static com.tstl.kesouk.Activity.CheckoutScreen2.passDate;
import static com.tstl.kesouk.Fragments.Basket_Fragment.cart_priceList;
import static com.tstl.kesouk.Activity.CheckoutScreen2.deliveryType;
import static com.tstl.kesouk.Fragments.Basket_Fragment.cartArrayList;
import static com.tstl.kesouk.Fragments.Basket_Fragment.cartcount;
import static com.tstl.kesouk.Fragments.Basket_Fragment.expressCount;
import static com.tstl.kesouk.Fragments.Basket_Fragment.result;

/**
 * Created by user on 07-Mar-18.
 */

public class CheckoutScreen3 extends AppCompatActivity{
    Button mContinue;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView toolbarTitle,carttext,address,date,time,charge;
    ImageView img;
    DB db;
    public static String order_id;
    String yourDataObject=null ;
    String yourDataObject1= null;
    String yourDataObject3= null;
    String yourDataObject2= null;
    String yourDataObject4= null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_screen3);
        mContinue=(Button)findViewById(R.id.continue_btn);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Order Summary");
        img = (ImageView) findViewById(R.id.img);
        setSupportActionBar(mToolbar);
        db=new DB(CheckoutScreen3.this);

        carttext = (TextView) findViewById(R.id.cart_value);
        address = (TextView) findViewById(R.id.address);
        date = (TextView) findViewById(R.id.input_date);
        time = (TextView) findViewById(R.id.input_time);
        charge = (TextView) findViewById(R.id.input_charge);

        setFont();

        if (getIntent().hasExtra("date")) {
            yourDataObject = getIntent().getStringExtra("date");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "date");
        }
        if (getIntent().hasExtra("charge")) {
            yourDataObject1 = getIntent().getStringExtra("charge");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "charge");
        }

        if (getIntent().hasExtra("time")) {
            yourDataObject2 = getIntent().getStringExtra("time");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "time");
        }
        if (getIntent().hasExtra("amount")) {
            yourDataObject3 = getIntent().getStringExtra("amount");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "amount");
        }
        if (getIntent().hasExtra("id")) {
            yourDataObject4 = getIntent().getStringExtra("id");
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + "id");
        }
        carttext.setText("Your cart value is SH "+result+" for "+cartcount +" items.");
        address.setText(db.getDefaultAddr().get(0));
        date.setText(yourDataObject);
        time.setText(yourDataObject2);
        charge.setText(yourDataObject1);

        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaceOrder();


            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto_Regular.ttf");

        toolbarTitle.setTypeface(mDynoRegular);
    }

    public void getPlaceOrder() {

        RequestQueue queue = Volley.newRequestQueue(CheckoutScreen3.this);
        Map<String, String> params = new HashMap<String, String>();
        final ProgressDialog progressDialog = new ProgressDialog(CheckoutScreen3.this);
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
        for (int i = 0; i < cart_priceList.size();i++) {
            obj = new JSONObject();
            try {
                obj.put("actual_selling_amount", "0");
                obj.put("discount", cartArrayList.get(i).getDiscount());
                obj.put("finalamount", "0");
                obj.put("finalsaveamount", "0");
                obj.put("price_id", cart_priceList.get(i).getCart_price_id());
                obj.put("product_id", cart_priceList.get(i).getCart_productId());
                obj.put("quantity", cart_priceList.get(i).getCart_quantity());
                obj.put("price", cartArrayList.get(i).getMarketPrice());
                obj.put("quantity_type_id", cartArrayList.get(i).getQuantity_type());
                obj.put("saved_amount", "0");
                if(cartArrayList.get(i).getExpressDelivery().equals("1"))
                {
                    obj.put("delivery_type", "Express");
                }
                else
                {
                    obj.put("delivery_type", "Standard");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }


        // params.put("products", String.valueOf(array));
        JSONObject obj1 = new JSONObject();
        try {
            if(deliveryType.equals("Express"))
            {
                if(cartcount==expressCount)
                {
                    obj1.put("express_charge", "50");
                    obj1.put("timeslot", "");
                }
                else
                {
                    obj1.put("express_charge", a);
                    obj1.put("timeslot", yourDataObject4);
                }

                obj1.put("standard_charge", "0");

            }
            else
            {
                obj1.put("express_charge", "0");
                obj1.put("standard_charge", a);
                obj1.put("timeslot", yourDataObject4);
            }


            obj1.put("payment_type", "cod");
            obj1.put("address_id", def_id);

            obj1.put("standard_delivery_date", passDate);

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
                                     order_id = object.getString("order_id");
                                    Intent intent = new Intent(CheckoutScreen3.this, CheckoutScreen4.class);
                                    startActivity(intent);


                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(CheckoutScreen3.this, reason, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(CheckoutScreen3.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(CheckoutScreen3.this.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                    VolleyLog.e("responseError" + volleyError);

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

}
