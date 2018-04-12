package com.tstl.kesouk.DB;

/**
 * Created by user on 11-Jan-18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 06-May-17.
 */

public class DB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "kesouk";

    // Contacts table name
    private static final String TABLE_ADDTOCART = "addtocarttable";
    private static final String TABLE_WISHLIST = "wishlisttable";
    private static final String TABLE_ADDTOCART_CUST = "addtocarttablecust";
    private static final String TABLE_WISHLISTZ_CUST = "wishlisttablecust";
    private static final String LOGIN_DETAILS = "logindetails";
    private static final String LOGIN_ROLE = "loginrole";
    private static final String LOGIN_COOKIE = "logincookie";
    private static final String DEVICE_TOKEN = "logintoken";
    private static final String REMEMBER_EMAIL = "rememail";
    private static final String REMEMBER_PASSWD = "rempasswd";

    private static final String PROFILE_FNAME = "firstnametbl";
    private static final String PROFILE_LNAME = "lastnametbl";
    private static final String PROFILE_EMAIL = "profileemailtbl";
    private static final String PROFILE_DOB = "dobtbl";
    private static final String PROFILE_MOB = "mobiletbl";
    private static final String PROFILE_LAND = "landlinetbl";
    private static final String PROFILE_PROMOTIONS = "promotionstbl";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATA_ADDTOCART = "addtocart";
    private static final String KEY_DATA_WISHLIST = "wishlist";
    private static final String KEY_DATA_ADDTOCART_CUST = "addtocartcust";
    private static final String KEY_DATA_WISHLIST_CUST = "wishlistcust";
    private static final String KEY_DATA_LOGIN = "login";
    private static final String KEY_DATA_ROLE = "role";
    private static final String KEY_DATA_COOKIE = "cookie";
    private static final String KEY_DATA_TOKEN = "token";
    private static final String KEY_DATA_REM_EMAIL = "email";
    private static final String KEY_DATA_REM_PASSWD = "passwd";

    private static final String KEY_DATA_FNAME = "firstname";
    private static final String KEY_DATA_LNAME = "lastname";
    private static final String KEY_DATA_EMAIL = "profileemail";
    private static final String KEY_DATA_DOB = "dob";
    private static final String KEY_DATA_MOB = "mobile";
    private static final String KEY_DATA_LAND = "landline";
    private static final String KEY_DATA_PROFMOTIONS = "promotions";

    private SQLiteDatabase db;

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE" +" "+ TABLE_ADDTOCART + "("
                + KEY_DATA_ADDTOCART + " INTEGER"
                +  ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_WISHLIST_TABLE = "CREATE TABLE" +" "+ TABLE_WISHLIST + "("
                + KEY_DATA_WISHLIST + " INTEGER"
                +  ")";
        db.execSQL(CREATE_WISHLIST_TABLE);

        String CREATE_ADDTOCART_CUST_TABLE = "CREATE TABLE" +" "+ TABLE_ADDTOCART_CUST + "("
                + KEY_DATA_ADDTOCART_CUST + " INTEGER"
                +  ")";
        db.execSQL(CREATE_ADDTOCART_CUST_TABLE);

        String CREATE_WISHLIST_CUST_TABLE = "CREATE TABLE" +" "+ TABLE_WISHLISTZ_CUST + "("
                + KEY_DATA_WISHLIST_CUST + " INTEGER"
                +  ")";
        db.execSQL(CREATE_WISHLIST_CUST_TABLE);
        String CREATE_LOGIN_DETAILS = "CREATE TABLE" +" "+ LOGIN_DETAILS + "("
                + KEY_DATA_LOGIN + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LOGIN_DETAILS);
        String CREATE_LOGIN_ROLE = "CREATE TABLE" +" "+ LOGIN_ROLE + "("
                + KEY_DATA_ROLE + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LOGIN_ROLE);
        String CREATE_LOGIN_COOKIE = "CREATE TABLE" +" "+ LOGIN_COOKIE + "("
                + KEY_DATA_COOKIE + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LOGIN_COOKIE);
        String CREATE_LOGIN_TOKEN = "CREATE TABLE" +" "+ DEVICE_TOKEN  + "("
                + KEY_DATA_TOKEN + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LOGIN_TOKEN);
        String CREATE_REMEMBER_EMAIL = "CREATE TABLE" +" "+ REMEMBER_EMAIL  + "("
                + KEY_DATA_REM_EMAIL + " INTEGER"
                +  ")";
        db.execSQL(CREATE_REMEMBER_EMAIL);
        String CREATE_REMEMBER_PASSWD = "CREATE TABLE" +" "+ REMEMBER_PASSWD  + "("
                + KEY_DATA_REM_PASSWD + " INTEGER"
                +  ")";
        db.execSQL(CREATE_REMEMBER_PASSWD);



        String CREATE_FNAME = "CREATE TABLE" +" "+ PROFILE_FNAME  + "("
                + KEY_DATA_FNAME + " INTEGER"
                +  ")";
        db.execSQL(CREATE_FNAME);
        String CREATE_LNAME = "CREATE TABLE" +" "+ PROFILE_LNAME  + "("
                + KEY_DATA_LNAME + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LNAME);
        String CREATE_UPDATEEMAIL= "CREATE TABLE" +" "+ PROFILE_EMAIL  + "("
                + KEY_DATA_EMAIL + " INTEGER"
                +  ")";
        db.execSQL(CREATE_UPDATEEMAIL);
        String CREATE_DOB = "CREATE TABLE" +" "+ PROFILE_DOB  + "("
                + KEY_DATA_DOB + " INTEGER"
                +  ")";
        db.execSQL(CREATE_DOB);
        String CREATE_MOBILE = "CREATE TABLE" +" "+ PROFILE_MOB  + "("
                + KEY_DATA_MOB + " INTEGER"
                +  ")";
        db.execSQL(CREATE_MOBILE);
        String CREATE_LANDLINE = "CREATE TABLE" +" "+ PROFILE_LAND  + "("
                + KEY_DATA_LAND + " INTEGER"
                +  ")";
        db.execSQL(CREATE_LANDLINE);
        String CREATE_PROMOTIONS = "CREATE TABLE" +" "+ PROFILE_PROMOTIONS  + "("
                + KEY_DATA_PROFMOTIONS + " INTEGER"
                +  ")";
        db.execSQL(CREATE_PROMOTIONS);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDTOCART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDTOCART_CUST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLISTZ_CUST);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_COOKIE);
        db.execSQL("DROP TABLE IF EXISTS " + DEVICE_TOKEN);
        db.execSQL("DROP TABLE IF EXISTS " + REMEMBER_EMAIL);
        db.execSQL("DROP TABLE IF EXISTS " + REMEMBER_PASSWD);

        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_FNAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_LNAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_EMAIL);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_DOB);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_MOB);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_LAND);
        db.execSQL("DROP TABLE IF EXISTS " + PROFILE_PROMOTIONS);


        // Create tables again
        onCreate(db);
    }
    public String insert(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_ADDTOCART, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(TABLE_ADDTOCART,null,initialValues);
        return id;



    }
    public int insertlogin(int id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_LOGIN, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(LOGIN_DETAILS,null,initialValues);
        return id;

    }

    public String insertRole(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_ROLE, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(LOGIN_ROLE,null,initialValues);
        return id;

    }
    public String insertRemEmail(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_REM_EMAIL, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(REMEMBER_EMAIL,null,initialValues);
        return id;

    }
    public String insertRemPasswd(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_REM_PASSWD, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(REMEMBER_PASSWD,null,initialValues);
        return id;

    }
    public String insertCookie(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_COOKIE, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(LOGIN_COOKIE,null,initialValues);
        return id;

    }
    public String insert_wishlist(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_WISHLIST, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(TABLE_WISHLIST,null,initialValues);
        return id;



    }
    public String insert_addtocart_cust(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_ADDTOCART_CUST, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(TABLE_ADDTOCART_CUST,null,initialValues);
        return id;
    }
    public String insert_wishlist_cust(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_WISHLIST_CUST, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(TABLE_WISHLISTZ_CUST,null,initialValues);
        return id;

    }
    public String insert_profile_fname(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_FNAME, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_FNAME,null,initialValues);
        return id;
    }

    public String insert_profile_lname(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_LNAME, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_LNAME,null,initialValues);
        return id;
    }
    public String insert_profile_email(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_EMAIL, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_EMAIL,null,initialValues);
        return id;
    }
    public String insert_profile_dob(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_DOB, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_DOB,null,initialValues);
        return id;
    }
    public String insert_profile_mob(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_MOB, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_MOB,null,initialValues);
        return id;
    }
    public String insert_profile_landline(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_LAND, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_LAND,null,initialValues);
        return id;
    }
    public String insert_promotions(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_PROFMOTIONS, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(PROFILE_PROMOTIONS,null,initialValues);
        return id;
    }
    public String insert_token(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA_TOKEN, id);
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.insert(DEVICE_TOKEN,null,initialValues);
        return id;
    }

    public String remove(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(TABLE_ADDTOCART," addtocart = '"+id+"'",null);
        return id;

    }
    public void removeRole() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(LOGIN_ROLE,null,null);

    }
    public void removeToken() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(DEVICE_TOKEN,null,null);

    }
    public void removeCookie() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(LOGIN_COOKIE,null,null);

    }
    public void removeLogin() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(LOGIN_DETAILS,null,null);
    }
    public void removeAll() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(TABLE_ADDTOCART,null,null);
    }
    public String remove_wishlist(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(TABLE_WISHLIST," wishlist = '"+id+"'",null);
        return id;

    }
    public void remove_rem_email() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(REMEMBER_EMAIL,null,null);

    }
    public void remove_rem_passwd() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(REMEMBER_PASSWD,null,null);

    }
    public String remove_addtocart_cust(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(TABLE_ADDTOCART_CUST," addtocartcust = '"+id+"'",null);
        return id;



    }

    public String remove_wishlist_cust(String id) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(TABLE_WISHLISTZ_CUST," wishlistcust = '"+id+"'",null);
        return id;

    }


    public void removeFname() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_FNAME,null,null);

    }
    public void removeLname() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_LNAME,null,null);

    }
    public void removeEmail() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_EMAIL,null,null);

    }
    public void removeDob() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_DOB,null,null);

    }
    public void removeMob() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_MOB,null,null);

    }
    public void removeLandline() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_LAND,null,null);

    }
    public void removePromotions() {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       /* ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATA, id);*/
        //db.insert(TABLE_ADDTOCART, null, initialValues);
        sqLiteDatabase.delete(PROFILE_PROMOTIONS,null,null);

    }



    public ArrayList<String> getAllData() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDTOCART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }



        return dataList;
    }

    public ArrayList<String> getAllWishlist() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WISHLIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getAllLogin() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + LOGIN_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }

    public ArrayList<String> getAllRole() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + LOGIN_ROLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }

    public ArrayList<String> getCookie() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + LOGIN_COOKIE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getRemeberPasswd() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + REMEMBER_PASSWD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getRememberEmail() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + REMEMBER_EMAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getToken() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DEVICE_TOKEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }



    public ArrayList<String> getAllAddtocart_cust() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDTOCART_CUST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getAllWishlist_cust() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WISHLISTZ_CUST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }


    public ArrayList<String> getFname() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_FNAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getLname() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_LNAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getProfileEmail() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_EMAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getDob() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_DOB;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getMobile() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_MOB;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getLandline() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_LAND;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
    public ArrayList<String> getPromotions() {
        ArrayList<String> dataList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PROFILE_PROMOTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                dataList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }


        return dataList;
    }
}
