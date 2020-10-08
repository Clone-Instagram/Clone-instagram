package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinEmailActivity extends AppCompatActivity {
    Button next2;
    Button num2;
    EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_email);

        Email = (EditText) findViewById(R.id.Email);
        next2 = (Button) findViewById(R.id.next2);
        num2 = (Button) findViewById(R.id.num2);

        num2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });



        next2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NamePasswordActivity.class);
                intent.putExtra("id",Email.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}