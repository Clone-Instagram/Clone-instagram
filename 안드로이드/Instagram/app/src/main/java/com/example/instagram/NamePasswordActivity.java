package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NamePasswordActivity extends AppCompatActivity {


    Button button3;

    String email;
    String phone;

    EditText Name;
    EditText Password;
    EditText nick_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_password);

        Intent intent =getIntent();
        email = intent.getStringExtra("id");
        phone = intent.getStringExtra("phone_Id");

        Name = (EditText) findViewById(R.id.Name);
        Password = (EditText) findViewById(R.id.Password);
        nick_Name = (EditText) findViewById(R.id.nick_Name);

        button3 = (Button) findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CompleteJoinActivity.class);
                if(email != null) {
                    intent.putExtra("id", email);
                    intent.putExtra("name",Name.getText().toString());
                    intent.putExtra("nickname",nick_Name.getText().toString());
                    intent.putExtra("password",Password.getText().toString());
                    startActivity(intent);
                }
                if(phone != null){
                    intent.putExtra("phone_Id",phone);
                    intent.putExtra("name",Name.getText().toString());
                    intent.putExtra("nickname",nick_Name.getText().toString());
                    intent.putExtra("password",Password.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }
}