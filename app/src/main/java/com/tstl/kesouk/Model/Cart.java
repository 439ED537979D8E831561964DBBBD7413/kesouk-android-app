package com.tstl.kesouk.Model;

/**
 * Created by user on 09-Feb-18.
 */

import java.io.Serializable;

/**
 * Created by user on 18-Nov-17.
 */

public class Cart implements Serializable {
    public static Cart instance;

    public static Cart getInstance() {
        if (instance == null)
            instance = new Cart();
        return instance;
    }
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    float rating;

    public int getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(int actual_amount) {
        this.actual_amount = actual_amount;
    }

    int actual_amount;

    public String getExpressDelivery() {
        return ExpressDelivery;
    }

    public void setExpressDelivery(String expressDelivery) {
        ExpressDelivery = expressDelivery;
    }

    String ExpressDelivery;
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getDukanPrice() {
        return dukanPrice;
    }

    public void setDukanPrice(String dukanPrice) {
        this.dukanPrice = dukanPrice;
    }

    public String getCart_price_id() {
        return cart_price_id;
    }

    public void setCart_price_id(String cart_price_id) {
        this.cart_price_id = cart_price_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getOtherMarketPrice() {
        return otherMarketPrice;
    }

    public void setOtherMarketPrice(String otherMarketPrice) {
        this.otherMarketPrice = otherMarketPrice;
    }

    String otherMarketPrice;
    String product_name;
    String image_url;
    String marketPrice;
    String dukanPrice;
    String cart_price_id;

    public int getQuantity_type() {
        return quantity_type;
    }

    public void setQuantity_type(int quantity_type) {
        this.quantity_type = quantity_type;
    }

    int quantity_type;

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    int discount;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    String emailId;
    String mobilenumber;

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    String discount_price;
    int quantity;
}
