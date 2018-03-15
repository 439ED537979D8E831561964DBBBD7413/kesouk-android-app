package com.tstl.kesouk.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tstl.kesouk.R;

/**
 * Created by user on 07-Mar-18.
 */

public class CheckoutScreen2 extends AppCompatActivity {
    Button mContinue;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView toolbarTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_screen2);
        mContinue=(Button)findViewById(R.id.continue_btn);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Slots kshs - 2814.00");
        setSupportActionBar(mToolbar);
        setFont();
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CheckoutScreen2.this,CheckoutScreen3.class);
                startActivity(intent);
            }
        });
    }
    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto_Regular.ttf");

        toolbarTitle.setTypeface(mDynoRegular);
    }
}
