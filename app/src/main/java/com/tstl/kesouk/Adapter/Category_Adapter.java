package com.tstl.kesouk.Adapter;

/**
 * Created by user on 24-Jan-18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.bumptech.glide.Glide;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Fragments.Favorites_Fragment;
import com.tstl.kesouk.Fragments.Product_Description_Fragment;
import com.tstl.kesouk.Model.Browse_Category;
import com.tstl.kesouk.Model.CircularTransform;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.MyViewHolder> {

    private Context mContext;
    public static ArrayList<Browse_Category> browse_categoryArrayList;
    public ArrayList<Recipe> recipeArrayList;
    private ArrayList<String> arrayList;
    private int check;
    private Typeface mDynoRegular;
    public static int position_product_onclick;
    Browse_Category browse_category1 = new Browse_Category();
    DB db;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private HashMap<Integer, Integer> selectedItem = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> totalPrices = new HashMap<Integer, String>();
    private String total_amount = null, quanity_with_name, kgs, search_word;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, marketprice, kesoukPrice, no_prod, tagprice, spinner_text;
        public ImageView prod_image, mExpress;
        public Spinner spinner;
        public CardView cardView;
        public ToggleButton toggleButton;
        Button mAddtoCArt;
        RatingBar ratingBar;

        // public CircleImageView circleImageView;

        public MyViewHolder(View view) {
            super(view);

            mDynoRegular = Typeface.createFromAsset(mContext.getAssets(),
                    "font/Roboto_Regular.ttf");

            title = (TextView) view.findViewById(R.id.tv_prodname);
            tagprice = (TextView) view.findViewById(R.id.tag_price);
            marketprice = (TextView) view.findViewById(R.id.off_amount);
            kesoukPrice = (TextView) view.findViewById(R.id.price);
            mExpress = (ImageView) view.findViewById(R.id.bike);
            prod_image = (ImageView) view.findViewById(R.id.imageView);
            //circleImageView = (CircleImageView) view.findViewById(R.id.circleView);
            toggleButton = (ToggleButton) view.findViewById(R.id.fav);
            spinner = (Spinner) view.findViewById(R.id.spinner);
            cardView = (CardView) view.findViewById(R.id.card_view);
            mAddtoCArt = (Button) view.findViewById(R.id.add_to_cart);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingbar_Small);

            marketprice.setPaintFlags(marketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            db = new DB(mContext);
            title.setTypeface(mDynoRegular);
            marketprice.setTypeface(mDynoRegular);
            kesoukPrice.setTypeface(mDynoRegular);
            mAddtoCArt.setTypeface(mDynoRegular);
            tagprice.setTypeface(mDynoRegular);
        }
    }

    public Category_Adapter(Context mContext, ArrayList<Browse_Category> browse_categoryArrayList, ArrayList<Recipe> receipe_productQtyList, int check, ArrayList<String> quantityList, String search_word) {
        this.mContext = mContext;
        this.browse_categoryArrayList = browse_categoryArrayList;
        this.recipeArrayList = receipe_productQtyList;
        this.check = check;
        this.arrayList = quantityList;
        this.search_word = search_word;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_listing, parent, false);

        return new MyViewHolder(itemView);

    }

    private void setRatingStarColor(Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final MyViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                // Filled stars
                setRatingStarColor(stars.getDrawable(2), ContextCompat.getColor(mContext, R.color.rating_star));
                // Half filled stars
                setRatingStarColor(stars.getDrawable(1), ContextCompat.getColor(mContext, R.color.rating_star));
                // Empty stars
                setRatingStarColor(stars.getDrawable(0), ContextCompat.getColor(mContext, R.color.tab_line));


                try {
                    Drawable progressDrawable = ratingBar.getProgressDrawable();
                    if (progressDrawable != null) {
                        DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(mContext, R.color.rating_star));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Browse_Category browse_category = browse_categoryArrayList.get(position);
        final Recipe recipe = recipeArrayList.get(position);
        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));


        holder.ratingBar.setTag("rating:" + position);


        final ArrayAdapter<String> quatity = new ArrayAdapter<String>(mContext, R.layout.spinner_item, recipe.getQuantityListPojo());
        quatity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(quatity);
        if (selectedItem.get(position) != null) {
            //This should call after setAdapter
            holder.spinner.setSelection(selectedItem.get(position));
        }


        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int i, long arg3) {
                // selected item in the list
                String item = recipe.getQuantityListPojo() + browse_category.getProduct_qty_name();
                Log.e("item", item);


                String state = arg0.getItemAtPosition(i).toString() + browse_category.getProduct_qty_name();
                holder.spinner_text = (TextView) arg1.findViewById(R.id.item);
                holder.spinner_text.setText(state);

                // ((TextView)arg1. findViewById(R.id.item)).setText(state);
                Log.e("position_spinner", recipe.getQuantityListPojo().get(i));
                Log.e("and", state);
                kgs = recipe.getQuantityListPojo().get(i);


                quanity_with_name = arg0.getItemAtPosition(i).toString() + browse_category.getProduct_qty_name();

                int prod = browse_category.getProduct_discount();
                if (prod > 0) {
                    holder.kesoukPrice.setText("SH " + recipe.getActualListPojo().get(i));
                    holder.marketprice.setText("SH " + recipe.getPriceListPojo().get(i));
                    holder.tagprice.setVisibility(View.VISIBLE);

                    holder.tagprice.setText(browse_category.getProduct_discount() + " % off");
                    holder.marketprice.setPaintFlags(holder.marketprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    String express = browse_category.getIs_express_delivery();
                    if (express.equals("1")) {
                        holder.mExpress.setVisibility(View.VISIBLE);
                    } else {
                        holder.mExpress.setVisibility(View.GONE);
                    }


                } else {
                    holder.marketprice.setVisibility(View.GONE);
                    holder.kesoukPrice.setText("SH " + recipe.getActualListPojo().get(i));
                    // holder.tagprice.setVisibility(View.GONE);
                }
                total_amount = "SH " + recipe.getActualListPojo().get(i);


            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, recipe.getQuantityListPojo());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setPrompt("QUANTITY");
        holder.spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        mContext));*/


        if (check == 1) {
            Log.e("product_name", browse_category.getProd_name());
            Log.e("product_image", browse_category.getProduct_image());
            Log.e("product_price", "$ " + browse_category.getDukanPrice());
            Log.e("product_quantity_type", String.valueOf(browse_category.getProduct_quantity_type()));

            Log.e("adapter", "adapter");

            holder.title.setText(browse_category.getProd_name());
            // holder.ratingBar.setRating(browse_category.getRatingStar());
            holder.ratingBar.setRating(3);


            // loading album cover using Glide library
            // Glide.with(mContext).load(Constants.PRODUCT_IMAGES + browse_category.getProduct_image()).into(holder.prod_image);
            Glide.with(mContext).load(Constants.PRODUCT_IMAGES + browse_category.getProduct_image()).placeholder(R.drawable.logo).into(holder.prod_image);

           /* Glide.with(mContext).load(Constants.PRODUCT_IMAGES + browse_category.getProduct_image()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.prod_image) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.prod_image.setImageDrawable(circularBitmapDrawable);
                }
            });*/


        } else {

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product_Description_Fragment newFragment = new Product_Description_Fragment();
                Bundle args = new Bundle();
                args.putInt("position", position);
                args.putString("product_random_id", browse_category.getProduct_random_id());
                args.putString("category", browse_category.getSimilar_category());
                args.putString("subcategory", browse_category.getSimilar_subcategory());
                args.putString("id", browse_category.getSimilar_id());
                args.putString("qty_name", browse_category.getProduct_qty_name());
                args.putString("price", holder.kesoukPrice.getText().toString());
                args.putString("qty", holder.spinner_text.getText().toString());
                args.putString("search_word", search_word);
                args.putString("product_price_id", recipe.getProduct_priceId());
                args.putInt("product_id", browse_category.getProduct_id());
                args.putString("price_product_id", browse_category.getProduct_priceId());

                int prod = browse_category.getProduct_discount();
                if (prod > 0)
                {
                    args.putString("other_price", holder.marketprice.getText().toString());

                } else {
                    args.putString("other_price","empty");

                }
                newFragment.setArguments(args);

                FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.rldContainer, newFragment);
                transaction.addToBackStack("Some String");
                transaction.commit();


            }
        });

        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext.getApplicationContext(), browse_category.getProd_name() + " has been added to your Wishlist", Toast.LENGTH_LONG).show();

                RequestQueue queue = Volley.newRequestQueue(mContext);
                Map<String, String> params = new HashMap<String, String>();


                if (db.getAllLogin().size() == 1) {
                    params.put("customer_id", db.getAllLogin().get(0));

                } else if (db.getAllLogin().size() == 0) {
                    params.put("customer_id", "0");

                } else if (db.getAllLogin().size() == 2) {
                    params.put("customer_id", db.getAllLogin().get(0));

                }

                params.put("product_id", String.valueOf((browse_category.getProduct_id())));
                params.put("price_id", String.valueOf(recipe.getProduct_priceId()));
                Log.e("json", params.toString());


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        Constants.ADDTOWISHLIST, new JSONObject(params),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject object) {
                                try {
                                    Log.e("wishlist", String.valueOf(object));


                                    String status = object.getString("status");
                                    if (status.equals("Success")) {
                                        String data_id = object.getString("data");
                                        Log.e("data_id", data_id);

                                        if (db.getAllLogin().size() == 1) {

                                        } else if (db.getAllLogin().size() == 0) {
                                            db.insert_wishlist(data_id);
                                            Log.e("fetch", String.valueOf(db.getAllWishlist()));
                                        }

                                    } else {
                                        String reason = object.getString("reason");
                                        Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                        } else
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
        holder.mAddtoCArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(mContext);
                Map<String, String> params = new HashMap<String, String>();

                Log.e("customer_id ", "0");
                Log.e("product_id ", String.valueOf((browse_category.getProduct_id())));
                Log.e("quantity ", "1");
                Log.e("price_id ", String.valueOf(browse_category.getProduct_priceId()));


                if (db.getAllLogin().size() == 1) {
                    params.put("customer_id", db.getAllLogin().get(0));

                } else if (db.getAllLogin().size() == 0) {
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


                                    String status = object.getString("status");
                                    if (status.equals("Success")) {
                                        int data_id = object.getInt("data");
                                        String data_id1 = object.getString("data");
                                        Log.e("data_id", String.valueOf(data_id));

                                        if (db.getAllLogin().size() == 1) {

                                        } else if (db.getAllLogin().size() == 0) {
                                            db.insert(data_id1);
                                            Log.e("fetch", String.valueOf(db.getAllData()));
                                        }


                                        AlertDialog.Builder builder =
                                                new AlertDialog.Builder(mContext);
                                        builder.setTitle("Message");
                                        builder.setMessage(browse_category.getProd_name().toUpperCase() + " has been added to Basket !");

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
                                    } else {
                                        String reason = object.getString("reason");
                                        Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();

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
                        } else
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

        holder.prod_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*  dealsfrag = 1;
                Intent intent = new Intent(mContext.getApplicationContext(), Product_Details_Activity.class);


                Browse_Category browse_category1 = browse_categoryArrayList.get(position);
                browse_category1.getProduct_id();
                position_product_onclick=browse_category1.getProduct_id();
                Log.e("prod", String.valueOf(browse_category1.getProduct_id()));



                Bundle bundle = new Bundle();
                bundle.putString("name", browse_categoryArrayList.get(position).getProd_name());
                bundle.putInt("id", browse_categoryArrayList.get(position).getProduct_id());
                bundle.putInt("category", browse_categoryArrayList.get(position).getCategoryId());
                bundle.putInt("product_id", browse_categoryArrayList.get(position).getProduct_id());
                bundle.putString("product_price_id", browse_categoryArrayList.get(position).getProduct_priceId());

                Log.e("name", browse_categoryArrayList.get(position).getProd_name());
                Log.e("id", String.valueOf(browse_categoryArrayList.get(position).getProduct_id()));
                Log.e("category", String.valueOf(browse_categoryArrayList.get(position).getCategoryId()));
                Log.e("product_id", String.valueOf(browse_categoryArrayList.get(position).getProduct_id()));
                Log.e("product_price_id", String.valueOf(browse_categoryArrayList.get(position).getProduct_priceId()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
*/
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return browse_categoryArrayList.size();
    }

}



