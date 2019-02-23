package com.example.ILoveEat;

public class Message_2_commentreplied extends Message2 {
    private String uid = null;
    private String replymessage = null;

    public Message_2_commentreplied() {

    }

    public Message_2_commentreplied(String uid, String replymessage) {
        this.uid = uid;
        this.replymessage = replymessage;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getReplymessage() {
        return replymessage;
    }

    public void setReplymessage(String replymessage) {
        this.replymessage = replymessage;
    }
}
