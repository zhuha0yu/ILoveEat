package com.example.ILoveEat;

public class Message_system extends Message
{
private int type=0;
private String text=null;
public Message_system()
{

}
public Message_system(int type,String text)
{
    this.text=text;
    this.type=type;
}

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }
}
