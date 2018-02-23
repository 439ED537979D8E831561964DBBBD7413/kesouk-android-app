package com.tstl.kesouk.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tstl.kesouk.Activity.OTP_Activity.otpBackpress;
import static com.tstl.kesouk.Activity.SplashActivity.isNetworkAvailable;
import static com.tstl.kesouk.Activity.TabMain_Activity.home_signin_backpress;
import static com.tstl.kesouk.Activity.TabMain_Activity.logout_backpress;
import static com.tstl.kesouk.Fragments.Basket_Fragment.basket_home_signin_backpress;
import static com.tstl.kesouk.Fragments.Basket_Fragment.basket_logout_backpress;
import static com.tstl.kesouk.Fragments.Basket_Fragment.cart_backpress;
import static com.tstl.kesouk.Fragments.Favorites_Fragment.fav_home_signin_backpress;
import static com.tstl.kesouk.Fragments.Favorites_Fragment.fav_logout_backpress;


public class Login_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, ResultCallback<People.LoadPeopleResult> {
    private LoginButton mbtnFacebook;
    SignInButton mbtnGoogle;
    CallbackManager callbackManager;
    private String userFBEmailId, userFBAccessToken;
    GoogleApiClient google_api_client;
    GoogleApiAvailability google_api_availability;
    private boolean is_signInBtn_clicked;
    private ConnectionResult connection_result;
    private boolean is_intent_inprogress;
    private static final int SIGN_IN_CODE = 0;
    private int request_code;
    public static final String SCOPES = "https://www.googleapis.com/auth/plus.login "
            + "https://www.googleapis.com/auth/drive.file";
    private String mUserGAccessToken, mUserGEmailId;
    EditText mEmailId, mPassword;
    AppCompatCheckBox checkBox;
    TextView mForgotPasswd, login_with_text, remeber_me1, first_shopping;
    Button mLogin, mRegister;
    public static int forgotPassword = 0;
    DB db;
    Typeface mDynoRegular, mDynoRegular1;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    Button fb, google;
    LoginManager loginManager;
    String G_fname, G_lname, G_email, G_access_token, G_imageurl, F_name, F_token, F_email;
    boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        loginManager = LoginManager.getInstance();
        setContentView(R.layout.login_activity);
        buildNewGoogleApiClient();
        callbackManager = CallbackManager.Factory.create();
        //  mbtnFacebook = (LoginButton) findViewById(R.id.login_button);
        fb = (Button) findViewById(R.id.fb_button);
        google = (Button) findViewById(R.id.g_button);
        // mbtnGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        // mbtnGoogle.setSize(SignInButton.SIZE_STANDARD);
        //  mbtnGoogle.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});
        //  mbtnFacebook.setReadPermissions("public_profile email");
        // mbtnFacebook.setReadPermissions("public_profile");
        // mbtnFacebook.setReadPermissions("email");


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this, Arrays.asList("email", "public_profile"));

                // LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this, Arrays.asList("public_profile","email"));


            }
        });


        mEmailId = (EditText) findViewById(R.id.email_edittxt);
        mPassword = (EditText) findViewById(R.id.passwd_text);
        mLogin = (Button) findViewById(R.id.login_btn);
        mRegister = (Button) findViewById(R.id.register_btn);
        checkBox = (AppCompatCheckBox) findViewById(R.id.checkbox1);
        mForgotPasswd = (TextView) findViewById(R.id.forgot_pass1);
        login_with_text = (TextView) findViewById(R.id.login_with_text);
        remeber_me1 = (TextView) findViewById(R.id.remeber_me1);
        first_shopping = (TextView) findViewById(R.id.first_shopping);
        setFont();
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?


                if (((CheckBox) v).isChecked()) {

                    isChecked = true;


                } else {
                    isChecked = false;

                }

            }
        });

        if (db.getRememberEmail().size() == 0) {


        } else {
            String a = db.getRememberEmail().get(0);
            String b = db.getRemeberPasswd().get(0);

            mEmailId.setText(a);
            mPassword.setText(b);
            checkBox.setChecked(true);
        }
        mForgotPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* forgotPassword=1;
                Intent intent = new Intent(Login_Activity.this, OTP_Activity.class);
                startActivity(intent);*/

            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmailId.getText().toString().equals("")) {
                    mEmailId.setError("Please enter your Email Id");
                } else if (mPassword.getText().toString().equals("")) {
                    Toast.makeText(Login_Activity.this, "Please enter the Password", Toast.LENGTH_LONG).show();

                } else if ((mEmailId.getText().toString().equals("")) && (mPassword.getText().toString().equals(""))) {
                    mEmailId.setError("Please enter your Email Id");
                    Toast.makeText(Login_Activity.this, "Please enter the Password", Toast.LENGTH_LONG).show();
                } else if (isNetworkAvailable(Login_Activity.this)) {
                    if (isValidMail(mEmailId.getText().toString())) {

                        if (isChecked) {
                            Log.e("chekbox", "true");
                            String email = mEmailId.getText().toString();
                            String passwd = mPassword.getText().toString();
                            db.insertRemEmail(email);
                            db.insertRemPasswd(passwd);
                            Log.e("email", db.insertRemEmail(email));
                            Log.e("pass", db.insertRemPasswd(passwd));

                        } else {

                            db.remove_rem_email();
                            db.remove_rem_passwd();
                        }

                        getLoginAsyncTask();
                    } else {
                        mEmailId.setError("Please enter valid credentials");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
                }


            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            getRequestData();

        }
        getRequestData();
        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("Success", "Login");
                        AccessToken.getCurrentAccessToken().getToken();
                        if (AccessToken.getCurrentAccessToken() != null) {
                            if (isNetworkAvailable(Login_Activity.this)) {
                                getRequestData1();
                                Log.e("Token", loginResult.getAccessToken().getToken() + "--------");
                                F_token = loginResult.getAccessToken().getToken();


                                //getFBLoginRequest();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();

                            }
                        }

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login_Activity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login_Activity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
      /*  mbtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    if (isNetworkAvailable(Login_Activity.this)) {
                        getRequestData();
                        Log.e("Accesstoken", String.valueOf(AccessToken.getCurrentAccessToken().getToken()));
                        userFBAccessToken = String.valueOf(AccessToken.getCurrentAccessToken().getToken());

                        // getFBLoginRequest();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
        mbtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken.getCurrentAccessToken().getToken();
                if (AccessToken.getCurrentAccessToken() != null) {
                    if (isNetworkAvailable(Login_Activity.this)) {
                        getRequestData();
                        Log.e("Token", "--------" + loginResult.getAccessToken().getToken());
                        userFBAccessToken = loginResult.getAccessToken().getToken();
                        Intent intent=new Intent(Login_Activity.this,Home_Activity.class);
                        startActivity(intent);


                        //getFBLoginRequest();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });*/
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onclick", "g+");
                //mUserGEmailId = Plus.AccountApi.getAccountName(google_api_client);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                                REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    } else {
                        gPlusSignIn();

                    }
                } else {
                    gPlusSignIn();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        if (requestCode == SIGN_IN_CODE) {
            request_code = requestCode;
            if (resultCode != RESULT_OK) {
                is_signInBtn_clicked = false;

            }

            is_intent_inprogress = false;

            if (!google_api_client.isConnecting()) {
                google_api_client.connect();
            }

        }

    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mDynoRegular1 = Typeface.createFromAsset(getAssets(), "font/Roboto_Bold.ttf");

        mEmailId.setTypeface(mDynoRegular);
        mPassword.setTypeface(mDynoRegular);
        mLogin.setTypeface(mDynoRegular);
        mRegister.setTypeface(mDynoRegular);
        mForgotPasswd.setTypeface(mDynoRegular);
        login_with_text.setTypeface(mDynoRegular1);

        remeber_me1.setTypeface(mDynoRegular);
        first_shopping.setTypeface(mDynoRegular);


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

    private void getLoginAsyncTask() {

        String mailId = mEmailId.getText().toString();
        String replacedMail = mailId.replace("@", "%40");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
        tv1.setTextSize(20);
        tv1.setTypeface(mDynoRegular);
        tv1.setText("Please wait ...");

        final String string = " http://139.59.19.12.xip.io/kesouk/api/loginauth?&password=" + mPassword.getText().toString() + "&username=" + mailId;

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... params) {

                JSONObject response = null;
                HttpURLConnection urlConnection = null;


                try {
                    URL url = new URL(string);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    int responseCode = urlConnection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        try {
                            response = new JSONObject(readStream(urlConnection.getInputStream()));
                            String cookie = urlConnection.getHeaderField("Set-Cookie");
                            String url_session = cookie.substring(0, cookie.indexOf(";"));
                            // login_credentials.setCookie(url_session);
                            //db.insertCookie(url_session);
                            //Log.i("Msg", url_session);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return response;
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                Log.e("get login", String.valueOf(response));

                try {

                    JSONObject JObject = new JSONObject(String.valueOf(response));

                    String status = JObject.getString("status");

                    if (status.equals("Success")) {

                        progressDialog.dismiss();
                        String role = JObject.getString("role");
                        String reason = JObject.getString("reason");

                        JSONObject jsonObject = JObject.getJSONObject("userdetails");
                        String cust_id = jsonObject.getString("id");

                        int id = jsonObject.getInt("id");
                        db.insertlogin(id);
                        getCarttoCustomer();

                        if (!TextUtils.isEmpty(role)) {

                            if (role.equals("Customer") || role.equals("customer")) {

                                Intent intent2 = new Intent(Login_Activity.this, TabMain_Activity.class);
                                intent2.addCategory(Intent.CATEGORY_HOME);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                finish();
                            } /*else {
                                Intent intent = new Intent(Login_Activity.this, Sign_In_Home_activity.class);
                                startActivity(intent);
                                finish();
                            }*/

                        }


                    } else if (status.equals("Failure")) {
                       /* if (JObject.has("code")) {
                            code = JObject.getInt("code");
                            Log.e("code", String.valueOf(code));
                        }
                        Log.e("fail_code", String.valueOf(code));
                        if (code == 3) {
                            code_failure = 1;


                            Intent intent = new Intent(SignIn_Activity.this, OTP_Activity.class);


//Create the bundle
                            Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                            bundle.putString("email", mEmailId.getText().toString());
//Add the bundle to the intent
                            intent.putExtras(bundle);
                            startActivity(intent);


                        }*/
                        db.remove_rem_email();
                        db.remove_rem_passwd();
                        String reason = JObject.getString("reason");
                        Toast.makeText(Login_Activity.this, reason, Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.execute();
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private void getFbLogin() {
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

        params.put("email", F_email);
        params.put("name", F_name);
        params.put("token", F_token);

    /*    params.put("email", "kavin1944@gmail.com");
        params.put("name", "Kavin Kutty");
        params.put("token", "EAAcGMA1bjaUBANZA11xAQkddrCFzqlPiCr1qPOZAbTEI1zBojbRuJniIByOJEjuFkE5cejCICTOqq3yIFXug4XAk0sZCxFIJSKxSRbk7syZBwXZCMYygKfKXmTwhHAxPG3JXpfGti4yu2chzHKfuacdjFCc5PtXIsZATp9swQ4KZAOYSBYQTqLH");
       */
        Log.e("json", params.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.FB_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("fb_login", String.valueOf(object));


                            String status = object.getString("status");
                            if (status.equals("Success")) {
                                db.insertlogin(48);
                                progressDialog.cancel();


                                Intent intent = new Intent(Login_Activity.this, TabMain_Activity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                String reason = object.getString("reason");
                                Toast.makeText(Login_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                    Toast.makeText(Login_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
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

    private void getGoogleLogin() {
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

        params.put("email", G_email);
        params.put("first_name", G_fname);
        params.put("last_name", G_lname);
        params.put("imageurl", G_imageurl);
        params.put("token", G_access_token);

        Log.e("json", params.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.GOOGLE_LOGIN, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("google_login", String.valueOf(object));


                            String status = object.getString("status");
                            if (status.equals("Success")) {
                                db.insertlogin(48);
                                //  db.insertRole(role);
                                progressDialog.cancel();

                                Intent intent = new Intent(Login_Activity.this, TabMain_Activity.class);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                String reason = object.getString("reason");
                                Toast.makeText(Login_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                    Toast.makeText(Login_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
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

    public void getRequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Id :</b> " + json.getInt("id") + "<br><br><b>Profile link :</b> " + json.getString("link");
                        Log.e("details", text);
                        F_email = json.getString("email");
                        F_name = json.getString("name");

                        Log.e("id", String.valueOf(json.getInt("id")));
                        Log.e("id1", String.valueOf(json.getLong("id")));


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void getRequestData1() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Id :</b> " + json.getInt("id") + "<br><br><b>Profile link :</b> " + json.getString("link");
                        Log.e("details", text);
                        F_email = json.getString("email");
                        F_name = json.getString("name");

                        Log.e("id", String.valueOf(json.getInt("id")));
                        Log.e("id1", String.valueOf(json.getLong("id")));


                    }
                    getFbLogin();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // AppEventsLogger.activateApp(this);
        if (google_api_client.isConnected()) {
            google_api_client.connect();
        }
        if (loginManager != null) {
            loginManager.logOut();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

        // AppEventsLogger.activateApp(this);
    }

    protected void onStart() {
        super.onStart();
        google_api_client.connect();

    }

    protected void onStop() {
        super.onStop();
        if (google_api_client.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(google_api_client);
            google_api_client.disconnect();
        }

        //Facebook
        if (loginManager != null) {
            loginManager.logOut();
        }
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        is_signInBtn_clicked = false;
        // Get user's information and set it into the layout
        getProfileInfo();

        // Update the UI after signin
        // changeUI(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        google_api_client.connect();
        //changeUI(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
       /* if (!result.hasResolution()) {
            google_api_availability.getErrorDialog(this, result.getErrorCode(),request_code).show();
            return;
        }

        if (!is_intent_inprogress) {

            connection_result = result;

            if (is_signInBtn_clicked) {

                resolveSignInError();
            }
        }*/
        // Always assign result first
        connection_result = result;

        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!is_intent_inprogress) {

            if (is_signInBtn_clicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }


    private void gPlusSignIn() {
        if (!google_api_client.isConnecting()) {
            Log.e("user connected", "connected");
            is_signInBtn_clicked = true;
            google_api_client.connect();
            // resolveSignInError();

            if (isNetworkAvailable(Login_Activity.this)) {
                getProfileInfo();


            } else {
                Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void gPlusRevokeAccess() {
        if (google_api_client.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(google_api_client);
            Plus.AccountApi.revokeAccessAndDisconnect(google_api_client)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.d("MainActivity", "User access revoked!");
                            buildNewGoogleApiClient();
                            google_api_client.connect();
                            changeUI(false);
                        }

                    });
        }
    }

    private void resolveSignInError() {
        if (connection_result.hasResolution()) {
            try {
                is_intent_inprogress = true;
                connection_result.startResolutionForResult(this, SIGN_IN_CODE);
                Log.e("resolve error", "sign in error resolved");
            } catch (IntentSender.SendIntentException e) {
                is_intent_inprogress = false;
                google_api_client.connect();
            }
        }
    }

    private void gPlusSignOut() {
        if (google_api_client.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(google_api_client);
            google_api_client.disconnect();
            google_api_client.connect();
            changeUI(false);
        }
    }

    private void changeUI(boolean signedIn) {
        if (signedIn) {
            // findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {

            // findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    private void getProfileInfo() {

        try {

            if (Plus.PeopleApi.getCurrentPerson(google_api_client) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(google_api_client);
                setPersonalInfo(currentPerson);

            } else {
               /* Toast.makeText(getApplicationContext(),
                        "No Personal info mention", Toast.LENGTH_LONG).show();*/

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    //String email = Plus.AccountApi.getAccountName(google_api_client);
                    // Log.e("email",email);
                    gPlusSignIn();
                } else {
                    // Permission Denied
                    Log.e("email", "pErmsonDenied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setPersonalInfo(Person currentPerson) {

        String personName = currentPerson.getDisplayName();
        //Log.e("id",currentPerson.getId());
        G_fname = personName.substring(0, personName.indexOf(" "));
        G_lname = personName.substring(personName.indexOf(" "), personName.length());
        Log.e("names", G_fname);
        Log.e("names", G_lname);


        Log.e("name", personName);
        // Log.e("email", mUserGEmailId);

        G_imageurl = currentPerson.getImage().getUrl();
        G_imageurl = G_imageurl.substring(0, G_imageurl.indexOf("sz=") + 3) + "200";
        Log.e("prephoto", G_imageurl);
        int personGender = currentPerson.getGender();
        String personID = currentPerson.getId();
        String personGenderString;
        if (personGender == Person.Gender.MALE) {
            personGenderString = "Male";
        } else if (personGender == Person.Gender.FEMALE) {
            personGenderString = "Female";
        } else {
            personGenderString = "Other";
        }
        String personGooglePlusProfile = currentPerson.getUrl();
        Log.e("Gplus", personGooglePlusProfile);
        G_email = Plus.AccountApi.getAccountName(google_api_client);
        Log.e("email", G_email);


        new RetrieveTokenTask().execute();
        //  Toast.makeText(this, "Person information is shown!", Toast.LENGTH_LONG).show();
    }

    private void buildNewGoogleApiClient() {

        google_api_client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
        google_api_client.connect();
        /*if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.GET_ACCOUNTS},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }*/
    }


    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String token = null;
            Bundle appActivities = new Bundle();
            appActivities.putString(
                    GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES,
                    String.valueOf(678687));
            try {
                token = GoogleAuthUtil.getToken(
                        Login_Activity.this, Plus.AccountApi.getAccountName(google_api_client)
                        ,
                        "oauth2:" + SCOPES, appActivities);
            } catch (IOException transientEx) {
                // Network or server error, try later
                Log.e("gfd", transientEx.toString());
            } catch (UserRecoverableAuthException e) {
                // Recover (with e.getIntent())
                startActivityForResult(e.getIntent(), 545);
                Log.e("fdg", e.toString());
            } catch (GoogleAuthException authEx) {
                // The call is not ever expected to succeed
                // assuming you have already verified that
                // Google Play services is installed.
                Log.e("fd", authEx.toString());
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            G_access_token = token;
            Log.e("hj", "Access token retrieved:" + G_access_token);
            getGoogleLogin();
        }
    }

    @Override
    public void onBackPressed() {
        if (otpBackpress == 1) {
            Log.e("otp", "bcakpress");
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();


        } else if (logout_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();

        }
        else if (fav_logout_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();

        }
        else if (fav_home_signin_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();

        }
        else if (basket_logout_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();

        }
        else if (basket_home_signin_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();

        }
        else if (cart_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();
            Log.e("cart_backpress", "cart_backpress");
        } else if (home_signin_backpress == 1) {
            Intent intent2 = new Intent(Login_Activity.this, Login_Register_Activity.class);
            intent2.addCategory(Intent.CATEGORY_HOME);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            finish();
            Log.e("cart_backpress", "cart_backpress");
        } else {
            Log.e("else", "bcakpress");
            super.onBackPressed();
        }

    }


    private void getCarttoCustomer() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();


        JSONObject object = new JSONObject();
        try {
            if (db.getAllLogin().size() == 1) {
                object.put("customer_id", String.valueOf(db.getAllLogin().get(0)));

            } else if (db.getAllLogin().size() == 0) {
                object.put("customer_id", "0");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            if (db.getAllData().size() == 0) {
                object.put("localcart", "null");
            } else {
                object.put("localcart", new JSONArray(db.getAllData()));
                db.removeAll();

                Log.e("fetch_removed_data", String.valueOf((db.getAllData())));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json", object.toString());
        Log.e("db", db.getAllLogin().get(0));
        JsonObjectRequest jsonObjReq = null;
        try {
            jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.CARTTOCUSTOMER, new JSONObject(object.toString()),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject object) {
                            try {
                                Log.e("cart_cust", String.valueOf(object));


                                String status = object.getString("status");
                                if (status.equals("Success")) {

                                } else {
                                    String reason = object.getString("reason");
                                    Toast.makeText(Login_Activity.this, reason, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(Login_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                    } else
                        //Toast.makeText(getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
