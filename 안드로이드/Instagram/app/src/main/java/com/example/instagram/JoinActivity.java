package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {
    Button email;
    Button next;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        next = (Button) findViewById(R.id.next);
        phone = (EditText)findViewById(R.id.phone);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(phone.getText().length() == 11) {
                    Intent intent = new Intent(getApplicationContext(), NamePasswordActivity.class);
                    intent.putExtra("phone_Id",phone.getText().toString());
                    startActivity(intent);
                }

                else {
                    Toast.makeText(JoinActivity.this, "올바른 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        email = (Button) findViewById(R.id.email);
        email.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinEmailActivity.class);
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