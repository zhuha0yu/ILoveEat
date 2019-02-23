package com.example.ILoveEat;

public class Food {
    private String imageurl;
    private String foodid;
    private String foodname;
    private String foodprice;

    public Food() {
        imageurl = null;
        foodid = null;
        foodname = null;
        foodprice = "1kr";
    }

    public Food(String imageurl, String foodid, String foodname, String foodprice) {
        this.imageurl = imageurl;
        this.foodid = foodid;
        this.foodname = foodname;
        this.foodprice = foodprice;
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
