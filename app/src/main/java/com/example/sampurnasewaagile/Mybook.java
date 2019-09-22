package com.example.sampurnasewaagile;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Mybook {
    Context context;
    SharedPreferences sharedPreferences = context.getSharedPreferences("User", MODE_PRIVATE);
     String usid = sharedPreferences.getString("userid", "");
     String userid=usid;

    public Mybook(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
