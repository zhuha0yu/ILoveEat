package com.example.ILoveEat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Food implements Serializable {
    private String imageurl;
    private String foodid;
    private String foodname;
    private String foodprice;
    private ArrayList<String> commentid;
    private float sweet;
    private float spicy;
    private float salty;
    private double overall;
    public Food() {
        commentid=null;
        imageurl = null;
        foodid = null;
        foodname = null;
        foodprice = "1kr";
    }

    public Food(String imageurl, String foodid, String foodname, String foodprice,ArrayList<String> commentid,int sweet,int spicy,int salty,int overall) {
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

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public void setFoodprice(String foodprice) {
        this.foodprice = foodprice;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    public double getOverall() {
        return overall;
    }

    public float getSweet() {
        return sweet;
    }

    public float getSpicy() {
        return spicy;
    }

    public float getSalty() {
        return salty;
    }

    public void setSalty(float salty) {
        this.salty = salty;
    }

    public void setSpicy(float spicy) {
        this.spicy = spicy;
    }

    public void setSweet(float sweet) {
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

    public String getFoodprice() {
        return foodprice;
    }

    public String getImageurl() {
        return imageurl;
    }


}
