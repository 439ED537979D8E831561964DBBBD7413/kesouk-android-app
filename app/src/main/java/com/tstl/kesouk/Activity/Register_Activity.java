package com.tstl.kesouk.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.android.volley.toolbox.Volley;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.tstl.kesouk.Activity.Add_Address_Activity.checkbox_value;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strArea;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strCity;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strFirstname;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strHouse;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strLandmark;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strLastname;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strMobile;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strNickname;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strResident;
import static com.tstl.kesouk.Activity.Add_Address_Activity.strStreet;
import static com.tstl.kesouk.Activity.SplashActivity.isNetworkAvailable;

/**
 * Created by user on 10-Jan-18.
 */

public class Register_Activity  extends AppCompatActivity {
    public AppCompatEditText mFirstName, mLastName, mMobile, mEmail, mPassword, mRetypePass, mAddress1, mAddress2,mCity;
    public TextInputLayout mFirstlayout, mLastLayout, mMobNumLayout, mEmailLayout, mPassLayout, mRetypeLayout, mAddress1Layout, mAddress2Layout,mCityLayout;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private Button mSignUp,mAgree,mDecline;
    private TextView mToolbarTitle,mTermsCond;
    public static  int newRegister=0;
    Dialog dialog;
    TextView mterms_text,mArea,area_text,agree_text;
    public RelativeLayout  mArea_layout;
    String mArea_name_lib,area="";
    SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();
    RelativeLayout mAddressLayout;
   public static int temp_addr_edit=0;
    public static Button mAddAddress;
    public static int address_added=0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mFirstName = (AppCompatEditText) findViewById(R.id.input_firstname);
        mFirstlayout = (TextInputLayout) findViewById(R.id.input_layout_firstname);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        ((ImageView) mToolbar.findViewById(R.id.img))
                .setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("REGISTER");
        mToolbarTitle.setVisibility(View.VISIBLE);


        mLastName = (AppCompatEditText) findViewById(R.id.input_lastname1);
        mLastLayout = (TextInputLayout) findViewById(R.id.input_layout_lastname);

        mMobile = (AppCompatEditText) findViewById(R.id.input_mobile);
        mMobNumLayout = (TextInputLayout) findViewById(R.id.input_layout_mobile);

        mEmail = (AppCompatEditText) findViewById(R.id.input_email);
        mEmailLayout = (TextInputLayout) findViewById(R.id.input_layout_email);

        mPassword = (AppCompatEditText) findViewById(R.id.input_password);
        mPassLayout = (TextInputLayout) findViewById(R.id.input_layout_password);

        mRetypePass = (AppCompatEditText) findViewById(R.id.input_retype_pass);
        mRetypeLayout = (TextInputLayout) findViewById(R.id.input_layout_retypePass);

        mAddress1 = (AppCompatEditText) findViewById(R.id.input_address1);
        mAddress1Layout = (TextInputLayout) findViewById(R.id.input_layout_address1);

        mAddress2 = (AppCompatEditText) findViewById(R.id.input_address2);
        mAddress2Layout = (TextInputLayout) findViewById(R.id.input_layout_address2);

        mCity = (AppCompatEditText) findViewById(R.id.input_city);
        mCityLayout = (TextInputLayout) findViewById(R.id.input_layout_city);
        area_text = (TextView) findViewById(R.id.area);

        mArea = (TextView) findViewById(R.id.area1);
        mArea_layout = (RelativeLayout) findViewById(R.id.layer_ten);

        mSignUp = (Button) findViewById(R.id.sign_in_btn);
        mTermsCond = (TextView) findViewById(R.id.termscond);
        agree_text = (TextView) findViewById(R.id.agree_text);
        mTermsCond.setMovementMethod(LinkMovementMethod.getInstance());
        mAddressLayout = (RelativeLayout) findViewById(R.id.address_layout);
        mAddAddress = (Button) findViewById(R.id.add_new_addr);
        setFont();
        mTermsCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(Register_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.terms_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                mterms_text = (TextView)dialog. findViewById(R.id.temns_text);
                mAgree = (Button)dialog. findViewById(R.id.agree_button);
                mDecline = (Button)dialog. findViewById(R.id.decline_button);
                mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");

                mAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mterms_text.setTypeface(mDynoRegular);
                mAgree.setTypeface(mDynoRegular);
                mDecline.setTypeface(mDynoRegular);

            }
        });
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp_addr_edit==0)
                {
                    Intent intent=new Intent(Register_Activity.this,Add_Address_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(Register_Activity.this,Add_Address_Activity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("one", strNickname);
                    bundle.putString("two",strFirstname);
                    bundle.putString("three", strLastname);
                    bundle.putString("four", strMobile);
                    bundle.putString("five", strCity);
                    bundle.putString("six", strHouse);
                    bundle.putString("seven",strResident);
                    bundle.putString("eight", strArea);
                    bundle.putString("nine", strStreet);
                    bundle.putString("ten", strLandmark);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }


            }
        });
        mArea_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
            /*  //  if (!TextUtils.isEmpty(country_code_lib)) {
                    final ProgressDialog progressDialog = new ProgressDialog(Register_Activity.this);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                //    getCitiesList(country_code_lib, progressDialog);
               // }*/


                items.add("Kenya");
                items.add("kenya");


                spinnerDialog=new SpinnerDialog(Register_Activity.this,items,"Select Area");// With No Animation


                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        mArea.setText(item);
                    }
                });
                spinnerDialog.showSpinerDialog();

            }
        });
        if(!strMobile.equals(""))
        {
            mAddAddress.setText("EDIT ADDRESS");
            temp_addr_edit=1;

        }
       /* if (!city_list.isEmpty() && !items.isEmpty()) {
            spinnerDialog = new SpinnerDialog(Register_Activity.this, items, "Select City");// With No Animation
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    //Toast.makeText(Register_Activity.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                    Log.e(" Position: ", position + item);
                    city_list.get(position).getLat();
                    city_list.get(position).getLongitude();
                    Log.e("pos", String.valueOf(city_list.get(position).getLat()));
                    Log.e("pos", String.valueOf(city_list.get(position).getLongitude()));
                    getCompleteAddressString(city_list.get(position).getLat(), city_list.get(position).getLongitude());


                    mCity_name_lib = item;
                    mCity.setText(item);
                }
            });
            spinnerDialog.showSpinerDialog();
        }
*/

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((!(mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() || mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() || mRetypePass.getText().toString().isEmpty() ||(mPassword.getText().toString().equals("")) && (mRetypePass.getText().toString().equals("") ))))
                {

                    if (isValidMail(mEmail.getText().toString())) {
                        if (!mPassword.getText().toString().equals(mRetypePass.getText().toString())) {
                            Toast.makeText(Register_Activity.this, "Password Mismatch ", Toast.LENGTH_LONG).show();
                        } else {
                            if (isNetworkAvailable(Register_Activity.this)) {
                                if (address_added==0)
                                {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Register_Activity.this);
                                    alertDialogBuilder.setMessage("Please Add Address !");
                                    alertDialogBuilder.setCancelable(false);

                                    alertDialogBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    arg0.dismiss();

                                                }
                                            });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));

                                }
                                else
                                {
                                    getRegister();
                                }




                            } else {
                                Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                            }

                        }

                    } else {
                        Toast.makeText(Register_Activity.this, "Please enter valid Email id ", Toast.LENGTH_LONG).show();

                    }



                } else {
                    Toast.makeText(Register_Activity.this, "Please enter all the details ", Toast.LENGTH_LONG).show();

                }


            }
        });

    }
    private void setFont()
    {
        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");

        mFirstName.setTypeface(mDynoRegular);
        mFirstlayout.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);

        mLastName.setTypeface(mDynoRegular);
        mLastLayout.setTypeface(mDynoRegular);
        mMobile.setTypeface(mDynoRegular);
        mMobNumLayout.setTypeface(mDynoRegular);
        mEmail.setTypeface(mDynoRegular);
        mEmailLayout.setTypeface(mDynoRegular);
        mPassword.setTypeface(mDynoRegular);
        mPassLayout.setTypeface(mDynoRegular);
        mRetypePass.setTypeface(mDynoRegular);
        mRetypeLayout.setTypeface(mDynoRegular);
        mAddress1.setTypeface(mDynoRegular);
        mAddress2.setTypeface(mDynoRegular);
        mAddress1Layout.setTypeface(mDynoRegular);
        mAddress2Layout.setTypeface(mDynoRegular);
        mCity.setTypeface(mDynoRegular);
        mCityLayout.setTypeface(mDynoRegular);
        mArea.setTypeface(mDynoRegular);
        area_text.setTypeface(mDynoRegular);
        mSignUp.setTypeface(mDynoRegular);
        mTermsCond.setTypeface(mDynoRegular);

    }
    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }
    private void getRegister() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
        tv1.setTextSize(20);
        tv1.setTypeface(mDynoRegular);
        tv1.setText("Please wait ...");
/*

        params.put("country_shortname", "KE");
        params.put("country_code","214");
        params.put("country", "Kenya");
*/


        //  params.put("customer_id",db.getAllLogin().get(0));
        JSONArray array=new JSONArray();
        JSONObject params;

            params=new JSONObject();
            try {
                params.put("city", strCity);
                params.put("area",strArea);
                params.put("first_name", strFirstname);
                params.put("last_name", strLastname);
                params.put("house_no",strHouse);
                params.put("contact_no", strMobile);
                params.put("street_name", strStreet);
                params.put("complex",strResident);
                params.put("land_mark", strLandmark);
                params.put("default_address", checkbox_value);
                params.put("nick_name", strNickname);
                params.put("country_shortname", "KE");
                params.put("country_code","214");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(params);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email", mEmail.getText().toString());
            jsonObject.put("first_name", mFirstName.getText().toString());
            jsonObject.put("last_name", mLastName.getText().toString());
            jsonObject.put("phone_no", mMobile.getText().toString());
            jsonObject.put("password", mRetypePass.getText().toString());
            jsonObject.put("country_shortname", "KE");
            jsonObject.put("country_code","214");

            jsonObject.put("address",array);

        } catch (JSONException e) {
            e.printStackTrace();
        }





        Log.e("json",jsonObject.toString());

        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.REGISTER, new JSONObject(String.valueOf(jsonObject)),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.e("register_url", String.valueOf(object));
                                String status = object.getString("status");
                                if (status.equals("Success")) {
                                    progressDialog.cancel();
                                    newRegister=1;
                                    Intent intent = new Intent(Register_Activity.this, OTP_Activity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email_id",mEmail.getText().toString() );
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(Register_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(Register_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
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
