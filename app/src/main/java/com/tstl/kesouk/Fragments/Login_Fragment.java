package com.tstl.kesouk.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.tstl.kesouk.R;

/**
 * Created by user on 17-Jan-18.
 */

public class Login_Fragment extends Fragment {
    View view;
    RecyclerView recycler_view;
    ImageView toolbar_back;
    String name_category,image_url_category;
    public static String selected_product_list;
    public static Login_Fragment newInstance() {
        Login_Fragment fragment = new Login_Fragment();
        return fragment;
    }
    public Login_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_activity, container, false);
       /* toolbar_back = (ImageView) view.findViewById(R.id.img);
        toolbar_back.setVisibility(View.GONE);

*/
        return view;
    }


}