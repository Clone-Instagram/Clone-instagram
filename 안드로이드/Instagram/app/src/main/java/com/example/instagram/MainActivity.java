package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.instagram.navigation.AddFragment;
import com.example.instagram.navigation.AlarmFragment;
import com.example.instagram.navigation.DetailViewFragment;
import com.example.instagram.navigation.SearchFragment;
import com.example.instagram.navigation.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment detailViewFragment = new DetailViewFragment();
    Fragment searchFragment = new SearchFragment();
    Fragment addFragment = new AddFragment();
    Fragment alarmFragment = new AlarmFragment();
    Fragment userFragment = new UserFragment();

    String imgurl;

    Menu menu;
    String name;

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent(); //데이터 수신
        name = intent.getExtras().getString("name"); //String형

        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        detailViewFragment.setArguments(bundle);


        System.out.println(name + "#####");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);
        menu = bottomNavigationView.getMenu();

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, detailViewFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.action_home:
                        Log.i("Duk", "action_home");

                        menuItem.setIcon(R.drawable.hom2);
                        menu.findItem(R.id.action_heart).setIcon(R.drawable.heart);
                        menu.findItem(R.id.action_account).setIcon(R.drawable.profile);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, detailViewFragment).commit();

                        return true;

                    case R.id.action_search:
                        Log.i("Duk", "action_search");
                        menu.findItem(R.id.action_home).setIcon(R.drawable.home);
                        menu.findItem(R.id.action_heart).setIcon(R.drawable.heart);
                        menu.findItem(R.id.action_account).setIcon(R.drawable.profile);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, searchFragment).commit();
                       return true;

                    case R.id.action_add_photo:
                        menu.findItem(R.id.action_home).setIcon(R.drawable.home);
                        menu.findItem(R.id.action_heart).setIcon(R.drawable.heart);
                        menu.findItem(R.id.action_account).setIcon(R.drawable.profile);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, addFragment).commit();
                        return true;

                    case R.id.action_heart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, alarmFragment).commit();
                        menuItem.setIcon(R.drawable.heart2);
                        menu.findItem(R.id.action_home).setIcon(R.drawable.home);
                        menu.findItem(R.id.action_account).setIcon(R.drawable.profile);
                        return true;

                    case R.id.action_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, userFragment).commit();
                        menuItem.setIcon(R.drawable.profile2);
                        menu.findItem(R.id.action_home).setIcon(R.drawable.home);
                        menu.findItem(R.id.action_heart).setIcon(R.drawable.heart);
                        return true;
                }
                return false;
            }
        });
    }
    public void setimgurl(String imgurl) { this.imgurl = imgurl;}
    public String getimgurl() { return imgurl; }
}
