package com.tstl.kesouk.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tstl.kesouk.Fragments.Recipe_category_list_Fragment;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Recipe;
import com.tstl.kesouk.Model.SingleItemModel;
import com.tstl.kesouk.R;

import java.util.ArrayList;

/**
 * Created by user on 25-Jan-18.
 */

public class Recipes_Adapter extends RecyclerView.Adapter<Recipes_Adapter.SingleItemRowHolder> {

    private ArrayList<Recipe> itemsList;
    private Context mContext;
    private String transaction;


    public Recipes_Adapter(Context context, ArrayList<Recipe> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {

        final Recipe recipe = itemsList.get(i);

        holder.tvTitle.setText(recipe.getTitle());
        Glide.with(mContext)
                .load(Constants.CATEGORY_IMAGES + recipe.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.logo)
                .into(holder.itemImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recipe_category_list_Fragment newFragment = new Recipe_category_list_Fragment();
                Bundle args = new Bundle();
                args.putInt("position", recipe.getRecipeTypeId());
                newFragment.setArguments(args);

                FragmentTransaction transaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.rldContainer,newFragment);
                transaction.addToBackStack("Some String");
                transaction.commit();



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
