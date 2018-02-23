package com.tstl.kesouk.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tstl.kesouk.R;

/**
 * Created by user on 18-Jan-18.
 */

public class Home extends Fragment {
    View view;
    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.nav_drawer_home_activity, container, false);
        return view;
    }

}
