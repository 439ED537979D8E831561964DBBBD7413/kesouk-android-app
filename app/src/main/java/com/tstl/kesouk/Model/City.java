package com.tstl.kesouk.Model;

/**
 * Created by user on 05-Feb-18.
 */


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 08-Nov-17.
 */

public class City implements Serializable {
    public static City instance;
    public static City getInstance() {
        if (instance == null)
            instance = new City();
        return instance;
    }

    public String getmCity_name() {
        return mCity_name;
    }

    public void setmCity_name(String mCity_name) {
        this.mCity_name = mCity_name;
    }

    String mCity_name;

    public String getmArea_name() {
        return mArea_name;
    }

    public void setmArea_name(String mArea_name) {
        this.mArea_name = mArea_name;
    }

    String mArea_name;

    public ArrayList<String> getmAreaList() {
        return mAreaList;
    }

    public void setmAreaList(ArrayList<String> mAreaList) {
        this.mAreaList = mAreaList;
    }

    private ArrayList<String> mAreaList;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    double lat;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    double longitude;
}
