package com.tstl.kesouk.Model;

import java.util.ArrayList;

/**
 * Created by user on 18-Jan-18.
 */

public class SingleItemModel {


    private String name;
    private int category;
    private String url;
    private String description;

    public ArrayList<Integer> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    private ArrayList<Integer> arrayList;

    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;
    }

    int discount_price;

    public String getProduct_price_id() {
        return product_price_id;
    }

    public void setProduct_price_id(String product_price_id) {
        this.product_price_id = product_price_id;
    }

    private String product_price_id;
    public static SingleItemModel instance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getCatgOrProd() {
        return CatgOrProd;
    }

    public void setCatgOrProd(int catgOrProd) {
        CatgOrProd = catgOrProd;
    }

    private int CatgOrProd;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;


    public String getKesoukPrice() {
        return kesoukPrice;
    }

    public void setKesoukPrice(String kesoukPrice) {
        this.kesoukPrice = kesoukPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    private String kesoukPrice;
    private String sellingPrice;
    private int disPrice;

    public static SingleItemModel getInstance() {
        if (instance == null)
            instance = new SingleItemModel();
        return instance;
    }
    public SingleItemModel() {
    }

    public SingleItemModel(String name, String url,int id,String product_price_id,int CatgOrProd) {
        this.name = name;
        this.url = url;
        this.id=id;
        this.CatgOrProd=CatgOrProd;
        this.product_price_id=product_price_id;
    }
    public SingleItemModel(String name, String url, int id, String product_price_id, int CatgOrProd,int quantity, String kesoukPrice,String sellingPrice,ArrayList<Integer> arrayList) {
        this.name = name;
        this.url = url;
        this.id=id;
        this.CatgOrProd=CatgOrProd;
        this.product_price_id=product_price_id;
        this.arrayList=arrayList;
        this.quantity=quantity;
        this.kesoukPrice=kesoukPrice;
        this.sellingPrice=sellingPrice;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}