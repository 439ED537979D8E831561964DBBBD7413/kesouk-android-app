package com.tstl.kesouk.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tstl.kesouk.Activity.SplashActivity.isNetworkAvailable;

/**
 * Created by user on 20-Feb-18.
 */

public class Profile_Activity extends AppCompatActivity {
    public AppCompatEditText mFirstName, mLastName, mMobile, mEmail, mDOB, mLandline;
    public TextInputLayout mFirstlayout, mLastLayout, mMobNumLayout, mEmailLayout, mDOBLayout, mLandLineLayout;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView mToolbarTitle, chekbox_text;
    DB db;
    ImageView backBtn, settings, search,calender_icon;
    CheckBox checkBox;
    Button mSave;
    LinearLayout dobLinearLayout;
    DatePickerDialog datePickerDialog;
    int month,day;
    public static int checkbox_value_email_promotions=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);


        calender_icon = (ImageView) findViewById(R.id.calender);


        setSupportActionBar(mToolbar);

      /*  ((TextView) mToolbar.findViewById(R.id.toolbar_title))
                .setText("Update Profile");
*/
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("Update Profile");
        mToolbarTitle.setVisibility(View.VISIBLE);
        db = new DB(this);
        backBtn = (ImageView) findViewById(R.id.img);
        settings = (ImageView) findViewById(R.id.settings);
        search = (ImageView) findViewById(R.id.search);
        search.setVisibility(View.GONE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        mFirstName = (AppCompatEditText) findViewById(R.id.input_firstname);
        mFirstlayout = (TextInputLayout) findViewById(R.id.input_layout_firstname);

        mLastName = (AppCompatEditText) findViewById(R.id.input_lastname1);
        mLastLayout = (TextInputLayout) findViewById(R.id.input_layout_lastname);

        mEmail = (AppCompatEditText) findViewById(R.id.input_email);
        mEmailLayout = (TextInputLayout) findViewById(R.id.input_layout_email);

        mDOB = (AppCompatEditText) findViewById(R.id.input_dob);
        mDOBLayout = (TextInputLayout) findViewById(R.id.input_layout_dob);

        mMobile = (AppCompatEditText) findViewById(R.id.input_mob);
        mMobNumLayout = (TextInputLayout) findViewById(R.id.input_layout_mob);


        mLandline = (AppCompatEditText) findViewById(R.id.input_landline);
        mLandLineLayout = (TextInputLayout) findViewById(R.id.input_layout_landline);


        checkBox = (AppCompatCheckBox) findViewById(R.id.checkbox1);
        chekbox_text = (TextView) findViewById(R.id.remeber_me1);

        mSave = (Button) findViewById(R.id.save);
        setFont();
        calender_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
               datePickerDialog = new DatePickerDialog(Profile_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Log.e("year", String.valueOf(year));
                                Log.e("date", String.valueOf(dayOfMonth));
                                Log.e("month", String.valueOf(monthOfYear));



                                if (monthOfYear < 9 &&dayOfMonth>9) {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "-" + "0"
                                            + (monthOfYear + 1) + "-" + dayOfMonth);
                                    Log.e("if","one");
                                } /*else {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "/"
                                            + (monthOfYear + 1) + "/" + dayOfMonth);
                                    Log.e("else","one");
                                }*/

                                if (dayOfMonth < 9 && (monthOfYear+1)>9) {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "-" + "0"
                                            + (dayOfMonth) + "-" + (monthOfYear+1));
                                    Log.e("if","two");
                                } /*else {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "/"
                                            + (dayOfMonth) + "/" + monthOfYear);
                                    Log.e("else","2");
                                }
*/
                                if (dayOfMonth < 9  && monthOfYear < 9) {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "-" + "0"
                                            + (dayOfMonth ) + "-" + "0"+ (monthOfYear+1));
                                    Log.e("if","3");
                                } /* else {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText(year + "/"
                                            + (dayOfMonth) + "/" + (monthOfYear+1));
                                    Log.e("else","3");
                                }*/

                                if (dayOfMonth >9  && (monthOfYear+1) > 9) {
                                    // set day of month , month and year value in the edit text
                                    mDOB.setText((year+1) + "-"
                                            + (dayOfMonth ) + "-" + (monthOfYear+1));
                                    Log.e("if","3");
                                }


                            }
                        }, mYear, mMonth, mDay);
             datePickerDialog.show();


            }
        });


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((!(mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() || mEmail.getText().toString().isEmpty() || mDOB.getText().toString().isEmpty() || mMobile.getText().toString().isEmpty())))

                {

                    if (isValidMail(mEmail.getText().toString())) {

                        if (isNetworkAvailable(Profile_Activity.this)) {
                            // finish();

                            if (checkBox.isChecked())
                            {
                                checkbox_value_email_promotions=1;
                            }else
                            {
                                checkbox_value_email_promotions=0;
                            }
                            UpdateProfile();
                        } else {
                            Toast.makeText(Profile_Activity.this, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

                        }


                    } else {
                        Toast.makeText(Profile_Activity.this, "Please enter valid Email id ", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(Profile_Activity.this, "Please enter all the details ", Toast.LENGTH_LONG).show();

                }

            }
        });

    }



    private void UpdateProfile() {
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

        params.put("customer_id", db.getAllLogin().get(0));
        params.put("first_name", mFirstName.getText().toString());
        params.put("last_name", mLastName.getText().toString());
        params.put("email", mEmail.getText().toString());
        params.put("dob", mDOB.getText().toString());
        params.put("mobilenumber", mMobile.getText().toString());
        params.put("landline", mLandline.getText().toString());
        params.put("promotions_and_offers_mail", String.valueOf(checkbox_value_email_promotions));
        Log.e("json",params.toString());



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.UPDATE_PROFILE, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("update_profile", String.valueOf(object));


                            String status=object.getString("status");
                            if(status.equals("Success")||status.equals("success"))
                            {
                                progressDialog.cancel();

                                String reason=object.getString("message");
                                Toast.makeText(Profile_Activity.this, reason ,Toast.LENGTH_LONG).show();
                                finish();

                            }
                            else
                            {
                                String reason=object.getString("message");
                                Toast.makeText(Profile_Activity.this, reason ,Toast.LENGTH_LONG).show();

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
                    Toast.makeText(Profile_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
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

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mFirstName.setTypeface(mDynoRegular);
        mFirstlayout.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);

        mLastName.setTypeface(mDynoRegular);
        mLastLayout.setTypeface(mDynoRegular);
        mMobile.setTypeface(mDynoRegular);
        mMobNumLayout.setTypeface(mDynoRegular);
        mDOB.setTypeface(mDynoRegular);
        mDOBLayout.setTypeface(mDynoRegular);
        mEmailLayout.setTypeface(mDynoRegular);
        mEmail.setTypeface(mDynoRegular);
        mLandline.setTypeface(mDynoRegular);
        mLandLineLayout.setTypeface(mDynoRegular);

        mSave.setTypeface(mDynoRegular);
        chekbox_text.setTypeface(mDynoRegular);

    }
}

