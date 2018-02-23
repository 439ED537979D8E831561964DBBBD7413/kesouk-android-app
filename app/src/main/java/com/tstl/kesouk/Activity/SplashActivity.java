package com.tstl.kesouk.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;

import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by user on 05-Jan-18.
 */

public class SplashActivity extends ActionBarActivity {

    private static int SPLASH_TIME_OUT = 3000;
    DB db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
        //      SplashActivity.this));
        setContentView(R.layout.splash_activity);
        db=new DB(SplashActivity.this);
        Log.e("size", String.valueOf(db.getAllLogin().size()));
        // mOpenSansRegular = Typeface.createFromAsset(getAssets(), "font/OpenSans-Regular.ttf");
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this,Login_Register_Activity.class);
                startActivity(intent);

/*

                if((db.getAllLogin().size()==1))
                {
                    Intent intent1=new Intent(SplashActivity.this,Navigation_Tab_Activity.class);
                    startActivity(intent1);
                }
                else if(db.getAllLogin().size()==0)
                {
                    Log.e("size","else");
                    Intent intent = new Intent(SplashActivity.this,Login_Register_Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent1=new Intent(SplashActivity.this,Navigation_Tab_Activity.class);
                    startActivity(intent1);
                   // finish();
                }
*/


                printKeyHash();
            }
        }, SPLASH_TIME_OUT);

    }

   private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tstl.kesouk",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}