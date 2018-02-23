package com.tstl.kesouk.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tstl.kesouk.Activity.Login_Register_Activity;
import com.tstl.kesouk.Activity.Navigation_Tab_Activity;
import com.tstl.kesouk.Activity.Recipe_Youtube_Activity;
import com.tstl.kesouk.Adapter.Category_Adapter;
import com.tstl.kesouk.Adapter.Recipes_Adapter;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.CircularTransform;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tstl.kesouk.Activity.TabMain_Activity.search;
import static com.tstl.kesouk.Activity.TabMain_Activity.toolbar_title;

/**
 * Created by user on 25-Jan-18.
 */

public class Recipe_category_list_Fragment  extends Fragment {
    private RecyclerView products_listview;
    private Typeface mDynoRegular;
   // private Toolbar mToolbar;
    Category_Adapter browse_category_adapter;
    DB db;
    View view;
   // ImageView img_back,settings,search;
   // private TextView mToolbarTitle;
    int category_id = 0;
    public ArrayList<Recipe> recipeArrayList;
    RelativeLayout filterlayout;


    public static Recipe_category_list_Fragment newInstance() {
        Recipe_category_list_Fragment fragment = new Recipe_category_list_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_list_main, container, false);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        products_listview = (RecyclerView) view.findViewById(R.id.product_listing);


       // mToolbar = (Toolbar) view.findViewById(R.id.logintoolbar);

       // img_back = (ImageView) view.findViewById(R.id.img);
       // settings = (ImageView) view.findViewById(R.id.settings);
       // search = (ImageView) view.findViewById(R.id.search);
        //settings.setVisibility(View.GONE);
        //search.setVisibility(View.GONE);
        filterlayout = (RelativeLayout) view.findViewById(R.id.filter_layout);


        db = new DB(getActivity());
        filterlayout.setVisibility(View.GONE);

        //mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        //mToolbarTitle.setVisibility(View.VISIBLE);
      //  mToolbarTitle.setText("Recipes List");
        search.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Recipes List");

        //((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setFont();
        Bundle bundle1 = this.getArguments();
        if (bundle1 != null) {

            category_id = bundle1.getInt("position");

        }
        getRecipeBasedCategory();




 /*       img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.getAllLogin().size() == 1) {


                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                        System.exit(0);
                    }

                } else {
                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    } else {
                        Intent intent = new Intent(getActivity(), Login_Register_Activity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }

                }

            }
        });*/
        return view;
    }

    private void getRecipeBasedCategory() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_RECIPEBASED_CATEGORY + category_id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("getRecipeTypeCategory", String.valueOf(response));

                        try {


                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            JSONArray DataArray = JObject.getJSONArray("data");
                            Recipe recipe = null;
                            recipeArrayList = new ArrayList<Recipe>();
                            if (DataArray.length() != 0) {
                                for (int i = 0; i < DataArray.length(); i++) {

                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    String food_name = jsonObject.getString("recipe_name");
                                    String food_url = jsonObject.getString("recipe_image");
                                    int id = jsonObject.getInt("id");

                                    recipe = new Recipe();

                                    recipe.setTitle(food_name);
                                    recipe.setImage(food_url);
                                    recipe.setRecipeTypeId(id);
                                    recipeArrayList.add(recipe);


                                }
                            } else {
                                Toast.makeText(getActivity(), "No Recipes Sections ! ", Toast.LENGTH_LONG).show();

                            }


                            Recipes_Based_Category_Adapter adapter = new Recipes_Based_Category_Adapter(getActivity(), recipeArrayList);

                            products_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                            products_listview.setAdapter(adapter);


                        } catch (Exception e) {
                            Log.i("Err", e.getLocalizedMessage());
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(getActivity(), "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsObjRequest);
    }

    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getActivity().getAssets(),
                "font/Roboto_Regular.ttf");

     //   mToolbarTitle.setTypeface(mDynoRegular);
    }

    public class Recipes_Based_Category_Adapter extends RecyclerView.Adapter<Recipes_Based_Category_Adapter.MyViewHolder> {

        private Context mContext;
        public ArrayList<Recipe> recipeArrayList;
        private ArrayList<String> arrayList;
        private int check;
        private Typeface mDynoRegular;
        DB db;
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView prod_image;
            public CardView cardView;
            Button mAddtoCArt;

            public MyViewHolder(View view) {
                super(view);

                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(),
                        "font/Roboto_Regular.ttf");

                title = (TextView) view.findViewById(R.id.tv_prodname);

                prod_image = (ImageView) view.findViewById(R.id.imageView);

                cardView = (CardView) view.findViewById(R.id.card_view);
                mAddtoCArt = (Button) view.findViewById(R.id.add_to_cart);

                db = new DB(mContext);
                title.setTypeface(mDynoRegular);

                mAddtoCArt.setTypeface(mDynoRegular);

            }
        }

        public Recipes_Based_Category_Adapter(Context mContext, ArrayList<Recipe> recipeArrayList) {
            this.mContext = mContext;
            this.recipeArrayList = recipeArrayList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receipe_item_category, parent, false);

            return new MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Recipe recipe = recipeArrayList.get(position);


            holder.title.setText(recipe.getTitle());

          //  Glide.with(mContext).load(Constants.RECIPE_IMAGES + recipe.getImage()).into(holder.prod_image);

            Glide.with(mContext).load(Constants.RECIPE_IMAGES + recipe.getImage()).transform(new CircularTransform(mContext)).placeholder(R.drawable.logo).into(holder.prod_image);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, Recipe_Youtube_Activity.class);
                    Bundle args = new Bundle();
                    args.putInt("position", recipe.getRecipeTypeId());
                    args.putInt("category_id", category_id);
                    args.putString("title", recipe.getTitle());
                    intent.putExtras(args);
                    mContext.startActivity(intent);

/*
                    Recipe_Fragment newFragment = new Recipe_Fragment();
                    Bundle args = new Bundle();
                    args.putInt("position", recipe.getRecipeTypeId());
                    args.putInt("category_id", category_id);
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_fragment,newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/
                }
            });
           /* holder.mAddtoCArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    Map<String, String> params = new HashMap<String, String>();

                    Log.e("customer_id ","0");
                    Log.e("product_id ", String.valueOf((browse_category.getProduct_id())));
                    Log.e("quantity ","1");
                    Log.e("price_id ", String.valueOf(browse_category.getProduct_priceId()));


                    if(db.getAllLogin().size()==1)
                    {
                        params.put("customer_id", db.getAllLogin().get(0));

                    }
                    else if(db.getAllLogin().size()==0)
                    {
                        params.put("customer_id", "0");

                    }
                    params.put("product_id", String.valueOf((browse_category.getProduct_id())));
                    params.put("quantity", "1");
                    params.put("price_id", String.valueOf(browse_category.getProduct_priceId()));




                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            Constants.ADDTOCART, new JSONObject(params),
                            new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject object) {
                                    try {
                                        Log.e("addtocart", String.valueOf(object));


                                        String status=object.getString("status");
                                        if(status.equals("Success"))
                                        {
                                            int  data_id = object.getInt("data");
                                            String  data_id1 = object.getString("data");
                                            Log.e("data_id", String.valueOf(data_id));

                                            if(db.getAllLogin().size()==1)
                                            {

                                            }
                                            else if(db.getAllLogin().size()==0)
                                            {
                                                db.insert(data_id1);
                                                Log.e("fetch", String.valueOf(db.getAllData()));
                                            }


                                            AlertDialog.Builder builder =
                                                    new AlertDialog.Builder(mContext);
                                            builder.setTitle("Message");
                                            builder.setMessage(browse_category.getProd_name().toUpperCase() +" has been added to Basket !");

                                            String positiveText = "Ok";
                                            builder.setPositiveButton(positiveText,
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });

                                            String negativeText = mContext.getString(android.R.string.cancel);
                                            builder.setNegativeButton(negativeText,
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // negative button logic
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            AlertDialog dialog = builder.create();
                                            // display dialog
                                            dialog.show();
                                        }
                                        else
                                        {
                                            String reason=object.getString("reason");
                                            Toast.makeText(mContext, reason ,Toast.LENGTH_LONG).show();

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
                                Toast.makeText(mContext, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(mContext, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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
            });

*/
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return recipeArrayList.size();
        }

    }
}
