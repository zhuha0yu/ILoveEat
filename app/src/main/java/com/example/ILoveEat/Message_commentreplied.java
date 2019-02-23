package com.example.ILoveEat;

public class Message_commentreplied extends Message {
    private String uid = null;
    private String replymessage = null;

    public Message_commentreplied() {

    }

    public Message_commentreplied(String uid, String replymessage) {
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
