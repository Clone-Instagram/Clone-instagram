package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.navigation.DetailViewFragment;
import com.example.instagram.navigation.UserFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    public CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    int drawable_checked=1;
    String dbId;
    String dbpw;
    EditText input_pw;
    EditText input_Id;
    private Object FacebookCallback;
    Button facebook_login1;
    Button regist;
    Button login_btn;

    Fragment userFragment = new UserFragment();

    String nick;
    private static final String TAG = "MainActivity";

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_pw= (EditText) findViewById(R.id.input_pw);
        input_Id = (EditText) findViewById(R.id.input_Id);
        login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //아래 링크를 파라미터를 넘겨준다는 의미
                new JSONTask().execute("http://13.125.61.188:8080/android_login");

            }
        });

        facebook_login1 = (Button) findViewById(R.id.facebook_login);
        facebook_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

        regist = (Button) findViewById(R.id.regist);
        regist.setOnClickListener(new Button.OnClickListener()
         {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                 startActivity(intent);
             }

         });



        input_pw.setOnTouchListener(new View.OnTouchListener() {
            int Drawable_Right=2;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=(input_pw.getRight()-input_pw.getCompoundDrawables()[Drawable_Right].getBounds().width())){
                        if(drawable_checked==1){
                            input_pw.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visible_eye,0);
                            input_pw.setInputType(InputType.TYPE_CLASS_TEXT);
                            drawable_checked=2;
                        }
                        else{
                            input_pw.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.hidden_eye,0);
                            input_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            drawable_checked=1;
                        }
                        return true;
                    }
                }

                return false;
            }
        });

        //printHashKey();
        //Q8nYiOdt/yhBIiPse6ZgAV5upo8=
        callbackManager = CallbackManager.Factory.create();

    }

    public class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

//                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");

                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행


                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray jarray = new JSONArray(result);
                String id = null;
                String password = null;
                String nickname = null;
                String[] arrayID = new String[jarray.length()];
                String[] arrayPassword = new String[jarray.length()];
                String[] arrayNickname = new String[jarray.length()];
                Log.d(TAG, "@@@@@@@: "+result);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    id = jObject.optString("id");
                    password = jObject.optString("password");
                    nickname = jObject.optString("nickname");

                    arrayID[i] = id;
                    arrayPassword[i] = password;
                    arrayNickname[i] = nickname;

                        if((input_Id.getText().toString().equals(arrayID[i])) && (input_pw.getText().toString().equals(arrayPassword[i]))) {
                            Log.d(TAG, "결과값 " + arrayID[i]+":"+arrayPassword[i]);

                            nick = arrayNickname[i];

                            Log.d(TAG, "onPostExecute: nick" + nick);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name",input_Id.getText().toString());
                            intent.putExtra("nickname", nick.toString());
                            startActivity(intent);
                        }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }

    }
    public void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    moveMainPage(task.getResult().getUser());
                }
                else {
                    Toast.makeText(LoginActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void moveMainPage(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
