package com.tstl.kesouk.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 11-Jan-18.
 */

public class Home_Activity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mToolbarTitle,otp_text;
    public static int logout_backpress=0;
    Typeface mDynoRegular,mDynoRegular1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        ((ImageView) mToolbar.findViewById(R.id.img))
                .setVisibility(View.GONE);

        mToolbarTitle= (TextView) findViewById(R.id.toolbar_title);
        otp_text= (TextView) findViewById(R.id.otp_text);
        mToolbarTitle.setText("HOME");
        mToolbarTitle.setVisibility(View.VISIBLE);
        setFont();

    }
    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mDynoRegular1 = Typeface.createFromAsset(getAssets(), "font/Roboto_Bold.ttf");

        otp_text.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);


    }
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar tb = (Toolbar) findViewById(R.id.app_bar);
        tb.inflateMenu(R.menu.home);
        tb.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        return true;
    }
  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
          case R.id.logout:
              getLogout();
             /* Intent intent2 = new Intent(Home_Activity.this, Login_Activity.class);
              intent2.addCategory(Intent.CATEGORY_HOME);
              intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent2);
              finish();*/
              break;

          default:
              break;
      }

      return true;
  }

    private void getLogout() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.LOGOUT, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            Log.e("logout", String.valueOf(object));
                            String status = object.getString("status");
                            if (status.equals("Success")) {
                                logout_backpress=1;
                                Intent intent2 = new Intent(Home_Activity.this, Login_Activity.class);
                                intent2.addCategory(Intent.CATEGORY_HOME);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                finish();
                            } else {
                                String reason = object.getString("reason");
                                Toast.makeText(Home_Activity.this, reason, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Home_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
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

}
