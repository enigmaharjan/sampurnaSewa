package com.example.sampurnasewaagile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
                    Toast.makeText(SplashActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }
}
