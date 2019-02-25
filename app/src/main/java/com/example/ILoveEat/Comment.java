package com.example.ILoveEat;


import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Comment {
    private int number_of_likes;
    private String commentuserid;
    private String commentid;
    private String imageurl;
    private String commentcontent;
    private String price;
    private Long date;
    private float spicy;
    private float sweet;
    private float salty;
    private float overall;
    public Comment() {

    }
    public Comment(int number_of_likes, String commentuserid, String commentid, String imageurl, String commentcontent, String price, Long date,float spicy,float sweet,float salty,float overall)
    {
        this.date=date;
        this.number_of_likes=number_of_likes;
        this.commentid=commentid;
        this.imageurl=imageurl;
        this.commentuserid=commentuserid;
        this.commentcontent=commentcontent;
        this.price=price;
        this.salty=salty;
        this.spicy=spicy;
        this.sweet=sweet;
        this.overall=overall;

    }

    public void setSweet(float sweet) {
        this.sweet = sweet;
    }

    public void setSpicy(float spicy) {
        this.spicy = spicy;
    }

    public void setOverall(float overall) {
        this.overall = overall;
    }

    public float getOverall() {
        return overall;
    }

    public void setSalty(float salty) {
        this.salty = salty;
    }

    public float getSalty() {
        return salty;
    }

    public float getSpicy() {
        return spicy;
    }

    public float getSweet() {
        return sweet;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getNumber_of_likes() {
        return number_of_likes;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public String getCommentid() {
        return commentid;
    }

    public String getCommentuserid() {
        return commentuserid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public void setCommentuserid(String commentuserid) {
        this.commentuserid = commentuserid;
    }

    public void setNumber_of_likes(int number_of_likes) {
        this.number_of_likes = number_of_likes;
    }
}
