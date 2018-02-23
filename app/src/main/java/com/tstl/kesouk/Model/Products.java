package com.tstl.kesouk.Model;

import java.io.Serializable;

/**
 * Created by user on 18-Jan-18.
 */
public class Products implements Serializable {

    private String header_Name, subcategoryName, category_image, category_icon;
    private Integer subCategoryId;
    private Integer productId;
    private Integer statusId;
    public static Products instance;


    public String getHeader_Name() {
        return header_Name;
    }

    public void setHeader_Name(String header_Name) {
        this.header_Name = header_Name;
    }


    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public static void setInstance(Products instance) {
        Products.instance = instance;
    }

    public static Products getInstance() {
        if (instance == null)
            instance = new Products();
        return instance;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(String category_icon) {
        this.category_icon = category_icon;
    }
}
