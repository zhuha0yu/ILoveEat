package com.example.ILoveEat;

public class Message_commentliked extends Message {
    private String uid = null;
    private int liketype = 0;

    public Message_commentliked() {

    }

    public Message_commentliked(String uid) {
        this.uid = uid;
    }

    public Message_commentliked(String uid, int liketype) {

        this.uid = uid;
        this.liketype = liketype;
    }

    public String getUid() {
        return uid;
    }

    public int getLiketype() {
        return liketype;
    }

    public void setLiketype(int liketype) {
        this.liketype = liketype;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
