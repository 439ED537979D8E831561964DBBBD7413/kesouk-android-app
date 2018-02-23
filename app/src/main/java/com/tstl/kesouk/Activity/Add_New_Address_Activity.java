package com.tstl.kesouk.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.City;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.tstl.kesouk.Activity.Register_Activity.address_added;
import static com.tstl.kesouk.Activity.Register_Activity.mAddAddress;
import static com.tstl.kesouk.Activity.Register_Activity.temp_addr_edit;


/**
 * Created by user on 05-Feb-18.
 */

public class Add_New_Address_Activity extends AppCompatActivity {
    public AppCompatEditText mNickname,mFirstName, mLastName, mMobile,mHouse,mResident, mStreet, mLandmark;
    public TextInputLayout mNicknameLayout,mFirstlayout, mLastLayout, mMobNumLayout, mHouseLayout, mResidentLayout, mStreetLayout,mLandmarkLayout;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    String age_item, gender_Text, mEmailId, mCity_name_lib, add_id;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    Location location;
    Double MyLat, MyLong;
    private ImageView mCompass;
    Spinner country_code_spinner, country_spinner;
    Object country_code, country_name;
    String country_dial_code_value_lib, country_name_value_lib, country_code_lib = "SA";
    ArrayList<String> items = new ArrayList<>();
    int locate = 0;
    Gson gson;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;
    String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private TextView mToolbarTitle,chekbox_text;
    private TextView mArea, mCity,area_label,city_label;
    public RelativeLayout  mCity_layout,mArea_layout;
    SpinnerDialog spinnerDialog,areaDialog;
    Button mSave;
    int temp = 0,cityPosition;
    DB db;
    private ImageView backBtn;
    AppCompatCheckBox checkBox;
    ArrayList<String> areaArrayList ;
    public static int checkbox_value=0;
    public static String strNickname="",strFirstname="",strLastname="",strMobile="",strHouse="",strCity="",strResident="",strArea="",strStreet="",strLandmark="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_activity);

        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);

      //  ((TextView) mToolbar.findViewById(R.id.toolbar_title))
       //         .setText("ADD NEW ADDRESS");

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("ADD NEW ADDRESS");
        mToolbarTitle.setVisibility(View.VISIBLE);
        db= new DB(this);
        backBtn = (ImageView) findViewById(R.id.img);


        setSupportActionBar(mToolbar);

        /*mToolbar.setNavigationIcon(R.drawable.back_button);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                onBackPressed();
            }
        });
*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        chekbox_text = (TextView) findViewById(R.id.remeber_me1);
        mNickname = (AppCompatEditText) findViewById(R.id.input_nick);
        mNicknameLayout= (TextInputLayout) findViewById(R.id.input_layout_nick);

        mFirstName = (AppCompatEditText) findViewById(R.id.input_firstname);
        mFirstlayout = (TextInputLayout) findViewById(R.id.input_layout_firstname);

        mLastName = (AppCompatEditText) findViewById(R.id.input_lastname1);
        mLastLayout = (TextInputLayout) findViewById(R.id.input_layout_lastname);

        mMobile = (AppCompatEditText) findViewById(R.id.input_mobile);
        mMobNumLayout = (TextInputLayout) findViewById(R.id.input_layout_mobile);

        mHouse = (AppCompatEditText) findViewById(R.id.input_house);
        mHouseLayout = (TextInputLayout) findViewById(R.id.input_layout_house);

        mResident = (AppCompatEditText) findViewById(R.id.input_resident);
        mResidentLayout = (TextInputLayout) findViewById(R.id.input_layout_resident);

        mStreet = (AppCompatEditText) findViewById(R.id.input_street);
        mStreetLayout = (TextInputLayout) findViewById(R.id.input_layout_street);

        mLandmark = (AppCompatEditText) findViewById(R.id.input_landmark);
        mLandmarkLayout = (TextInputLayout) findViewById(R.id.input_layout_landmark);

        checkBox = (AppCompatCheckBox) findViewById(R.id.checkbox1);



        mCity = (TextView) findViewById(R.id.city1);
        mArea = (TextView) findViewById(R.id.area1);
        mCity_layout = (RelativeLayout) findViewById(R.id.layer_four);
        mArea_layout = (RelativeLayout) findViewById(R.id.layer_area);
        area_label = (TextView) findViewById(R.id.area_label);
        city_label = (TextView) findViewById(R.id.city_label);

        mSave = (Button) findViewById(R.id.save);



        mCity_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(country_code_lib)) {
                    final ProgressDialog progressDialog = new ProgressDialog(Add_New_Address_Activity.this);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                    getCitiesList(country_code_lib, progressDialog);
                }
            }
        });
        mArea_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(Add_New_Address_Activity.this);
                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                getAreaList(country_code_lib, progressDialog);

            }
        });

       /* if (My_Address_Activity.add_addr_click == 1|| My_Address_MenuActivity.add_addr_click_menu==1) {
            String value = getIntent().getExtras().getString("email_id");
            mEmail.setText(value);
            My_Address_Activity.add_addr_click = 0;
            My_Address_MenuActivity.add_addr_click_menu=0;
        } else if (My_Address_Activity.edit_img_click == 1||My_Address_MenuActivity.edit_img_click==1) {
            My_Address_Activity.edit_img_click = 0;
            String value = getIntent().getExtras().getString("fname");
            mFirstName.setText(value);
            String value1 = getIntent().getExtras().getString("lname");
            mLastName.setText(value1);
            String value2 = getIntent().getExtras().getString("email_id");
            mEmail.setText(value2);
            String value3 = getIntent().getExtras().getString("country_code");
            country_code_spinner.setSelection(getIndex(country_code_spinner, value3));
            String value4 = getIntent().getExtras().getString("mobile");
            mMobile.setText(value4);
            String value5 = getIntent().getExtras().getString("country");
            mCountry.setText(value5);
            Country country_obj = Country.getCountryByName(value5);
            country_name_value_lib = country_obj.getName();
            country_code_lib = country_obj.getCode();
            String value6 = getIntent().getExtras().getString("city");
            mCity.setText(value6);
            String value7 = getIntent().getExtras().getString("district");
            mDistrict.setText(value7);
            String value8 = getIntent().getExtras().getString("location");
            mLocation.setText(value8);
            String value9 = getIntent().getExtras().getString("id");
            add_id = value9;
            temp = 1;


        }
*/
        mCity_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(country_code_lib)) {
                    final ProgressDialog progressDialog = new ProgressDialog(Add_New_Address_Activity.this);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                    getCitiesList(country_code_lib, progressDialog);
                }
                // spinnerDialog=new SpinnerDialog(MainActivity.this,items,"Select or Search City",R.style.DialogAnimations_SmileWindow);// With 	Animation
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!(mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() || mMobile.getText().toString().isEmpty() || mHouse.getText().toString().isEmpty() || mCity.getText().toString().isEmpty() || mArea.getText().toString().isEmpty())))
                {


                    if (SplashActivity.isNetworkAvailable(Add_New_Address_Activity.this)) {

                        if (checkBox.isChecked())
                        {
                            checkbox_value=1;
                        }else
                        {
                            checkbox_value=0;
                        }



                        addAddress();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(Add_New_Address_Activity.this, "Please enter all the details ", Toast.LENGTH_LONG).show();

                }
            }
        });




        setFont();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }


    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mFirstName.setTypeface(mDynoRegular);
        mFirstlayout.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);

        mLastName.setTypeface(mDynoRegular);
        mLastLayout.setTypeface(mDynoRegular);
        mMobile.setTypeface(mDynoRegular);
        mMobNumLayout.setTypeface(mDynoRegular);
        mNickname.setTypeface(mDynoRegular);
        mNicknameLayout.setTypeface(mDynoRegular);
        mHouse.setTypeface(mDynoRegular);
        mHouseLayout.setTypeface(mDynoRegular);
        mResident.setTypeface(mDynoRegular);
        mResidentLayout.setTypeface(mDynoRegular);
        mStreet.setTypeface(mDynoRegular);
        mStreetLayout.setTypeface(mDynoRegular);
        mLandmark.setTypeface(mDynoRegular);
        mLandmarkLayout.setTypeface(mDynoRegular);
        mSave.setTypeface(mDynoRegular);
        chekbox_text.setTypeface(mDynoRegular);

        mCity.setTypeface(mDynoRegular);
        mArea.setTypeface(mDynoRegular);
        city_label.setTypeface(mDynoRegular);
        area_label.setTypeface(mDynoRegular);


    }

    private void addAddress() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
        tv1.setTextSize(20);
        tv1.setTypeface(mDynoRegular);
        tv1.setText("Please wait ...");


        params.put("city", mCity.getText().toString());
        params.put("user_id",  db.getAllLogin().get(0));
        params.put("area",mArea.getText().toString());
        params.put("first_name", mFirstName.getText().toString());
        params.put("last_name", mLastName.getText().toString());
        params.put("house_no",mHouse.getText().toString());
        params.put("contact_no", mMobile.getText().toString());
        params.put("street_name", mStreet.getText().toString());
        params.put("complex",mResident.getText().toString());
        params.put("land_mark", mLandmark.getText().toString());
        params.put("default_address", String.valueOf(checkbox_value));
        params.put("nick_name", mNickname.getText().toString());
        params.put("country_shortname", "KE");
        params.put("country_code","214");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.ADD_NEW_ADDRESS, new JSONObject(params),

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("addd_address", String.valueOf(object));
                            String status = object.getString("status");
                            if (status.equals("Success")||status.equals("success")) {

                                String reason = object.getString("reason");
                                Toast.makeText(Add_New_Address_Activity.this, reason, Toast.LENGTH_LONG).show();
                                progressDialog.cancel();

                                Intent intent = new Intent(Add_New_Address_Activity.this, MyAddress_Activity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                            } else {
                                String reason = object.getString("reason");
                                Toast.makeText(Add_New_Address_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                    Toast.makeText(Add_New_Address_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
               // String title = String.valueOf(db.getCookie());
               // String role = title.substring(1, title.length() - 1);
               // Log.e("cookie",role);
                header.put("Content-Type", "application/json; charset=utf-8");
               // header.put("Authorization","Bearer "+role);
                return header;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);

    }


    private void getCitiesList(final String name, final ProgressDialog progressDialog) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.GET_CITIES, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            if (progressDialog != null) progressDialog.cancel();
                            Log.e("get_city", String.valueOf(object));
                            String status=object.getString("status");
                            if(status.equals("Success")) {

                                JSONObject JObject = new JSONObject(String.valueOf(object));
                                JSONArray DataArray = JObject.getJSONArray("data");
                                City city = null;
                                if (DataArray.length() != 0) {
                                    /*final List<City> city_list = Arrays.asList(gson.fromJson(object.getJSONArray("data").toString(), City[].class));
                                    items = new ArrayList<>();
                                    for (int i = 0; i < city_list.size(); i++) {
                                        String city_name = city_list.get(i).getmCity_name();
                                        items.add(city_name);
                                        Log.e("city_list", String.valueOf(city_list));
                                       *//* if (name.equals("IN") && i > 100) {
                                            break;
                                        }*//*

                                        JSONArray areaArray = JObject.getJSONArray("data");

*/

                                    // }
                                    items = new ArrayList<>();
                                    for(int i=0;i<DataArray.length();i++)
                                    {
                                        JSONObject jsonObject = DataArray.getJSONObject(i);

                                        String city_name =jsonObject.getString("name");
                                        items.add(city_name);
                                        city=new City();
                                        city.setmCity_name(city_name);

                                        JSONArray areaArray = jsonObject.getJSONArray("areas");
                                        if(areaArray.length()!=0)
                                        {
                                            areaArrayList=new ArrayList<>();
                                            for(int j=0;j<DataArray.length();j++) {
                                                JSONObject jsonObject1 = areaArray.getJSONObject(j);
                                                String area_name =jsonObject1.getString("area_name");
                                                areaArrayList.add(area_name);
                                            }
                                            city.setmAreaList(areaArrayList);
                                        }
                                        else {

                                        }
                                        Log.e("getarealist", String.valueOf(city.getmAreaList()));




                                    }



                                    if ( !items.isEmpty()) {
                                        spinnerDialog = new SpinnerDialog(Add_New_Address_Activity.this, items, "Select City");// With No Animation
                                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                            @Override
                                            public void onClick(String item, int position) {
                                                //Toast.makeText(Register_Activity.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                                                Log.e(" Position: ", position + item);
                                                cityPosition=position;
                                                mCity_name_lib = item;
                                                mCity.setText(item);
                                            }
                                        });
                                        spinnerDialog.showSpinerDialog();
                                    }

                                }

                            }
                            else
                            {

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog != null) progressDialog.cancel();
                Log.e("verify_otp_error", "error" + volleyError);
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(Add_New_Address_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);

    }

    private void getAreaList(final String name, final ProgressDialog progressDialog) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.GET_CITIES, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            if (progressDialog != null) progressDialog.cancel();
                            Log.e("get_city", String.valueOf(object));
                            String status=object.getString("status");
                            if(status.equals("Success")) {

                                JSONObject JObject = new JSONObject(String.valueOf(object));
                                JSONArray DataArray = JObject.getJSONArray("data");
                                City city = null;
                                if (DataArray.length() != 0) {

                                    for(int i=0;i<DataArray.length();i++)
                                    {
                                        JSONObject jsonObject = DataArray.getJSONObject(i);

                                        String city_name =jsonObject.getString("name");

                                        if(mCity_name_lib.equals(city_name))
                                        {
                                            JSONArray areaArray = jsonObject.getJSONArray("areas");
                                            if(areaArray.length()!=0)
                                            {
                                                areaArrayList=new ArrayList<>();
                                                for(int j=0;j<DataArray.length();j++) {
                                                    JSONObject jsonObject1 = areaArray.getJSONObject(j);
                                                    String area_name =jsonObject1.getString("area_name");
                                                    areaArrayList.add(area_name);
                                                }
                                            }
                                            else {

                                            }


                                        }

                                    }


                                }

                            }
                            else {

                            }



                            if ( !areaArrayList.isEmpty()) {
                                spinnerDialog = new SpinnerDialog(Add_New_Address_Activity.this, areaArrayList, "Select City");// With No Animation
                                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                    @Override
                                    public void onClick(String item, int position) {
                                        //Toast.makeText(Register_Activity.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                                        Log.e(" Position: ", position + item);
                                        mArea.setText(item);
                                    }
                                });
                                spinnerDialog.showSpinerDialog();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (progressDialog != null) progressDialog.cancel();
                Log.e("verify_otp_error", "error" + volleyError);
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(Add_New_Address_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);

    }




}
