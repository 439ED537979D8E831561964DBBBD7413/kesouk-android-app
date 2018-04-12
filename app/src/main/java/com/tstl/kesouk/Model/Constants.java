package com.tstl.kesouk.Model;

/**
 * Created by user on 10-Jan-18.
 */

public class Constants {
    public static String LOCAL_DOMAIN="http://192.168.1.3/dukan/public/api";
    public static String END_DOMAIN="http://139.59.19.12/kesouk";
    public static String DOMAIN="http://139.59.19.12.xip.io/kesouk/api";
    public static String DOMAIN1="http://139.59.19.12/dukan/api";


    public static final String REGISTER = DOMAIN+"/createaccount";
    public static final String GET_CITIES = DOMAIN+"/getcities";
    public static final String RESEND_OTP = DOMAIN+"/resend";
    public static final String VERIFY_OTP = DOMAIN+"/verifyotp";
    public static final String FORGET_PASSWORD = DOMAIN+"/passwordupdate";
    public static final String LOGOUT = DOMAIN+"/logout";
    public static final String FB_LOGIN = DOMAIN+"/fblogin";
    public static final String GOOGLE_LOGIN = DOMAIN+"/googlelogin";
    final String GETCATEGORY_SUBCATEGORY = DOMAIN+"/getcategoryandsubcategory";
    public static final String GETCATEGORY = DOMAIN+"/getcategory";
    public static final String GET_SECTIONS = DOMAIN+"/getsections";
    public static final String CATEGORY_IMAGES = END_DOMAIN+"/assets/category/";
    public static final String RECIPE_IMAGES = END_DOMAIN+"/assets/recipe/";
    public static final String SIMILAR_PRODUCTS_IMAGES = END_DOMAIN+"/assets/products/";
    public static final String  PRODUCT_IMAGES = END_DOMAIN+"/assets/products/";
    public static final String GET_PRODUCT_LIST = DOMAIN+"/getproductlist";
    public static final String GET_QUANTITY_TYPE = DOMAIN+"/getquantitytype";
    public static final String GET_RECIPETYPE_CATEGORY = DOMAIN+"/getrecipetypecategory";
    public static final String GET_RECIPEBASED_CATEGORY = DOMAIN+"/getrecipebasedcategory?category_id=";
    public static final String GET_RECIPE_DETAIL = DOMAIN+"/getrecipedetail?recipe_id=";
    public static final String GET_SIMILAR_RECIPES_PRODUCTS = DOMAIN+"/getsimilarrecipes?recipe_id";
    public static final String GET_PRODUCT_DETAILS = DOMAIN+"/getproductbyid";
    public static final String GET_SIMILAR_PRODUCTS = DOMAIN+"/getsimilarproducts";
    public static final String ADDTOWISHLIST = DOMAIN+"/addtowishlist";
    public static final String GETWISHLIST = DOMAIN+"/getwishlist";
    public static final String  WISHLISTTO_CUSTOMER = DOMAIN+"/wishlisttocustomer";
    public static final String REMOVE_WISHLIST = DOMAIN+"/removewishlist";
    public static final String ADDTOCART = DOMAIN+"/addtocart";
    public static final String GET_CART = DOMAIN+"/getcart";
    public static final String CART_QUANTITY = DOMAIN+"/carquantitychange";
    public static final String REMOVE_CART = DOMAIN+"/removecart";
    public static final String CARTTOCUSTOMER = DOMAIN+"/carttocustomer";
    public static final String YOUTUBE_API_KEY = "AIzaSyBemHlbdeE2fNF76w5vxjlNwH6x0hO3HSI";
    public static final String UPDATE_PROFILE = DOMAIN+"/updateprofile";
    public static final String UPDATE_ADDRESS = DOMAIN+"/updateaddress";
    public static final String ADD_NEW_ADDRESS = DOMAIN+"/addaddress";
    public static final String GET_USER_ADDRESS = DOMAIN+"/getuseraddress?customer_id=";
    public static final String ADD_RECEIPETOCART = DOMAIN+"/addrecipetocart";


    public static final String BANNER_IMAGES = END_DOMAIN+"/assets/homepage/banner";
    public static final String GET_BANNER_IMAGE = DOMAIN+"/banner";


    public static int MY_SOCKET_TIMEOUT_MS = 20000;
}
