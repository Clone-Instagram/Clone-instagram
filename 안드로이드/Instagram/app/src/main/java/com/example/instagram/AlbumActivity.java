package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    ImageView post_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        post_image2 = (ImageView) findViewById(R.id.post_image2);

        if (requestCode == REQUEST_CODE) {
            if (requestCode == RESULT_OK) {
                try {

                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    post_image2.setImageBitmap(img);

                    Intent intent = new Intent(getApplicationContext(), AddPhotoActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                }
            }
        } else if (requestCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
        }
    }
}