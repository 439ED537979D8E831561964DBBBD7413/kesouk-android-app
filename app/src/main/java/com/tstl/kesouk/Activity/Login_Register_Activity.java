package com.tstl.kesouk.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.R;

/**
 * Created by user on 10-Jan-18.
 */

public class Login_Register_Activity extends AppCompatActivity {
    Button mLogin, mRegister;
    TextView mSkip;
    private Typeface mDynoRegular;
    private TextView kesouke, kenya_premium_market;
    DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
        db = new DB(this);
        mLogin = (Button) findViewById(R.id.login_btn);
        mRegister = (Button) findViewById(R.id.reg_btn);
        mSkip = (TextView) findViewById(R.id.skip);
        kesouke = (TextView) findViewById(R.id.kesouk);
        kenya_premium_market = (TextView) findViewById(R.id.kenya_market);
        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mLogin.setTypeface(mDynoRegular);
        mRegister.setTypeface(mDynoRegular);
        mSkip.setTypeface(mDynoRegular);
        kesouke.setTypeface(mDynoRegular);
        kenya_premium_market.setTypeface(mDynoRegular);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("db", String.valueOf(db.getRememberEmail()));

/*

                Intent intent=new Intent(Login_Register_Activity.this,Login_Activity.class);
                startActivity(intent);
                finish();
*/
                db.removeLogin();
                Intent intent1 = new Intent(Login_Register_Activity.this, Login_Activity.class);
                intent1.addCategory(Intent.CATEGORY_HOME);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                //finish();


            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeLogin();
                Intent intent = new Intent(Login_Register_Activity.this, Register_Activity.class);
                startActivity(intent);

            }
        });
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Login_Register_Activity.this, TabMain_Activity.class);
                startActivity(intent);

            }
        });
        setFont();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");


        mLogin.setTypeface(mDynoRegular);
        mRegister.setTypeface(mDynoRegular);
        mSkip.setTypeface(mDynoRegular);
        kesouke.setTypeface(mDynoRegular);

        kenya_premium_market.setTypeface(mDynoRegular);
    }
}
