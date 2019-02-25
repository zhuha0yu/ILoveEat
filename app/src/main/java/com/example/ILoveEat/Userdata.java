package com.example.ILoveEat;

public class Userdata {
String username;
String uid;
String imagepath;
public Userdata()
{

}
public Userdata(String username, String uid, String imagepath)
{
    this.uid=uid;
    this.username=username;
    this.imagepath=imagepath;
}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
