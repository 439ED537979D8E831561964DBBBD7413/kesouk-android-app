package com.tstl.kesouk.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.SingleItemModel;
import com.tstl.kesouk.R;

import java.util.ArrayList;

/**
 * Created by user on 18-Jan-18.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    private String transaction;


    public SectionListDataAdapter(Context context,String transaction, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.transaction = transaction;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());
        Glide.with(mContext)
                .load(Constants.CATEGORY_IMAGES + singleItem.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.logo)
                .into(holder.itemImage);


       /* if(transaction.equals("category"))
        {
           Glide.with(mContext)
                    .load(Constants.CATEGORY_IMAGES + singleItem.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(R.drawable.logo)
                    .into(holder.itemImage);

        }else if(transaction.equals("product")) {
           Glide.with(mContext)
                    .load(Constants.CATEGORY_IMAGES + singleItem.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .error(R.drawable.logo)
                    .into(holder.itemImage);

        }
*/
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.products_name);
            this.itemImage = (ImageView) view.findViewById(R.id.image2);
            mDynoRegular = Typeface.createFromAsset(mContext.getAssets(), "font/Roboto_Regular.ttf");
            tvTitle.setTypeface(mDynoRegular);

        }

    }

}
