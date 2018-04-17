package com.tstl.kesouk.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Login_Credentials;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Fragments.Basket_Fragment.cartcount;
import static com.tstl.kesouk.Fragments.Basket_Fragment.expressCount;

/**
 * Created by user on 07-Mar-18.
 */

public class CheckoutScreen2 extends AppCompatActivity {
    Button mContinue;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    RecyclerView recycler_view;
    private ImageView backBtn, settings, search;
    public ArrayList<Login_Credentials> login_credentials = null;
    Login_Credentials login;
    int isPrev, isNext;
    String url, time, strDate;
    ArrayList<String> TimeSlotArraylist;
    ArrayList<String> TimeSlotIDArraylist;
    ArrayList<Integer> Day1List;
    ArrayList<Integer> Day2List;
    ArrayList<Integer> Day3List;
    ArrayList<Integer> Day4List;
    ArrayList<Integer> Day5List;
    ArrayList<Integer> Day6List;
    ArrayList<Integer> Day7List;
    String tomorrow, day4, day5, day6, day7, timeslotId, timeslotID;
    int adapterCheck;
    Dialog dialog;
    ImageView mClose;
    Button firstBtn, lastBtn;
    int firstClick = 0, lastClick = 0, continueCheck = 0, timeslotAmount;
    Date today1;
    public static String deliveryType;
    public static String passDate;
    public static int a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPrev = 0;
        isNext = 1;
        adapterCheck = 0;
        continueCheck = 0;
        setContentView(R.layout.my_address_activity);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        // recycler_view.setNestedScrollingEnabled(false);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);


        setSupportActionBar(mToolbar);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("ORDER SUMMARY");
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
        for (int i = 0; i < 10; i++) {
            login = new Login_Credentials();
            login.setFirstname("yahoo");
            login_credentials.add(login);

        }
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        time = simpleDateFormat.format(calander.getTime());

        Log.e("time", time);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        strDate = mdformat.format(calendar.getTime());
        Log.e("strDate", strDate);
// get a calendar instance, which defaults to "now"
        Calendar calendar1 = Calendar.getInstance();

        // get a date to represent "today"
        Date today = calendar1.getTime();
        Log.e("today:    ", String.valueOf(today));


        timeslot(strDate);

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
        private int lastSelectedPosition = -1, text2position = -1, text3position = -1, text4position = -1;
        public String address_id;
        int selectedPosition = -1;
        int onClick = 0;

        public RecyclerViewAdapter(Context context) {
            this.mContext = context;
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

            if (adapterCheck == 1) {
                // get a calendar instance, which defaults to "now"
                Calendar calendar2 = Calendar.getInstance();
                Calendar calendar1 = Calendar.getInstance();
                final SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");

                // add one day to the date/calendar
                calendar2.add(Calendar.DAY_OF_YEAR, 2);

                // now get "tomorrow"
                tomorrow = mdformat.format(calendar2.getTime());
                Log.e("tommDATe", String.valueOf(mdformat.format(calendar2.getTime())));
                // get a date to represent "today"
                today1 = calendar1.getTime();
                Log.e("today:    ", String.valueOf(today1));
                if (holder instanceof HeaderViewHolder) {
                    final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                    if (cartcount == expressCount) {
                        continueCheck = 1;
                    }
                    if (cartcount > expressCount && (expressCount != 0)) {
                        headerHolder.expressDelivery.setTextColor(Color.parseColor("#000000"));
                        headerHolder.expressDelivery.setBackgroundColor(Color.parseColor("#f2f238"));
                        headerHolder.expressDelivery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog = new Dialog(CheckoutScreen2.this);
                                dialog.setCancelable(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.screen2_popup);
                                mClose = (ImageView) dialog.findViewById(R.id.closeButton);
                                firstBtn = (Button) dialog.findViewById(R.id.firstBtn);
                                lastBtn = (Button) dialog.findViewById(R.id.last_btn);

                                dialog.show();

                                mClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                });
                                firstBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        firstClick = 1;
                                        lastClick = 0;
                                        continueCheck = 0;


                                    }
                                });
                                lastBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        firstClick = 0;
                                        lastClick = 1;
                                        continueCheck = 0;
                                    }
                                });

                            }
                        });

                    } else {
                        headerHolder.expressDelivery.setTextColor(Color.parseColor("#000000"));
                        headerHolder.expressDelivery.setBackgroundColor(Color.parseColor("#FFEFB5"));
                    }


                    headerHolder.express_text.setText("Get " + expressCount + " out of " + cartcount + " items within two hours.");
                    headerHolder.previous.setTextColor(Color.parseColor("#DCDCDC"));
                    headerHolder.next.setTextColor(Color.parseColor("#000000"));
                    headerHolder.previous.setText("<  Previous");
                    headerHolder.next.setText("Next  >");
                    headerHolder.morning.setText("Time Slots");
                    headerHolder.tommorow.setText("Today");
                    headerHolder.saturday.setText("Tommorow");
                    headerHolder.sunday.setText(tomorrow);


                    headerHolder.next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isNext == 1) {
                                headerHolder.previous.setTextColor(Color.parseColor("#000000"));
                                headerHolder.next.setTextColor(Color.parseColor("#DCDCDC"));
                                Log.e("load", "next data");
                                isPrev = 1;
                                isNext = 0;
                                adapterCheck = 2;
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this);
                                recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            } else {

                            }

                        }
                    });
                    headerHolder.previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isPrev == 0) {

                                headerHolder.previous.setTextColor(Color.parseColor("#DCDCDC"));
                                headerHolder.next.setTextColor(Color.parseColor("#000000"));


                            } else {
                                Log.e("load", "previous data");
                                isPrev = 0;
                                isNext = 1;
                                adapterCheck = 1;

                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this);
                                recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            }
                        }
                    });

                } else if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                    FooterViewHolder footerHolder = (FooterViewHolder) holder;
                    footerHolder.mContinue.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (continueCheck == 0) {
                                Toast.makeText(CheckoutScreen2.this, "Please select any TimeSlots", Toast.LENGTH_LONG).show();
                            } else {
                                if (expressCount == cartcount) {
                                    deliveryType = "Express";
                                    Calendar calendar3 = Calendar.getInstance();

                                    passDate = mdformat.format(calendar3.getTime());
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("time", "Within two hours");
                                    intent.putExtra("charge", "Kshs 50.00 (Express Delivery)");
                                    intent.putExtra("amount", "50");
                                    intent.putExtra("id", timeslotID);
                                    startActivity(intent);
                                } else if (expressCount == 0) {
                                    deliveryType = "Standard";
                                    a = timeslotAmount;
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("time", timeslotId);
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery)");
                                    intent.putExtra("amount", a);
                                    intent.putExtra("id", timeslotID);
                                    startActivity(intent);
                                } else if (cartcount > expressCount && (firstClick == 1)) {
                                    deliveryType = "Express";
                                    a = 50 + timeslotAmount;
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("amount", a);
                                    intent.putExtra("id", timeslotID);
                                    intent.putExtra("time", timeslotId + " (Standard Delivery)\nWithin two hours(Express Delivery)");
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery+Express Delivery)");
                                    startActivity(intent);
                                } else if (cartcount > expressCount && (lastClick == 1)) {
                                    a = timeslotAmount;
                                    deliveryType = "Standard";

                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("id", timeslotID);
                                    intent.putExtra("time", timeslotId);
                                    intent.putExtra("amount", a);
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery)");
                                    intent.putExtra("amount", a);
                                    startActivity(intent);
                                }

                            }


                        }

                    });


                } else if (holder instanceof ItemViewHolder) {
                    ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                    if (cartcount == expressCount) {
                        continueCheck = 1;
                    }

                    if (cartcount == expressCount) {
                        itemViewHolder.text1.setVisibility(View.GONE);
                        itemViewHolder.text2.setVisibility(View.GONE);
                        itemViewHolder.text3.setVisibility(View.GONE);
                        itemViewHolder.text4.setVisibility(View.GONE);
                    } else {
                        if (position > 0) {
                            // itemViewHolder .relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_selector));


                            // address_id = login_credentialsArrayList.get(position - 1).getAddId();

                            itemViewHolder.text1.setText(TimeSlotArraylist.get(position - 1));
                            itemViewHolder.text2.setText("Kshs " + String.valueOf(Day1List.get(position - 1)));
                            itemViewHolder.text3.setText("Kshs " + String.valueOf(Day2List.get(position - 1)));
                            itemViewHolder.text4.setText("Kshs " + String.valueOf(Day3List.get(position - 1)));

                            if (lastSelectedPosition == position) {
                                timeslotId = TimeSlotArraylist.get(position - 1);

                                Log.e("timeslotId", timeslotId);
                                lastSelectedPosition = -1;
                                itemViewHolder.text1.setBackground(getResources().getDrawable(R.drawable.border_listview));

                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }

                            } else {
                                itemViewHolder.text1.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }
                            if (text2position == position) {
                                text2position = -1;
                                timeslotAmount = Day1List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Calendar calendar3 = Calendar.getInstance();
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text2.setBackground(getResources().getDrawable(R.drawable.border_listview));
                                passDate = mdformat.format(calendar3.getTime());
                                Log.e("timeslotid", timeslotId);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text2.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }
                            if (text3position == position) {
                                text3position = -1;
                                timeslotAmount = Day2List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                Log.e("timeslotid", timeslotId);
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text3.setBackground(getResources().getDrawable(R.drawable.border_listview));

                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 1);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text3.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }
                            if (text4position == position) {
                                text4position = -1;
                                timeslotAmount = Day3List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                Log.e("timeslotid", timeslotId);
                                itemViewHolder.text4.setBackground(getResources().getDrawable(R.drawable.border_listview));

                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 2);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text4.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }

                        }
                    }

                }
            } else if (adapterCheck == 2) {

                // get a calendar instance, which defaults to "now"
                Calendar calendar7 = Calendar.getInstance();
                Calendar calendar4 = Calendar.getInstance();
                Calendar calendar5 = Calendar.getInstance();
                Calendar calendar6 = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");

                // add one day to the date/calendar
                calendar7.add(Calendar.DAY_OF_YEAR, 3);
                calendar4.add(Calendar.DAY_OF_YEAR, 4);
                calendar5.add(Calendar.DAY_OF_YEAR, 5);
                calendar6.add(Calendar.DAY_OF_YEAR, 6);


                // now get "tomorrow"
                day4 = mdformat.format(calendar7.getTime());
                day5 = mdformat.format(calendar4.getTime());
                day6 = mdformat.format(calendar5.getTime());
                day7 = mdformat.format(calendar6.getTime());


                if (holder instanceof HeaderViewHolder) {
                    final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                    Calendar calendar1 = Calendar.getInstance();
                    today1 = calendar1.getTime();
                    Log.e("today:    ", String.valueOf(today1));
                    if (cartcount == expressCount) {
                        continueCheck = 1;
                    }
                    if (cartcount > expressCount && (expressCount != 0)) {
                        headerHolder.expressDelivery.setTextColor(Color.parseColor("#000000"));
                        headerHolder.expressDelivery.setBackgroundColor(Color.parseColor("#f2f238"));
                        headerHolder.expressDelivery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog = new Dialog(CheckoutScreen2.this);
                                dialog.setCancelable(true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.screen2_popup);
                                mClose = (ImageView) dialog.findViewById(R.id.closeButton);
                                firstBtn = (Button) dialog.findViewById(R.id.firstBtn);
                                lastBtn = (Button) dialog.findViewById(R.id.last_btn);

                                dialog.show();

                                mClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                });
                                firstBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        firstClick = 1;
                                        lastClick = 0;
                                        continueCheck = 0;

                                    }
                                });
                                lastBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        firstClick = 0;
                                        lastClick = 1;
                                        continueCheck = 0;
                                    }
                                });

                            }
                        });

                    } else {
                        headerHolder.expressDelivery.setTextColor(Color.parseColor("#000000"));
                        headerHolder.expressDelivery.setBackgroundColor(Color.parseColor("#FFEFB5"));
                    }


                    headerHolder.express_text.setText("Get " + expressCount + " out of " + cartcount + " items within two hours.");
                        /*headerHolder.previous.setTextColor(Color.parseColor("#DCDCDC"));
                        headerHolder.next.setTextColor(Color.parseColor("#000000"));*/
                    headerHolder.next.setTextColor(Color.parseColor("#DCDCDC"));
                    headerHolder.previous.setText("<  Previous");
                    headerHolder.next.setText("Next  >");
                    headerHolder.morning.setText(day4);
                    headerHolder.tommorow.setText(day5);
                    headerHolder.saturday.setText(day6);
                    headerHolder.sunday.setText(day7);


                    headerHolder.next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isNext == 1) {
                                headerHolder.previous.setTextColor(Color.parseColor("#000000"));
                                headerHolder.next.setTextColor(Color.parseColor("#DCDCDC"));
                                Log.e("load", "next data");
                                isPrev = 1;
                                isNext = 0;
                                adapterCheck = 2;
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this);
                                recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            } else {

                            }

                        }
                    });
                    headerHolder.previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (isPrev == 0) {

                                headerHolder.previous.setTextColor(Color.parseColor("#DCDCDC"));
                                headerHolder.next.setTextColor(Color.parseColor("#000000"));


                            } else {
                                Log.e("load", "previous data");
                                isPrev = 0;
                                isNext = 1;
                                adapterCheck = 1;

                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this);
                                recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            }
                        }
                    });

                } else if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                    FooterViewHolder footerHolder = (FooterViewHolder) holder;
                    footerHolder.mContinue.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (continueCheck == 0) {
                                Toast.makeText(CheckoutScreen2.this, "Please select any TimeSlots", Toast.LENGTH_LONG).show();
                            } else {
                                if (expressCount == cartcount) {
                                    deliveryType = "Express";
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", today1);
                                    intent.putExtra("time", "Within two hours");
                                    intent.putExtra("charge", "Kshs 50.00 (Express Delivery)");
                                    intent.putExtra("amount", "50");
                                    intent.putExtra("id", timeslotID);
                                    startActivity(intent);
                                } else if (expressCount == 0) {
                                    deliveryType = "Standard";
                                    a = timeslotAmount;
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("time", timeslotId);
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery)");
                                    intent.putExtra("amount", a);
                                    intent.putExtra("id", timeslotID);
                                    startActivity(intent);
                                } else if (cartcount > expressCount && (firstClick == 1)) {
                                    deliveryType = "Express";
                                    a = 50 + timeslotAmount;
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("amount", a);
                                    intent.putExtra("id", timeslotID);
                                    intent.putExtra("time", timeslotId + " (Standard Delivery)\nWithin two hours(Express Delivery)");
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery+Express Delivery)");
                                    startActivity(intent);
                                } else if (cartcount > expressCount && (lastClick == 1)) {
                                    a = timeslotAmount;
                                    deliveryType = "Standard";
                                    Intent intent = new Intent(CheckoutScreen2.this, CheckoutScreen3.class);
                                    intent.putExtra("date", passDate);
                                    intent.putExtra("time", timeslotId);
                                    intent.putExtra("amount", a);
                                    intent.putExtra("id", timeslotID);
                                    intent.putExtra("charge", "Kshs " + a + ".00 (Standard Delivery)");
                                    startActivity(intent);
                                }

                            }


                        }

                    });


                } else if (holder instanceof ItemViewHolder) {
                    ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                    if (cartcount == expressCount) {
                        continueCheck = 1;
                    }
                    if (cartcount == expressCount) {
                        itemViewHolder.text1.setVisibility(View.GONE);
                        itemViewHolder.text2.setVisibility(View.GONE);
                        itemViewHolder.text3.setVisibility(View.GONE);
                        itemViewHolder.text4.setVisibility(View.GONE);
                    } else {

                        if (position > 0) {
                            itemViewHolder.text1.setText("Kshs " + String.valueOf(Day4List.get(position - 1)));
                            itemViewHolder.text2.setText("Kshs " + String.valueOf(Day5List.get(position - 1)));
                            itemViewHolder.text3.setText("Kshs " + String.valueOf(Day6List.get(position - 1)));
                            itemViewHolder.text4.setText("Kshs " + String.valueOf(Day7List.get(position - 1)));


                            if (lastSelectedPosition == position) {
                                lastSelectedPosition = -1;
                                timeslotAmount = Day4List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text1.setBackground(getResources().getDrawable(R.drawable.border_listview));

                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 3);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                Log.e("timeslotid", timeslotId);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text1.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }
                            if (text2position == position) {
                                text2position = -1;
                                timeslotAmount = Day5List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                Log.e("timeslotid", timeslotId);
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text2.setBackground(getResources().getDrawable(R.drawable.border_listview));
                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 4);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text2.setBackground(getResources().getDrawable(R.drawable.border_unselected));
                            }
                            if (text3position == position) {
                                text3position = -1;
                                timeslotAmount = Day6List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                Log.e("timeslotid", timeslotId);
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text3.setBackground(getResources().getDrawable(R.drawable.border_listview));
                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 5);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text3.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }

                            if (text4position == position) {
                                text4position = -1;
                                timeslotAmount = Day7List.get(position - 1);
                                timeslotId = TimeSlotArraylist.get(position - 1);
                                Log.e("timeslotAmount", String.valueOf(timeslotAmount));
                                Log.e("timeslotid", timeslotId);
                                timeslotID = TimeSlotIDArraylist.get(position - 1);
                                itemViewHolder.text4.setBackground(getResources().getDrawable(R.drawable.border_listview));
                                Calendar calendar3 = Calendar.getInstance();

                                // add one day to the date/calendar
                                calendar3.add(Calendar.DAY_OF_YEAR, 6);

                                // now get "tomorrow"
                                passDate = mdformat.format(calendar3.getTime());
                                if (cartcount == expressCount) {
                                    continueCheck = 1;
                                } else {
                                    continueCheck = 1;
                                }
                            } else {
                                itemViewHolder.text4.setBackground(getResources().getDrawable(R.drawable.border_unselected));

                            }


                        }
                    }


                }


            }


        }

        @Override
        public int getItemCount() {

            return TimeSlotArraylist.size() + 2;//Add 2 more size to array list for Header and Fo        }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == TimeSlotArraylist.size() + 1) {
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView exp_title, std_title, express_text, previous, next, calendar, morning, tommorow, saturday, sunday;
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
            TextView text1, text2, text3, text4;

            public ItemViewHolder(View view) {
                super(view);
                text1 = (TextView) view.findViewById(R.id.text1);
                text2 = (TextView) view.findViewById(R.id.text2);
                text3 = (TextView) view.findViewById(R.id.text3);
                text4 = (TextView) view.findViewById(R.id.text4);

                if (adapterCheck == 1) {
                    text1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lastSelectedPosition = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text2position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text3position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text4position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                } else if (adapterCheck == 2) {
                    text1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lastSelectedPosition = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text2position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text3position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                    text4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            text4position = getAdapterPosition();
                            notifyDataSetChanged();
                        }
                    });
                }


                text1.setTypeface(mDynoRegular);
                text2.setTypeface(mDynoRegular);
                text3.setTypeface(mDynoRegular);
                text4.setTypeface(mDynoRegular);


            }

        }


    }


    private void timeslot(final String receivedDate1) {
        RequestQueue queue = Volley.newRequestQueue(CheckoutScreen2.this);
        final ProgressDialog progressDialog = new ProgressDialog(CheckoutScreen2.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv1 = (TextView) progressDialog.findViewById(android.R.id.message);
        tv1.setTextSize(20);
        tv1.setTypeface(mDynoRegular);
        tv1.setText("Please wait ...");


        url = Constants.TIMESLOTS + receivedDate1 + "&currenttime=" + time;
        Log.e("url", Constants.TIMESLOTS + receivedDate1 + "&currenttime=" + time);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("gettimeslots", String.valueOf(response));

                        try {
                            String status = response.getString("status");
                            if (status.equals("Success") || status.equals("success")) {

                                progressDialog.cancel();
                                JSONObject JObject = new JSONObject(String.valueOf(response));
                                JSONArray dataArray = JObject.getJSONArray("data");
                                if (dataArray.length() != 0) {
                                    Day1List = new ArrayList<>();
                                    Day2List = new ArrayList<>();
                                    Day3List = new ArrayList<>();
                                    Day4List = new ArrayList<>();
                                    Day5List = new ArrayList<>();
                                    Day6List = new ArrayList<>();
                                    Day7List = new ArrayList<>();
                                    TimeSlotArraylist = new ArrayList<>();
                                    TimeSlotIDArraylist = new ArrayList<>();

                                    for (int i = 0; i < dataArray.length(); i++) {
                                        Log.e("datacount", String.valueOf(dataArray.length()));
                                        JSONObject jsonObject = dataArray.getJSONObject(i);
                                        String open_time_formatted = jsonObject.getString("open_time_formatted");
                                        String id = jsonObject.getString("id");
                                        String close_time_formatted = jsonObject.getString("close_time_formatted");

                                        int day1 = jsonObject.getInt("day_1");
                                        int day2 = jsonObject.getInt("day_2");
                                        int day3 = jsonObject.getInt("day_3");
                                        int day4 = jsonObject.getInt("day_4");
                                        int day5 = jsonObject.getInt("day_5");
                                        int day6 = jsonObject.getInt("day_6");
                                        int day7 = jsonObject.getInt("day_7");


                                        TimeSlotArraylist.add(open_time_formatted + " - " + close_time_formatted);
                                        Day1List.add(day1);
                                        Day2List.add(day2);
                                        Day3List.add(day3);
                                        Day4List.add(day4);
                                        Day5List.add(day5);
                                        Day6List.add(day6);
                                        Day7List.add(day7);
                                        TimeSlotIDArraylist.add(id);


                                    }
                                    adapterCheck = 1;

                                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen2.this);
                                    recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen2.this, LinearLayoutManager.VERTICAL, false));
                                    recycler_view.setAdapter(adapter);

                                } else {
                                    Toast.makeText(CheckoutScreen2.this, " Time slots are not available ", Toast.LENGTH_LONG).show();
                                    progressDialog.cancel();

                                }

                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error", String.valueOf(volleyError));
                if (volleyError instanceof TimeoutError) {

                    Toast.makeText(CheckoutScreen2.this.getApplicationContext(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(CheckoutScreen2.this.getApplicationContext(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(
                    NetworkResponse networkResponse) {
                // TODO Auto-generated method stub
                Map<String, String> responseHeaders = networkResponse.headers;
                return super.parseNetworkResponse(networkResponse);
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<String, String>();

                return header;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }
}
