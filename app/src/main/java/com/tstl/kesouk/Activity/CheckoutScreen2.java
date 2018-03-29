package com.tstl.kesouk.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tstl.kesouk.Model.Login_Credentials;
import com.tstl.kesouk.R;

import java.util.ArrayList;

/**
 * Created by user on 07-Mar-18.
 */

public class CheckoutScreen2 extends AppCompatActivity {
    Button mContinue;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    RecyclerView recycler_view;
    private ImageView backBtn,settings,search;
    public ArrayList<Login_Credentials> login_credentials = null;
    Login_Credentials login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_address_activity);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        // recycler_view.setNestedScrollingEnabled(false);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);


        setSupportActionBar(mToolbar);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("Slots - kshs 2814.00");
        mToolbarTitle.setVisibility(View.VISIBLE);
        backBtn = (ImageView) findViewById(R.id.img);


        settings = (ImageView) findViewById(R.id.settings);
        search = (ImageView) findViewById(R.id.search);
        search.setVisibility(View.GONE);

        setFont();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        setFont();
        login_credentials = new ArrayList<>();
        for(int i=0;i<10;i++) {
            login = new Login_Credentials();
            login.setFirstname("yahoo");
            login_credentials.add(login);

        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this, login_credentials);
        recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(adapter);

    }
    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto_Regular.ttf");

        mToolbarTitle.setTypeface(mDynoRegular);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context mContext;
        public ArrayList<Login_Credentials> login_credentialsArrayList;
        private Typeface mDynoRegular;
        public ArrayList<Float> subtotal_list = new ArrayList<>();
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_FOOTER = 1;
        private static final int TYPE_ITEM = 2;
        private int lastSelectedPosition = -1;
        public String address_id;
        int selectedPosition=-1;
        int onClick=0;

        public RecyclerViewAdapter(Context context, ArrayList<Login_Credentials> login_credentialses) {
            this.mContext = context;
            this.login_credentialsArrayList = login_credentialses;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                //Inflating recycle view item layout
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen2_item, parent, false);
                return new ItemViewHolder(itemView);
            } else if (viewType == TYPE_HEADER) {
                //Inflating header view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen2_header, parent, false);
                return new HeaderViewHolder(itemView);
            } else if (viewType == TYPE_FOOTER) {
                //Inflating footer view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen2_footer, parent, false);
                return new FooterViewHolder(itemView);
            } else return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;



            } else if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                FooterViewHolder footerHolder = (FooterViewHolder) holder;
                footerHolder.mContinue.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                        startActivity(intent);
                    }

                });



            } else if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

                if (position > 0) {
                    // itemViewHolder .relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_selector));


                    address_id = login_credentialsArrayList.get(position - 1).getAddId();

                    final Login_Credentials login_credentials = login_credentialsArrayList.get(position - 1);
                    itemViewHolder.text1.setText("5:30 - 6:30 am");
                    itemViewHolder.text2.setText("kshs 6.50");
                    itemViewHolder.text3.setText("kshs 7.50");
                    itemViewHolder.text4.setText("kshs 8.50");


                }
            }
        }

        @Override
        public int getItemCount() {

            return login_credentialsArrayList.size() + 2;//Add 2 more size to array list for Header and Fo        }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == login_credentialsArrayList.size() + 1) {
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView exp_title,std_title,express_text,previous,next,calendar,morning,tommorow,saturday,sunday;
            Button expressDelivery;

            public HeaderViewHolder(View view) {
                super(view);
                exp_title = (TextView) view.findViewById(R.id.express_title);
                std_title = (TextView) view.findViewById(R.id.standarddel_title);
                express_text = (TextView) view.findViewById(R.id.expressnote);
                previous = (TextView) view.findViewById(R.id.previous);
                next = (TextView) view.findViewById(R.id.next);
                calendar = (TextView) view.findViewById(R.id.calender);
                morning = (TextView) view.findViewById(R.id.mrng);
                tommorow = (TextView) view.findViewById(R.id.tomm);
                saturday = (TextView) view.findViewById(R.id.sat);
                sunday = (TextView) view.findViewById(R.id.sun);
                expressDelivery = (Button) view.findViewById(R.id.getexprssdel);


                exp_title.setTypeface(mDynoRegular);
                std_title.setTypeface(mDynoRegular);
                express_text.setTypeface(mDynoRegular);
                previous.setTypeface(mDynoRegular);
                next.setTypeface(mDynoRegular);
                calendar.setTypeface(mDynoRegular);
                morning.setTypeface(mDynoRegular);
                tommorow.setTypeface(mDynoRegular);
                saturday.setTypeface(mDynoRegular);
                sunday.setTypeface(mDynoRegular);
                expressDelivery.setTypeface(mDynoRegular);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {
            Button mContinue;

            public FooterViewHolder(View view) {
                super(view);
                mContinue = (Button) view.findViewById(R.id.continue_btn);
                mContinue.setTypeface(mDynoRegular);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView text1,text2,text3,text4;

            public ItemViewHolder( View view) {
                super(view);
                text1 = (TextView) view.findViewById(R.id.text1);
                text2 = (TextView) view.findViewById(R.id.text2);
                text3 = (TextView) view.findViewById(R.id.text3);
                text4 = (TextView) view.findViewById(R.id.text4);


                text1.setTypeface(mDynoRegular);
                text2.setTypeface(mDynoRegular);
                text3.setTypeface(mDynoRegular);
                text4.setTypeface(mDynoRegular);


            }

        }


    }

}
