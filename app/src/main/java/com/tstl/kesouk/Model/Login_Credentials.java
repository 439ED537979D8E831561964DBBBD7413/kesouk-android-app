package com.tstl.kesouk.Model;

/**
 * Created by user on 21-Feb-18.
 */

import java.io.Serializable;

public class Login_Credentials implements Serializable{
    public  String mRole;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String firstname;
    public String lastname;
    public String city;
    public String country;
    public String district;
    public String location;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String house;
    public String area;

    public int getDefault_address() {
        return default_address;
    }

    public void setDefault_address(int default_address) {
        this.default_address = default_address;
    }

    public int default_address;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String contact;
    public String country_code;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String addId;
    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String cookie;

    public String getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String customer_Id;

    public String getmResetEmailId() {
        return mResetEmailId;
    }

    public void setmResetEmailId(String mResetEmailId) {
        this.mResetEmailId = mResetEmailId;
    }

    public String mResetEmailId;

    public String getmSignUpEmailId() {
        return mSignUpEmailId;
    }

    public void setmSignUpEmailId(String mSignUpEmailId) {
        this.mSignUpEmailId = mSignUpEmailId;
    }

    public String mSignUpEmailId;

    public int getmNewUserRegistration() {
        return mNewUserRegistration;
    }

    public void setmNewUserRegistration(int mNewUserRegistration) {
        this.mNewUserRegistration = mNewUserRegistration;
    }

    int mNewUserRegistration;

    public int getmForgotResendOTp() {
        return mForgotResendOTp;
    }

    public void setmForgotResendOTp(int mForgotResendOTp) {
        this.mForgotResendOTp = mForgotResendOTp;
    }

    int mForgotResendOTp;

    public int getmForgotPassword() {
        return mForgotPassword;
    }

    public void setmForgotPassword(int mForgotPassword) {
        this.mForgotPassword = mForgotPassword;
    }

    int mForgotPassword;

    public String getmReason() {
        return mReason;
    }

    public void setmReason(String mReason) {
        this.mReason = mReason;
    }

    public String mReason;
    public static Login_Credentials instance;

    public String getmLogin_Status() {
        return mLogin_Status;
    }

    public void setmLogin_Status(String mLogin_Status) {
        this.mLogin_Status = mLogin_Status;
    }

    public String mLogin_Status;
    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public static Login_Credentials getInstance() {
        if (instance == null)
            instance = new Login_Credentials();
        return instance;
    }

}
