package com.example.instagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.instagram.navigation.DetailViewFragment;

public class AddPhotoActivity extends AppCompatActivity {

    private Button back;
    private String strImage;
    ImageView post_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ImageView post_image2 = (ImageView) findViewById(R.id.post_image2);

        strImage = getIntent().getStringExtra("imgId");
        System.out.println(strImage + "사진");
        Glide.with(this).load(strImage).override(1000).into(post_image2);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}