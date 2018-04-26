package com.tstl.kesouk.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.R;

import static com.tstl.kesouk.Activity.CheckoutScreen3.order_id;

/**
 * Created by user on 16-Apr-18.
 */

public class ThankyouScreen_Activity extends AppCompatActivity {
    Button mContinue;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView toolbarTitle,carttext,address,date,time,charge;
    ImageView img;
    DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_screen);
        mContinue = (Button) findViewById(R.id.continue_btn);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        carttext = (TextView) findViewById(R.id.defaultaddr);
        toolbarTitle.setText("THANKYOU");
        img = (ImageView) findViewById(R.id.img);
        img.setVisibility(View.GONE);
        setSupportActionBar(mToolbar);
        db = new DB(ThankyouScreen_Activity.this);
        setFont();
        carttext.setText("Thankyou for placing your order! Your Order No. is "+order_id);
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankyouScreen_Activity.this, TabMain_Activity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto_Regular.ttf");

        toolbarTitle.setTypeface(mDynoRegular);
        carttext.setTypeface(mDynoRegular);
    }
}
