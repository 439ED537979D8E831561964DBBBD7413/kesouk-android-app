package com.tstl.kesouk.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tstl.kesouk.DB.DB;
import com.tstl.kesouk.Model.CircularTransform;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 26-Jan-18.
 */

public class Recipe_Youtube_Activity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    private TextView mIngredients, mservings, mPrepare, mCooking, ingredients_title, quick_info, cooking_steps, textview, mToolbarTitle, textview1;
    Button mAddtoBasket;
    RecyclerView similarProducts_recylerview, ingedients_recyclerview;
    private Typeface mDynoRegular;
    private Toolbar mToolbar;
    ImageView img_back,settings,search;
    int recipe_id = 0,category_id=0;
    public ArrayList<Recipe> recipeArrayList,similarProductsArrayList;
    RelativeLayout relativeLayout;
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String video_id;
    View tab1,tab2,tab3;
    DB db;


  /*  public static com.tstl.kesouk.Fragments.Recipe_Fragment newInstance() {
        com.tstl.kesouk.Fragments.Recipe_Fragment fragment = new com.tstl.kesouk.Fragments.Recipe_Fragment();
        return fragment;
    }*/

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.receipe_list);
        mToolbar = (Toolbar) findViewById(R.id.logintoolbar);
        db=new DB(Recipe_Youtube_Activity.this);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Constants.YOUTUBE_API_KEY, this);
        img_back = (ImageView) findViewById(R.id.img);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mIngredients = (TextView) findViewById(R.id.ingred);
        mservings = (TextView) findViewById(R.id.serving);
        settings = (ImageView)findViewById(R.id.settings);
        search = (ImageView) findViewById(R.id.search);
        mPrepare = (TextView) findViewById(R.id.prepare);
        mCooking = (TextView) findViewById(R.id.cooking);
        ingredients_title = (TextView) findViewById(R.id.ingred1);
        quick_info = (TextView) findViewById(R.id.qucik_info);
        cooking_steps = (TextView) findViewById(R.id.cooking_steps);
        textview = (TextView) findViewById(R.id.textview);
        textview1 = (TextView) findViewById(R.id.textview1);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);

        tab1 = (View) findViewById(R.id.tab_view1);
        tab2 = (View) findViewById(R.id.tab_view2);
        tab3 = (View) findViewById(R.id.tab_view3);

        ingedients_recyclerview = (RecyclerView) findViewById(R.id.ingred_listing);
        similarProducts_recylerview = (RecyclerView) findViewById(R.id.list_similar_products);

        mAddtoBasket = (Button) findViewById(R.id.add_to_basket);
        settings.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
       /* Bundle bundle1 = this.getArguments();
        if (bundle1 != null) {

            recipe_id = bundle1.getInt("position");
            category_id = bundle1.getInt("category_id");

        }*/

        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
//Extract the data…
            recipe_id = bundle1.getInt("position");
            category_id = bundle1.getInt("category_id");
            String title = bundle1.getString("title");
            mToolbarTitle.setText(title);
        }
        getRecipeDetails();
        getSimilarProducts();
        mToolbarTitle.setVisibility(View.VISIBLE);


        setFont();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Recipe_Youtube_Activity.this, TabMain_Activity.class);
                    startActivity(intent);


            }
        });


    }



    private void setFont() {

        mDynoRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto_Regular.ttf");

        mToolbarTitle.setTypeface(mDynoRegular);
        mIngredients.setTypeface(mDynoRegular);
        mservings.setTypeface(mDynoRegular);
        mAddtoBasket.setTypeface(mDynoRegular);
        mPrepare.setTypeface(mDynoRegular);
        mCooking.setTypeface(mDynoRegular);
        ingredients_title.setTypeface(mDynoRegular);
        quick_info.setTypeface(mDynoRegular);
        cooking_steps.setTypeface(mDynoRegular);
        textview.setTypeface(mDynoRegular);
        mToolbarTitle.setTypeface(mDynoRegular);
        textview1.setTypeface(mDynoRegular);

    }
    private void getSimilarProducts() {
        RequestQueue queue = Volley.newRequestQueue(Recipe_Youtube_Activity.this);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_SIMILAR_RECIPES_PRODUCTS + recipe_id+"&category_id="+category_id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("getSimilarProducts", String.valueOf(response));

                        try {



                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            JSONArray DataArray = JObject.getJSONArray("data");
                            Recipe recipe = null;
                            similarProductsArrayList = new ArrayList<Recipe>();
                            if (DataArray.length() != 0) {
                                for (int i = 0; i < DataArray.length(); i++) {

                                    Log.e("datacount", String.valueOf(DataArray.length()));
                                    JSONObject jsonObject = DataArray.getJSONObject(i);
                                    String food_name = jsonObject.getString("recipe_name");
                                    String food_url = jsonObject.getString("recipe_image");
                                    int id = jsonObject.getInt("id");

                                    recipe = new Recipe();

                                    recipe.setSimilar_title(food_name);
                                    recipe.setSimilar_image(food_url);
                                    recipe.setRecipeTypeId(id);
                                    similarProductsArrayList.add(recipe);


                                }
                            } else {
                                Toast.makeText(Recipe_Youtube_Activity.this, "No Similar Recipes Sections Found! ", Toast.LENGTH_LONG).show();

                            }

                            SimilarAdapter adapter = new SimilarAdapter(Recipe_Youtube_Activity.this, similarProductsArrayList);

                            // similarProducts_recylerview.setHasFixedSize(true);
                            similarProducts_recylerview.setLayoutManager(new LinearLayoutManager(Recipe_Youtube_Activity.this, LinearLayoutManager.HORIZONTAL, false));
                            similarProducts_recylerview.setAdapter(adapter);



                        } catch (Exception e) {
                            // Log.i("Err", e.getLocalizedMessage());
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(Recipe_Youtube_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(Recipe_Youtube_Activity.this, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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



    public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SingleItemRowHolder> {

        private ArrayList<Recipe> itemsList;
        private Context mContext;
        private String transaction;


        public SimilarAdapter(Context context, ArrayList<Recipe> itemsList) {
            this.itemsList = itemsList;
            this.mContext = context;

        }

        @Override
        public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, null);

            return new SingleItemRowHolder(v);        }

        @Override
        public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {

            final Recipe recipe = itemsList.get(i);

            holder.tvTitle.setText(recipe.getSimilar_title());
            Glide.with(mContext)
                    .load(Constants.RECIPE_IMAGES + recipe.getSimilar_image())
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.centerCrop()
                   // .error(R.drawable.logo)
                    .into(holder.itemImage);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 /*   Recipe_category_list_Fragment newFragment = new Recipe_category_list_Fragment();
                    Bundle args = new Bundle();
                    args.putInt("position", recipe.getRecipeTypeId());
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_fragment,newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/



              /*  SingleItemModel singleItemModel = itemsList.get(i);
                singleItemModel.getName();

                Intent intent = new Intent(mContext.getApplicationContext(),Browse_Category_Activity.class);



//Create the bundle
                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putInt("category_selected_name", groupPosition);
//Add the bundle to the intent
                intent.putExtras(bundle);

                mContext.startActivity(intent);
*/

                }
            });
        }

        @Override
        public int getItemCount() {
            return (null != itemsList ? itemsList.size() : 0);
        }

        public class SingleItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView tvTitle;

            protected ImageView itemImage;
            private Typeface mDynoRegular;
            CardView cardView;


            public SingleItemRowHolder(View view) {
                super(view);

                this.tvTitle = (TextView) view.findViewById(R.id.products_name);
                this.itemImage = (ImageView) view.findViewById(R.id.image2);
                this.cardView = (CardView) view.findViewById(R.id.cardview);

                mDynoRegular = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto_Regular.ttf");
                tvTitle.setTypeface(mDynoRegular);

            }

        }
    }

    private void getRecipeDetails() {
        RequestQueue queue = Volley.newRequestQueue(Recipe_Youtube_Activity.this);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, Constants.GET_RECIPE_DETAIL + recipe_id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("getRecipeDetails", String.valueOf(response));
                        tab1.setVisibility(View.VISIBLE);

                        try {


                            JSONObject JObject = new JSONObject(String.valueOf(response));
                            Recipe recipe = null;
                            recipeArrayList = new ArrayList<Recipe>();


                            JSONObject jsonObject = JObject.getJSONObject("data");
                            String description = jsonObject.getString("description");
                            String servepeople = jsonObject.getString("servepeople");
                            String cooking_time = jsonObject.getString("cooking_time");
                            String cooking_step = jsonObject.getString("cooking_step");
                            String recipe_link = jsonObject.getString("recipe_link");
                            String namepass[] = recipe_link.split("=");
                            String name = namepass[0];
                            video_id = namepass[1];
                            Log.e("name",name);
                            Log.e("pass",video_id);

                                 /*   recipe = new Recipe();
                                    recipe.setServing(servepeople);
                                    recipe.setCookingTime(cooking_time);
                                    recipe.setQuick_Info(description);
                                    recipe.setCooking_Steps(cooking_step);
                                    recipe.setVideo_Url(recipe_link);

                                    recipeArrayList.add(recipe);*/
                            mservings.setText(servepeople + "\n" + "Servings");
                            mCooking.setText(cooking_time + "\n" + "mins for cooking");
                            mPrepare.setText(cooking_time + "\n" + "mins to prepare");

                            textview1.setText(cooking_step);
                            textview.setText(description);
                            JSONArray recipe_product = jsonObject.getJSONArray("recipe_product");


                            if (recipe_product.length() != 0) {


                                for (int i = 0; i < recipe_product.length(); i++)
                                {
                                    JSONObject jsonObject1 = recipe_product.getJSONObject(i);
                                    JSONObject jsonObject2=jsonObject1.getJSONObject("product");
                                    String product_name = jsonObject2.getString("product_name");
                                    String display_image = jsonObject2.getString("display_image");
                                    recipe = new Recipe();
                                    recipe.setTitle(product_name);
                                    recipe.setImage(display_image);
                                    recipeArrayList.add(recipe);
                                }

                               Recipe_Detail_Adapter adapter = new Recipe_Detail_Adapter(Recipe_Youtube_Activity.this, recipeArrayList);

                                ingedients_recyclerview.setLayoutManager(new LinearLayoutManager(Recipe_Youtube_Activity.this, LinearLayoutManager.VERTICAL, false));
                                ingedients_recyclerview.setVisibility(View.VISIBLE);
                                ingedients_recyclerview.setAdapter(adapter);

                            }
                            else
                            {
                                Toast.makeText(Recipe_Youtube_Activity.this, "No Ingredients ", Toast.LENGTH_LONG).show();

                            }



                            mIngredients.setText(recipe_product.length()+ "\n" + "Ingredients");

                            cooking_steps.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textview.setVisibility(View.VISIBLE);
                                    textview1.setVisibility(View.GONE);
                                    mAddtoBasket.setVisibility(View.GONE);
                                    ingedients_recyclerview.setVisibility(View.GONE);
                                    tab3.setVisibility(View.VISIBLE);
                                    tab2.setVisibility(View.GONE);
                                    tab1.setVisibility(View.GONE);


                                }
                            });
                            quick_info.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textview1.setVisibility(View.VISIBLE);
                                    textview.setVisibility(View.GONE);
                                    mAddtoBasket.setVisibility(View.GONE);
                                    ingedients_recyclerview.setVisibility(View.GONE);
                                    tab2.setVisibility(View.VISIBLE);
                                    tab1.setVisibility(View.GONE);
                                    tab3.setVisibility(View.GONE);
                                }
                            });
                            ingredients_title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textview1.setVisibility(View.GONE);
                                    textview.setVisibility(View.GONE);
                                    mAddtoBasket.setVisibility(View.VISIBLE);
                                    ingedients_recyclerview.setVisibility(View.VISIBLE);

                                    tab1.setVisibility(View.VISIBLE);
                                    tab2.setVisibility(View.GONE);
                                    tab3.setVisibility(View.GONE);
                                }
                            });





/*

                            Recipe_Detail_Adapter adapter = new Recipe_Detail_Adapter(getActivity(), recipeArrayList);

                            products_listview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                            products_listview.setAdapter(adapter);
*/


                        } catch (Exception e) {
                            Log.i("Err", e.getLocalizedMessage());
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof TimeoutError) {
                    Toast.makeText(Recipe_Youtube_Activity.this, "Connection was timeout. Please check your internet connection ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(Recipe_Youtube_Activity.this, "Please check your internet connection or server is not connected", Toast.LENGTH_LONG).show();

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

    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
   /* private void initializeYoutubePlayer() {

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment = (YouTubePlayerSupportFragment)getActivity(). getSupportFragmentManager()
                .findFragmentById(R.id.container_fragment);

        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize(Constants.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                   // youTubePlayer = player;

                    //set the player style default
                   // youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    //cue the 1st video by default
                   // youTubePlayer.cueVideo("27oHn30Az4E");
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
                Log.e("sfd", "Youtube Player View initialization failed");
            }
        });
    }
*/
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }*/

   /* protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }*/
    public class Recipe_Detail_Adapter extends RecyclerView.Adapter<Recipe_Detail_Adapter.MyViewHolder> {

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

                mAddtoCArt.setVisibility(View.GONE);
            }
        }

        public Recipe_Detail_Adapter(Context mContext, ArrayList<Recipe> recipeArrayList) {
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
            // Glide.with(mContext).load(Constants.PRODUCT_IMAGES + recipe.getImage()).into(holder.prod_image);


            Glide.with(mContext).load(Constants.PRODUCT_IMAGES + recipe.getImage()).transform(new CircularTransform(mContext)).placeholder(R.drawable.logo).into(holder.prod_image);

          /*  holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recipe_Fragment newFragment = new Recipe_Fragment();
                    Bundle args = new Bundle();
                    args.putInt("position", recipe.getRecipeTypeId());
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
*/

        }

        @Override
        public int getItemCount() {
            return recipeArrayList.size();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(video_id); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(Recipe_Youtube_Activity.this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(Recipe_Youtube_Activity.this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constants.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


}

