package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CompleteJoinActivity extends AppCompatActivity {

    Button complete_Login;

    String id;
    String phone_Id;
    String Name;
    String Password;
    String nick_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_join);

        Intent intent = getIntent(); /*데이터 수신*/
        id = intent.getExtras().getString("id"); /*String형*/
        phone_Id = intent.getStringExtra("phone_Id");

        System.out.println("id value:"+id);
        System.out.println("phone_Id value:"+phone_Id);

        Name = intent.getExtras().getString("name");
        nick_Name = intent.getExtras().getString("nickname");
        Password = intent.getExtras().getString("password");

        complete_Login = (Button) findViewById(R.id.complete_Login);

        complete_Login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                new JSONTask().execute("http://13.125.61.188:8080/signup_process");

            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                if(id != null) {
                    jsonObject.accumulate("name",Name);
                    jsonObject.accumulate("id",id);
                    jsonObject.accumulate("password",Password);
                    jsonObject.accumulate("nickname",nick_Name);
                }
                if(phone_Id != null) {
                    jsonObject.accumulate("id",phone_Id);
                    jsonObject.accumulate("name",Name);
                    jsonObject.accumulate("password",Password);
                    jsonObject.accumulate("nickname",nick_Name);
                }
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송


                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("name",id);
            startActivity(intent);
        }
    }
}