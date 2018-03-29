package com.tstl.kesouk.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Login_Credentials;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 07-Mar-18.
 */





public class CheckoutScreen1 extends AppCompatActivity {
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    RecyclerView recycler_view;
    DB db;
    String firstName = "", lastname = "", email = "", countrycode = "", mobilnumber = "", gender = "", age = "", country = "", city = "", district = "", location_text = "", profile_image = "",house_no="",area="";
    String first_name, last_name;
    String id_add;
    public static int edit_img_click = 0;
    public static int add_addr_click = 0;
    private ImageView backBtn,settings,search;
    public static String delvy_pickup;
    int status_code,default_address,address_position;
    public ArrayList<Login_Credentials> login_credentials = null;
    public static int CheckoutAddress=0;
    String def_id;
    int def_checkbox;
    String defaultaddressTextView,defaultNameTextView,def_nickname,def_fNmae,def_LName,def_mob,def_city,def_house,def_resident,def_area,def_street,def_land;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new DB(this);
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Extract the data…
            delvy_pickup = bundle.getString("assignOrdelivery");
            status_code = bundle.getInt("status");
        }
        getUserAddress();

    }

    private void getUserAddress() {
        final ProgressDialog progressDialog = new ProgressDialog(CheckoutScreen1.this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        RequestQueue queue = Volley.newRequestQueue(CheckoutScreen1.this);
        StringRequest jsObjRequest = new StringRequest(
                Request.Method.GET, Constants.GET_USER_ADDRESS+db.getAllLogin().get(0),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        Login_Credentials login = null;

                        Log.e("get_current_user", String.valueOf(response));
                        try {
                            login_credentials = new ArrayList<>();
                            JSONObject JObject = new JSONObject(String.valueOf(response));


                            JSONArray jsonArray = JObject.getJSONArray("data");
                            if (jsonArray.length() != 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    default_address = jsonObject1.getInt("default_address");
                                    login = new Login_Credentials();
                                    if (default_address==1)
                                    {
                                        Log.e("postion", String.valueOf(i));
                                        address_position=1;
                                        def_checkbox=1;

                                        location_text = jsonObject1.getString("location");
                                        country = jsonObject1.getString("country");
                                        def_city = jsonObject1.getString("city");
                                        district = jsonObject1.getString("district");
                                        def_id = jsonObject1.getString("id");
                                        def_fNmae = jsonObject1.getString("first_name");
                                        def_LName = jsonObject1.getString("last_name");
                                        default_address = jsonObject1.getInt("default_address");
                                        def_mob = jsonObject1.getString("contact_no");
                                        String country_code = jsonObject1.getString("country_code");

                                        def_nickname = jsonObject1.getString("nick_name");
                                        def_resident = jsonObject1.getString("complex");
                                        def_street = jsonObject1.getString("street_name");
                                        def_land = jsonObject1.getString("land_mark");

                                        def_house = jsonObject1.getString("house_no");
                                        def_area = jsonObject1.getString("area");
                                        defaultaddressTextView=def_house+ "\n"+def_city+"\n"+def_area;
                                        defaultNameTextView=def_fNmae;
/*
                    itemViewHolder.address_field.setText(login_credentials.getFirstname() + " " + login_credentials.getLastname() + "\n" + login_credentials.getHouse() + "\n" + login_credentials.getCity() + "\n" + login_credentials.getArea());

                                        login.setFirstname(first_name);
                                        login.setLastname(last_name);
                                        login.setAddId(id_add);
                                        login.setLocation(location_text);
                                        login.setCountry(country);
                                        login.setCity(city);
                                        login.setDistrict(district);
                                        login.setHouse(house_no);
                                        login.setArea(area);
                                        login.setContact(contact_no);
                                        login.setCountry_code(country_code);
                                        login.setNickName(nickname);
                                        login.setComplex(complex);
                                        login.setStreetName(streetname);
                                        login.setLandMark(landmark);
                                        login.setDefaultAddress(default_address);


                                        login.setDefault_address(address_position);
                                        login_credentials.add(login);*/
                                    }
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    login = new Login_Credentials();
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    default_address = jsonObject1.getInt("default_address");
                                    if (default_address==0)
                                    {
                                        location_text = jsonObject1.getString("location");
                                        country = jsonObject1.getString("country");
                                        city = jsonObject1.getString("city");
                                        district = jsonObject1.getString("district");
                                        id_add = jsonObject1.getString("id");
                                        first_name = jsonObject1.getString("first_name");
                                        last_name = jsonObject1.getString("last_name");
                                        default_address = jsonObject1.getInt("default_address");
                                        String contact_no = jsonObject1.getString("contact_no");
                                        String country_code = jsonObject1.getString("country_code");
                                        house_no = jsonObject1.getString("house_no");
                                        area = jsonObject1.getString("area");
                                        String nickname = jsonObject1.getString("nick_name");
                                        String complex = jsonObject1.getString("complex");
                                        String streetname = jsonObject1.getString("street_name");
                                        String landmark = jsonObject1.getString("land_mark");

                                        login.setFirstname(first_name);
                                        login.setLastname(last_name);
                                        login.setAddId(id_add);
                                        login.setLocation(location_text);
                                        login.setCountry(country);
                                        login.setCity(city);
                                        login.setDistrict(district);
                                        login.setContact(contact_no);
                                        login.setCountry_code(country_code);
                                        login.setHouse(house_no);
                                        login.setArea(area);
                                        login.setNickName(nickname);
                                        login.setComplex(complex);
                                        login.setStreetName(streetname);
                                        login.setLandMark(landmark);
                                        login.setDefaultAddress(default_address);

                                        login_credentials.add(login);
                                    }

                                }

                                Log.e("addid", id_add);
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(CheckoutScreen1.this, login_credentials);
                                recycler_view.setLayoutManager(new LinearLayoutManager(CheckoutScreen1.this, LinearLayoutManager.VERTICAL, false));
                                recycler_view.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Log.e("Err",e.getLocalizedMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.cancel();
                Log.e("Err", String.valueOf(volleyError));
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(CheckoutScreen1.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(CheckoutScreen1.this, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<String, String>();
                // String title = String.valueOf(db.getCookie());
                // String role = title.substring(1, title.length() - 1);
                //  Log.e("cookie", role);
                header.put("Content-Type", "application/json; charset=utf-8");
                // header.put("Authorization", "Bearer " + role);
                return header;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);

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
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen1_item, parent, false);
                return new ItemViewHolder(itemView);
            } else if (viewType == TYPE_HEADER) {
                //Inflating header view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen1_header, parent, false);
                return new HeaderViewHolder(itemView);
            } else if (viewType == TYPE_FOOTER) {
                //Inflating footer view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_screen1_footer, parent, false);
                return new FooterViewHolder(itemView);
            } else return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                headerHolder.defaultName.setText(defaultNameTextView);
                headerHolder.defaultAddress.setText(defaultaddressTextView);
                headerHolder.defaultEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_img_click = 1;
                        CheckoutAddress=1;
                        Intent intent = new Intent(CheckoutScreen1.this, Add_Update_Address_Activity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString("one", def_nickname);
                        mBundle.putString("two", def_fNmae);
                        mBundle.putString("three", def_LName);
                        mBundle.putString("four", def_mob);
                        mBundle.putString("five", def_city);
                        mBundle.putString("six", def_house);
                        mBundle.putString("seven", def_resident);
                        mBundle.putString("eight", def_area);
                        mBundle.putString("nine", def_street);
                        mBundle.putString("ten", def_land);
                        mBundle.putInt("default", 1);
                        mBundle.putString("id", def_id);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                });



            } else if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                FooterViewHolder footerHolder = (FooterViewHolder) holder;
                footerHolder.mContinue.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CheckoutScreen1.this, CheckoutScreen2.class);
                        startActivity(intent);
                    }

                });

                footerHolder.mAddaddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_addr_click = 1;
                        CheckoutAddress=1;
                        Intent intent = new Intent(CheckoutScreen1.this, Add_New_Address_Activity.class);
                        startActivity(intent);
                    }
                });

            } else if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                // login_credentialsArrayList.get(address_position);

               /* if (onClick==1)
                {
                    if (position > 0) {

                        if (lastSelectedPosition == position) {
                            //itemViewHolder.relativeLayout.setBackgroundColor(getResources().getColor(R.color.app_bg));
                            itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_listview));
                        } else {
                            itemViewHolder.relativeLayout.setBackground(getResources().getDrawable(R.drawable.border_unselected));
                        }
                    }
                }
*/
                if (position > 0) {
                    // itemViewHolder .relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_selector));


                    address_id = login_credentialsArrayList.get(position - 1).getAddId();

                    final Login_Credentials login_credentials = login_credentialsArrayList.get(position - 1);
                    itemViewHolder.name.setText(login_credentials.getFirstname());
                    itemViewHolder.address_field.setText( login_credentials.getHouse() + "\n" + login_credentials.getCity() + "\n" + login_credentials.getArea());
                    itemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edit_img_click = 1;
                            CheckoutAddress=1;
                            Intent intent = new Intent(CheckoutScreen1.this, Add_Update_Address_Activity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString("one", login_credentials.getNickName());
                            mBundle.putString("two", login_credentials.getFirstname());
                            mBundle.putString("three", login_credentials.getLastname());
                            mBundle.putString("four", login_credentials.getContact());
                            mBundle.putString("five", login_credentials.getCity());
                            mBundle.putString("six", login_credentials.getHouse());
                            mBundle.putString("seven", login_credentials.getComplex());
                            mBundle.putString("eight", login_credentials.getArea());
                            mBundle.putString("nine", login_credentials.getStreetName());
                            mBundle.putString("ten", login_credentials.getLandMark());
                            mBundle.putInt("default", login_credentials.getDefaultAddress());
                            mBundle.putString("id", login_credentials.getAddId());
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        }
                    });
                   /* itemViewHolder.selectionState.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("addid",login_credentials.getAddId());
                            address_id=login_credentials.getAddId();
                        }
                    });*/

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

/*

        @Override
        public int getItemCount() {
            return cartArrayList.size() + 2;
        }

*/

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView defaultName,defaultAddress;
            Button defaultEdit;
            TextView defaultaddressTxt,otherAddressText;

            public HeaderViewHolder(View view) {
                super(view);
                defaultName = (TextView) view.findViewById(R.id.name_def);
                defaultAddress = (TextView) view.findViewById(R.id.address_def);
                defaultEdit = (Button) view.findViewById(R.id.edit_def);
                defaultaddressTxt = (TextView) view.findViewById(R.id.defaultaddr);
                otherAddressText = (TextView) view.findViewById(R.id.otheraddr);


                defaultName.setTypeface(mDynoRegular);
                defaultAddress.setTypeface(mDynoRegular);
                defaultEdit.setTypeface(mDynoRegular);
                defaultaddressTxt.setTypeface(mDynoRegular);
                otherAddressText.setTypeface(mDynoRegular);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {
            Button mContinue,mAddaddress;

            public FooterViewHolder(View view) {
                super(view);
                mContinue = (Button) view.findViewById(R.id.continue_btn);
                mAddaddress = (Button) view.findViewById(R.id.add_addr);
                mContinue.setTypeface(mDynoRegular);
                mAddaddress.setTypeface(mDynoRegular);
            }
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            Button mEdit;
            TextView name,address_field;
            RelativeLayout relativeLayout;

            public ItemViewHolder( View view) {
                super(view);
                mEdit = (Button) view.findViewById(R.id.edit_txt);
                address_field = (TextView) view.findViewById(R.id.address_txt);
                name = (TextView) view.findViewById(R.id.name_txt);
                relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_item);
                name.setTypeface(mDynoRegular);
                address_field.setTypeface(mDynoRegular);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClick=1;
                        lastSelectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                });

            }

        }


    }

    private void setFont() {
        mDynoRegular = Typeface.createFromAsset(getAssets(), "font/Roboto_Regular.ttf");
        mToolbarTitle.setTypeface(mDynoRegular);

    }
}
