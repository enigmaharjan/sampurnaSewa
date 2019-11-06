package com.example.sampurnasewaagile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import Api.Api;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");
        Api api = Url.getInstance().create(Api.class);
        Call<List<User>> userCall = api.getuser(userid);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                for (User user : list) {
                    name = user.getUsername();
                 }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                final String text = sharedPreferences.getString("userid", "");
                if (text.equals("null")) {
                    Intent intent = new Intent(SplashActivity.this, ViewPagerActivity.class);
                    startActivity(intent);
                    finish();
                }else if (text.equals("admin")) {
                        Intent intent = new Intent(SplashActivity.this, AdminActivity.class);
                        startActivity(intent);
                    Toast.makeText(SplashActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, UserActivity.class);
                    Toast.makeText(SplashActivity.this, "Welcome Back "+ name, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }
}
