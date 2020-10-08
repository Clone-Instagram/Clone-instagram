package com.example.instagram;

import android.graphics.Bitmap;

public class UploadData {

    private String post_image;
    private String post_user_name;
    private String post_text;
    private String sysdata;

    public UploadData(String post_image, String post_text) {
        this.post_image = post_image;
        this.post_text = post_text;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }


    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }
}
