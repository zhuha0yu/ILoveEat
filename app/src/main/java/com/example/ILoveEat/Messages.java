package com.example.ILoveEat;

import java.util.Date;

class Messages {
    private int messagetype = 0;
    private Date messagetime = null;
    private Message2 receivedmessage = null;

    Messages() {

    }

    public Messages(int messagetype, Date messagetime, Message2 receivedmessage) {
        this.messagetype = messagetype;
        this.messagetime = messagetime;
        this.receivedmessage = receivedmessage;

    }

    public Date getMessagetime() {
        return messagetime;
    }

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetime(Date messagetime) {
        this.messagetime = messagetime;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

    public void setReceivedmessage(Message2 receivedmessage) {
        this.receivedmessage = receivedmessage;
    }

    public Message2 getReceivedmessage() {
        return receivedmessage;
    }
}
