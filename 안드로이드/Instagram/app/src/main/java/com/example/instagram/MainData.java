package com.example.instagram;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MainData {

    private Bitmap post_image;
    private String post_user_name;
    private String post_text;
    private String sysdata;

    public MainData(Bitmap post_image, String post_user_name, String post_text, String sysdata) {
        this.post_image = post_image;
        this.post_user_name = post_user_name;
        this.post_text = post_text;
        this.sysdata = sysdata;
    }

    public Bitmap getPost_image() { return post_image; }

    public void setPost_image(Bitmap post_image) {
        this.post_image = post_image;
    }

    public String getPost_user_name() { return post_user_name; }

    public void setPost_user_name(String post_user_name) {
        this.post_user_name = post_user_name;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getSysdata() {
        return sysdata;
    }

    public void setSysdata(String sysdata) {
        this.sysdata = sysdata;
    }
}