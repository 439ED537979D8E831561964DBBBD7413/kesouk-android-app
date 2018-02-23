package com.tstl.kesouk.Activity;

/**
 * Created by user on 10-Jan-18.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Activity.Login_Activity.forgotPassword;
import static com.tstl.kesouk.Activity.Register_Activity.newRegister;
import static com.tstl.kesouk.Activity.SplashActivity.isNetworkAvailable;


/**
 * Created by user on 12-Oct-17.
 */

public class OTP_Activity extends AppCompatActivity {

    public TextView mOtpText,mOtp;
    public Button mVerify;
    private Typeface mDynoRegular,mDynoRegular1;
    private Toolbar mToolbar;
    private TextView mToolbarTitle,mResendOTP;
    public AppCompatEditText et_one,et_two,et_three,et_four;
    TextView textview;
    StringBuilder sb=new StringBuilder();
    String mEmailId,mOTPNumber,mFailure_emailid;
    public static int resendOTP=0;
    public static int otpBackpress=0;


    String registeredMessage="Registered Successfully !!!",forgotMessage="Your Login Credentials has been mailed to your EmailId";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen);

        mOtpText=(TextView)findViewById(R.id.otp_text);
        mResendOTP=(TextView)findViewById(R.id.resend_otp);
        mOtp=(TextView)findViewById(R.id.otp);
        mVerify=(Button)findViewById(R.id.verify);
        et_one=(AppCompatEditText)findViewById(R.id.et_one);

        et_two=(AppCompatEditText)findViewById(R.id.et_two);

        et_three=(AppCompatEditText)findViewById(R.id.et_three);

        et_four=(AppCompatEditText)findViewById(R.id.et_four);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        ((ImageView) mToolbar.findViewById(R.id.img))
                .setVisibility(View.GONE);

        mToolbarTitle= (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("OTP");
        mToolbarTitle.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        mEmailId = bundle.getString("email_id");


        setSupportActionBar(mToolbar);
        setFont();


        mResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable(OTP_Activity.this))
                {
                    resendOTP=1;
                    getResendOTP();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                }

            }
        });
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_one.getText().toString().equals("")||et_two.getText().toString().equals("")||et_three.getText().toString().equals("")||et_four.getText().toString().equals(""))
                {
                    Toast.makeText(OTP_Activity.this, "Please enter valid the OTP ", Toast.LENGTH_LONG).show();

                }
                else
                {

                    if(newRegister==1)
                    {
                        mOTPNumber=et_one.getText().toString()+et_two.getText().toString()+et_three.getText().toString()+et_four.getText().toString();
                        Log.e("otp",mOTPNumber);
                        if(isNetworkAvailable(OTP_Activity.this))
                        {
                            if(resendOTP==0)
                            {
                                if(mOTPNumber.equals("1234"))
                                {
                                    getVerifyOTP();


                                }else
                                {
                                    Toast.makeText(OTP_Activity.this, "Incorrect OTP ", Toast.LENGTH_LONG).show();

                                }
                            }
                            else if(resendOTP==1)
                            {
                                if(mOTPNumber.equals("5678"))
                                {
                                    getVerifyOTP();

                                }else
                                {
                                    Toast.makeText(OTP_Activity.this, "Incorrect OTP ", Toast.LENGTH_LONG).show();

                                }
                            }


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                        }

                    }

                    else  if((forgotPassword==1))
                    {

                        mOTPNumber=et_one.getText().toString()+et_two.getText().toString()+et_three.getText().toString()+et_four.getText().toString();
                        Log.e("otp",mOTPNumber);
                        if(isNetworkAvailable(OTP_Activity.this))
                        {
                            getVerifyOTP();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                        }

                    }
                   /* else if(code_failure==1)
                    {
                        if(isNetworkAvailable(OTP_Activity.this))
                        {
                            mOTPNumber=et_one.getText().toString()+et_two.getText().toString()+et_three.getText().toString()+et_four.getText().toString()+et_five.getText().toString()+et_six.getText().toString();
                            Log.e("otp",mOTPNumber);
                            Bundle bundle = getIntent().getExtras();

//Extract the data…
                            mFailure_emailid = bundle.getString("email");
                            Log.i("mFailure_emailid", String.valueOf(mFailure_emailid));
                            getVerifyOTP();


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                        }

                    }
                    else {

                        if(login_credentials.getmRole().equals("Customer")||login_credentials.getmRole().equals("customer"))
                        {

                            Intent intent=new Intent(OTP_Activity.this,Navigation_Tab_Activity.class);
                            startActivity(intent);
                        }
                        else

                        {
                            Intent intent=new Intent(OTP_Activity.this,Sign_In_Home_activity.class);
                            startActivity(intent);

                        }

                    }*/
                }

            }
        });



        et_one.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&et_one.length()==1)
                {
                    sb.append(s);
                    et_one.clearFocus();
                    et_two.requestFocus();
                    et_two.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    et_one.requestFocus();
                }

            }
        });

        et_two.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&et_two.length()==1)
                {
                    sb.append(s);
                    et_two.clearFocus();
                    et_three.requestFocus();
                    et_three.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    et_two.requestFocus();
                }

            }
        });

        et_three.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&et_three.length()==1)
                {
                    sb.append(s);
                    et_three.clearFocus();
                    et_four.requestFocus();
                    et_four.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    et_three.requestFocus();
                }

            }
        });

        et_four.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&et_four.length()==1)
                {
                    sb.append(s);


                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if(sb.length()==1)
                {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {

                    et_four.requestFocus();
                }

            }
        });



    }


    private void getVerifyOTP() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
/*
        if(forgotPassword==1)
        {
          //  params.put("email_id", login_credentials.getmResetEmailId());
            params.put("otp", mOTPNumber);

         //   Log.e("email_id", login_credentials.getmResetEmailId());
            Log.e("otp ",mOTPNumber);
        }*/
       /* else if(code_failure==1)
        {
            params.put("email_id", mFailure_emailid);
            params.put("otp", mOTPNumber);

            Log.e("code_email", mFailure_emailid);
            Log.e("otp ",mOTPNumber);
        }*/
    //    else
     //   {

            params.put("email_id", mEmailId);
            params.put("otp", mOTPNumber);
        Log.e("json",params.toString());


      //  }



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.VERIFY_OTP, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("verify_otp", String.valueOf(object));


                            String status=object.getString("status");
                            if(status.equals("Success"))
                            {


                              /*  if((forgotPassword==1))
                                {
                                    Intent intent=new Intent(OTP_Activity.this,Reset_Password_Activity.class);
                                    startActivity(intent);

                                }
                                else*/ if(newRegister==1)
                                {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OTP_Activity.this);
                                    alertDialogBuilder.setMessage("Registered Successfully !!!");
                                    alertDialogBuilder.setCancelable(false);

                                    alertDialogBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    newRegister=0;
                                                    resendOTP=0;
                                                    otpBackpress=1;

                                                    Intent intent=new Intent(OTP_Activity.this,Login_Activity.class);
                                                    intent.addCategory(Intent.CATEGORY_HOME);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    finish();


                                                }
                                            });


                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));
                                    ;
                                }
                                /*if(code_failure==1)
                                {
                                    Intent intent=new Intent(OTP_Activity.this,SignIn_Activity.class);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                }*/
                            }
                            else
                            {
                                String reason=object.getString("reason");
                                Toast.makeText(OTP_Activity.this, reason ,Toast.LENGTH_LONG).show();
                               /* if(code_failure==1)
                                {

                                    Intent intent=new Intent(OTP_Activity.this,Sign_In_Home_activity.class);
                                    startActivity(intent);

                                }*/
                            }




                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("verify_otp_error", "error" + volleyError);
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(OTP_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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



    private void getResendOTP() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
        tv1.setTextSize(20);
        tv1.setTypeface(mDynoRegular);
        tv1.setText("Please wait ...");

      /*  if(login_credentials.getmForgotResendOTp()==1)
        {
            params.put("email_id", login_credentials.getmResetEmailId());

            Log.e("email_id", login_credentials.getmResetEmailId());
        }*/
       /* else if(code_failure==1)
        {
            Bundle bundle = getIntent().getExtras();

            mFailure_emailid = bundle.getString("email");
            Log.i("mFailure_emailid", String.valueOf(mFailure_emailid));
            params.put("email_id", mFailure_emailid);
            Log.e("email_id",mFailure_emailid);
        }*/
     /*  else

        {*/
            params.put("email_id", mEmailId);
            Log.e("email_id",mEmailId);
     //   }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.RESEND_OTP, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("resend_otp", String.valueOf(object));


                            String status=object.getString("status");
                            if(status.equals("Success"))
                            {
                                progressDialog.cancel();
                            }
                            else
                            {
                                String reason=object.getString("reason");
                                Toast.makeText(OTP_Activity.this, reason ,Toast.LENGTH_LONG).show();

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
                    Toast.makeText(OTP_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your Login Credentials has been mailed to your Email id");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        forgotPassword=0;

                       /* Intent intent=new Intent(OTP_Activity.this,SignIn_Activity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();*/
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_bg));
    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mDynoRegular1 = Typeface.createFromAsset(getAssets(), "font/Roboto_Bold.ttf");


        mOtpText.setTypeface(mDynoRegular1);
        et_one.setTypeface(mDynoRegular);
        et_three.setTypeface(mDynoRegular);
        et_two.setTypeface(mDynoRegular);
        et_four.setTypeface(mDynoRegular);
        mResendOTP.setTypeface(mDynoRegular1);

        mOtp.setTypeface(mDynoRegular);
        mVerify.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);


    }
    @Override
    public void onBackPressed() {


    }
}