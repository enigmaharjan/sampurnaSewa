package com.example.sampurnasewaagile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LauchFirst extends AppCompatActivity {
    SharedPreferences loginPreference;
    String MY_PREF = "my_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPreference = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        if (loginPreference.getString("tag", "notok").equals("notok")) {
            SharedPreferences.Editor edit = loginPreference.edit();
            edit.putString("tag", "ok");
            edit.commit();
            SharedPreferences sharedPreferences = this.getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userid", "null");
            editor.commit();
//            setContentView(R.layout.first);
            Intent i = new Intent(LauchFirst.this, FirstSplash.class);
            startActivity(i);
            finish();

        } else if (loginPreference.getString("tag", null).equals("ok")) {
            Intent i = new Intent(LauchFirst.this, SplashActivity.class);
            startActivity(i);
            finish();
        }
    }
}
