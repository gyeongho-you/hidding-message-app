package com.example.chatapp2023;

public class Chat {
    String email;
    String text;
    String check_hide;
    String chat_time;
    String chat_id;

    public String getChat_id(){return chat_id;}

    public void setChat_id(String chat_id){
        this.chat_id = chat_id;
    }

    public String getCheck_hide(){return check_hide;}

    public void setCheck_hide(String check_hide){
        this.check_hide = check_hide;
    }

    public String getChat_time(){return chat_time;}

    public void setChat_time(String chat_time){
        this.chat_time = chat_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}