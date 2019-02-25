package com.example.ILoveEat;



class Messages {
    private long messagetype = 0;
    private long messagetime;
    private String messagecontent;
    private String uid;
    private String originaluid;
    private String messageid;
    private String username;
    public Messages() {

    }

    public Messages(long messagetype, long messagetime, String messagecontent,String uid,String originaluid,String messageid,String username) {
        this.messagetype = messagetype;
        this.messagetime = messagetime;
        this.username=username;
        this.messagecontent=messagecontent;
        this.uid=uid;
        this.messageid=messageid;
        this.originaluid=originaluid;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getOriginaluid() {
        return originaluid;
    }

    public void setOriginaluid(String originaluid) {
        this.originaluid = originaluid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }




    public void setMessagetime(long messagetime) {
        this.messagetime = messagetime;
    }

    public void setMessagetype(long messagetype) {
        this.messagetype = messagetype;
    }

    public long getMessagetime() {
        return messagetime;
    }

    public long getMessagetype() {
        return messagetype;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

}
