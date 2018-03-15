package com.tstl.kesouk.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 26-Oct-17.
 */

public class Browse_Category implements Serializable {

    public static Products instance;
    public static Products getInstance() {
        if (instance == null)
            instance = new Products();
        return instance;
    }


    public int getQuantitycart_text() {
        return quantitycart_text;
    }

    public void setQuantitycart_text(int quantitycart_text) {
        this.quantitycart_text = quantitycart_text;
    }

    int quantitycart_text;

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    int data_id;


    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    int cart_quantity;
    public String getCart_price_id() {
        return cart_price_id;
    }

    public void setCart_price_id(String cart_price_id) {
        this.cart_price_id = cart_price_id;
    }

    String cart_price_id;

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    String seller_id;
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    int categoryId;
    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    private int thumbnail,product_quantity_type;
    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
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

    private String prod_name;

    public int getProduct_quantity_type() {
        return product_quantity_type;
    }

    public void setProduct_quantity_type(int product_quantity_type) {
        this.product_quantity_type = product_quantity_type;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    private String product_image;

    public String getCart_productId() {
        return cart_productId;
    }

    public void setCart_productId(String cart_productId) {
        this.cart_productId = cart_productId;
    }

    private String cart_productId;

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    private String product_desc;

    public Browse_Category(String name, String marketPrice,String dukanPrice, int thumbnail) {
        this.prod_name = name;
        this.marketPrice = marketPrice;
        this.dukanPrice = dukanPrice;
        this.thumbnail = thumbnail;
    }

   public Browse_Category()
   {

   }

    private String marketPrice;
    private String dukanPrice;

    public String getSimilar_category() {
        return similar_category;
    }

    public void setSimilar_category(String similar_category) {
        this.similar_category = similar_category;
    }

    public String getSimilar_subcategory() {
        return similar_subcategory;
    }

    public void setSimilar_subcategory(String similar_subcategory) {
        this.similar_subcategory = similar_subcategory;
    }

    public String getSimilar_id() {
        return similar_id;
    }

    public void setSimilar_id(String similar_id) {
        this.similar_id = similar_id;
    }

    private String similar_category;
    private String similar_subcategory;
    private String similar_id;

    public String getProduct_random_id() {
        return product_random_id;
    }

    public void setProduct_random_id(String product_random_id) {
        this.product_random_id = product_random_id;
    }

    private String product_random_id;



    public String getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(String quantityId) {
        this.quantityId = quantityId;
    }



    private String quantityId;

    public String getIs_express_delivery() {
        return is_express_delivery;
    }

    public void setIs_express_delivery(String is_express_delivery) {
        this.is_express_delivery = is_express_delivery;
    }

    String is_express_delivery;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    private String totalAmount;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private String discount;


    public float getRatingStar() {
        return RatingStar;
    }

    public void setRatingStar(float ratingStar) {
        RatingStar = ratingStar;
    }

    private float RatingStar;

    public String getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(String cartProductId) {
        this.cartProductId = cartProductId;
    }

    private String cartProductId;

    public String getProduct_priceId() {
        return product_priceId;
    }

    public void setProduct_priceId(String product_priceId) {
        this.product_priceId = product_priceId;
    }

    private String product_priceId;
    private String product_qty_name;
    public String getProduct_qty_name() {
        return product_qty_name;
    }

    public void setProduct_qty_name(String product_qty_name) {
        this.product_qty_name = product_qty_name;
    }


    public ArrayList<String> getProduct_sliding() {
        return product_sliding;
    }

    public void setProduct_sliding(ArrayList<String> product_sliding) {
        this.product_sliding = product_sliding;
    }

    private ArrayList<String> product_sliding;


    public int getHorizontal_category() {
        return horizontal_category;
    }

    public void setHorizontal_category(int horizontal_category) {
        this.horizontal_category = horizontal_category;
    }

    private int horizontal_category;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    private int product_id;

    public Browse_Category getProduct_item_onlclick() {
        return product_item_onlclick;
    }

    public void setProduct_item_onlclick(Browse_Category product_item_onlclick) {
        this.product_item_onlclick = product_item_onlclick;
    }

    public Browse_Category product_item_onlclick;

    private String product_selling_price;

    public int getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(int product_discount) {
        this.product_discount = product_discount;
    }

    private int product_discount;


    public String getProduct_selling_price() {
        return product_selling_price;
    }

    public void setProduct_selling_price(String product_selling_price) {
        this.product_selling_price = product_selling_price;
    }


}
