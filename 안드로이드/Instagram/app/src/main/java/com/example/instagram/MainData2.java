package com.example.instagram;

import android.graphics.Bitmap;

public class MainData2 {

    private Bitmap[] myPost;

    public MainData2(Bitmap[] myPost) {
        this.myPost = myPost;
    }

    public Bitmap[] getMyPost() {
        return myPost;
    }

    public void setMyPost(Bitmap[] myPost) {
        this.myPost = myPost;
    }
}
