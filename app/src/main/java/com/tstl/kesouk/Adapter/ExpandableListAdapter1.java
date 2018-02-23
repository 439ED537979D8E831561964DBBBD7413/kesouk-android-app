package com.tstl.kesouk.Adapter;

/**
 * Created by user on 18-Jan-18.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tstl.kesouk.Model.Constants;
import com.tstl.kesouk.Model.Products;
import com.tstl.kesouk.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by user on 24-Oct-17.
 */

public class ExpandableListAdapter1 extends BaseExpandableListAdapter {
    private Context _context;
    private Typeface mDynoRegular;
    private ArrayList<Products> productHeaderList; // header titles
    private LinkedHashMap<String, List<Products>> productChildList;

    public ExpandableListAdapter1(Context context, ArrayList<Products> productHeader, LinkedHashMap<String, List<Products>> productChild) {
        this._context = context;
        this.productHeaderList = productHeader;
        this.productChildList = productChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        Products key = this.productHeaderList.get(groupPosition);
        return this.productChildList.get(key.getHeader_Name()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu1);

        //txtListChild.setText(childText);
        mDynoRegular = Typeface.createFromAsset(_context.getAssets(),
                "font/Dyno_Regular.ttf");
        txtListChild.setTypeface(mDynoRegular);


        Products products = (Products) getChild(groupPosition, childPosition);
        txtListChild.setText(products.getSubcategoryName());
        // Log.e("sub_item", products.getSubcategoryName());


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        Products key = this.productHeaderList.get(groupPosition);
        return this.productChildList.get(key.getHeader_Name()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.productHeaderList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.productHeaderList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.iconimage);
        ImageView prod_icon = (ImageView) convertView.findViewById(R.id.prod_image);

        Products product = productHeaderList.get(groupPosition);
       // Glide.with(_context).load(Constants.CATEGORY_IMAGES + product.getCategory_icon()).placeholder(R.drawable.dukan_1small).into(prod_icon);
        mDynoRegular = Typeface.createFromAsset(_context.getAssets(), "font/Dyno_Regular.ttf");
        lblListHeader.setTypeface(mDynoRegular);
        if (isExpanded) {
            img_icon.setImageResource(R.drawable.dukan_minus);
        } else {
            img_icon.setImageResource(R.drawable.dukan_plus);
        }
        lblListHeader.setText(product.getHeader_Name());
        Log.e("header_name", product.getHeader_Name());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
