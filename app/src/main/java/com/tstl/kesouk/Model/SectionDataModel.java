package com.tstl.kesouk.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 18-Jan-18.
 */

public class SectionDataModel implements Serializable {


    public static SectionDataModel getInstance() {
        if (instance == null)
            instance = new SectionDataModel();
        return instance;
    }

    public static SectionDataModel instance;
    private String headerTitle;
    private String transaction;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(int adStatus) {
        this.adStatus = adStatus;
    }

    int adStatus;
    private String caption;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SectionDataModel() {

    }

    public SectionDataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}