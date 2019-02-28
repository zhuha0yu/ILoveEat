package com.example.ILoveEat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Food implements Serializable {
    private String imageurl;
    private String foodid;
    private String foodname;
    private Integer foodprice;
    private ArrayList<String> commentid;
    private Float sweet;
    private Float spicy;
    private Float salty;
    private Float overall;
    private Float asia;
    private Float europe;
    public Food() {
        commentid=null;
        imageurl = null;
        foodid = null;
        foodname = null;
        foodprice = 100;
    }

    public Food(String imageurl, String foodid, String foodname, Integer foodprice,ArrayList<String> commentid,Float sweet,Float spicy,Float salty,Float overall) {
        this.imageurl = imageurl;
        this.foodid = foodid;
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.commentid=commentid;
        this.sweet=sweet;
        this.spicy=spicy;
        this.salty=salty;
        this.overall=overall;
    }

    public Float getAsia() {
        return asia;
    }

    public Float getEurope() {
        return europe;
    }

    public void setAsia(Float asia) {
        this.asia = asia;
    }

    public void setEurope(Float europe) {
        this.europe = europe;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setFoodprice(Integer foodprice) {
        this.foodprice = foodprice;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setOverall(Float overall) {
        this.overall = overall;
    }

    public Float getOverall() {
        return overall;
    }

    public Float getSweet() {
        return sweet;
    }

    public Float getSpicy() {
        return spicy;
    }

    public Float getSalty() {
        return salty;
    }

    public void setSalty(Float salty) {
        this.salty = salty;
    }

    public void setSpicy(Float spicy) {
        this.spicy = spicy;
    }

    public void setSweet(Float sweet) {
        this.sweet = sweet;
    }

    public ArrayList<String> getCommentid() {
        return commentid;
    }

    public void setCommentid(ArrayList<String> commentid) {
        this.commentid = commentid;
    }

    public String getFoodname() {
        return foodname;
    }

    public String getFoodid() {
        return foodid;
    }

    public Integer getFoodprice() {
        return foodprice;
    }

    public String getImageurl() {
        return imageurl;
    }


}
