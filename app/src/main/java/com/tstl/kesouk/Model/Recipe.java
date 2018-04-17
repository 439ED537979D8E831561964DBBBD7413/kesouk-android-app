package com.tstl.kesouk.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 25-Jan-18.
 */

public class Recipe implements Serializable {

    public static Recipe instance;
    public static Recipe getInstance() {
        if (instance == null)
            instance = new Recipe();
        return instance;
    }
    private String quanity_kesoukPrice;
    private String quantity_Marketprice;
    private String product_qty_name,productName,discount;

    private String product_price_qty_name;
    public String getProduct_priceId() {
        return product_priceId;
    }

    public void setProduct_priceId(String product_priceId) {
        this.product_priceId = product_priceId;
    }

    private String product_priceId;

    public  ArrayList<String> getQuantityListPojo() {
        return quantityListPojo;
    }

    public  void setQuantityListPojo(ArrayList<String> quantityListPojo) {
        this.quantityListPojo = quantityListPojo;
    }

    public  ArrayList<String> quantityListPojo;

    public ArrayList<String> getPriceListPojo() {
        return priceListPojo;
    }

    public void setPriceListPojo(ArrayList<String> priceListPojo) {
        this.priceListPojo = priceListPojo;
    }

    public ArrayList<String> getActualListPojo() {
        return actualListPojo;
    }

    public void setActualListPojo(ArrayList<String> actualListPojo) {
        this.actualListPojo = actualListPojo;
    }

    public ArrayList<String> priceListPojo;
    public ArrayList<String> actualListPojo;

    public String getProduct_qty_name() {
        return product_qty_name;
    }

    public void setProduct_qty_name(String product_qty_name) {
        this.product_qty_name = product_qty_name;
    }


    public String getProduct_price_qty_name() {
        return product_price_qty_name;
    }

    public void setProduct_price_qty_name(String product_price_qty_name) {
        this.product_price_qty_name = product_price_qty_name;
    }
    public String getQuanity_kesoukPrice() {
        return quanity_kesoukPrice;
    }

    public void setQuanity_kesoukPrice(String quanity_kesoukPrice) {
        this.quanity_kesoukPrice = quanity_kesoukPrice;
    }

    public String getQuantity_Marketprice() {
        return quantity_Marketprice;
    }

    public void setQuantity_Marketprice(String quantity_Marketprice) {
        this.quantity_Marketprice = quantity_Marketprice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String title;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    int categoryId;

    public String getSimilar_title() {
        return similar_title;
    }

    public void setSimilar_title(String similar_title) {
        this.similar_title = similar_title;
    }

    public String getSimilar_image() {
        return similar_image;
    }

    public void setSimilar_image(String similar_image) {
        this.similar_image = similar_image;
    }

    String similar_title;
    String similar_image;
    String image;
    String ingredients;
    String cookingTime;
    String serving;
    String quick_Info;
    String cooking_Steps;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDukanPrice() {
        return dukanPrice;
    }

    public void setDukanPrice(String dukanPrice) {
        this.dukanPrice = dukanPrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    String dukanPrice;
    String marketPrice;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getQuick_Info() {
        return quick_Info;
    }

    public void setQuick_Info(String quick_Info) {
        this.quick_Info = quick_Info;
    }

    public String getCooking_Steps() {
        return cooking_Steps;
    }

    public void setCooking_Steps(String cooking_Steps) {
        this.cooking_Steps = cooking_Steps;
    }

    public String getVideo_Url() {
        return video_Url;
    }

    public void setVideo_Url(String video_Url) {
        this.video_Url = video_Url;
    }

    String video_Url;

    public int getRecipeTypeId() {
        return recipeTypeId;
    }

    public void setRecipeTypeId(int recipeTypeId) {
        this.recipeTypeId = recipeTypeId;
    }


    int recipeTypeId;
}
