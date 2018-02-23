package com.tstl.kesouk.Model;

import java.io.Serializable;

/**
 * Created by user on 18-Jan-18.
 */

public class Home_Products implements Serializable {


    public String getProd1() {
        return prod1;
    }

    public void setProd1(String prod1) {
        this.prod1 = prod1;
    }

    public String getProd2() {
        return prod2;
    }

    public void setProd2(String prod2) {
        this.prod2 = prod2;
    }

    public String getProd3() {
        return prod3;
    }

    public void setProd3(String prod3) {
        this.prod3 = prod3;
    }

    private String prod1;
    private String prod2;
    private String prod3;
    private String prod4;
    public static Home_Products instance;

    public Integer getProd() {
        return prod;
    }

    public void setProd(Integer prod) {
        this.prod = prod;
    }

    private Integer prod;

    public Integer getProd_cateogry() {
        return prod_cateogry;
    }

    public void setProd_cateogry(Integer prod_cateogry) {
        this.prod_cateogry = prod_cateogry;
    }

    private Integer prod_cateogry;

    public String getProd4() {
        return prod4;
    }

    public void setProd4(String prod4) {
        this.prod4 = prod4;
    }

    public String getProd5() {
        return prod5;
    }

    public void setProd5(String prod5) {
        this.prod5 = prod5;
    }

    public String getProd6() {
        return prod6;
    }

    public void setProd6(String prod6) {
        this.prod6 = prod6;
    }

    private String prod5;
    private String prod6;

    public String getImage_Banner() {
        return image_Banner;
    }

    public void setImage_Banner(String image_Banner) {
        this.image_Banner = image_Banner;
    }

    private String image_Banner;

    public static Home_Products getInstance() {
        if (instance == null)
            instance = new Home_Products();
        return instance;
    }
}
