package com.tstl.kesouk.Fragments;

/**
 * Created by user on 22-Feb-18.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstl.kesouk.R;


public class Gift_Fragments extends Fragment {

    public Gift_Fragments() {
        // Required empty public constructor
    }

    public static Gift_Fragments newInstance(String param1, String param2) {
        Gift_Fragments fragment = new Gift_Fragments();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gift, container, false);
    }

}
