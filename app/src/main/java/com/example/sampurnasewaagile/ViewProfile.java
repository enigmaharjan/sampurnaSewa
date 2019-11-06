package com.example.sampurnasewaagile;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;


import static Url.Url.BASE_URL;

public class ViewProfile extends AppCompatActivity {
    private TextView upemail, upuname, upname, upphone, upaddress;
    private ImageView prof;
    String un, uun, ue, up, ua, img, imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        upname = findViewById(R.id.upname);
        upuname = findViewById(R.id.upuname);
        upemail = findViewById(R.id.upemail);
        upphone = findViewById(R.id.upphone);
        upaddress = findViewById(R.id.upaddress);
        prof = findViewById(R.id.prof);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            un = (bundle.getString("name"));
            uun = (bundle.getString("username"));
            ue = (bundle.getString("email"));
            up = (bundle.getString("phone"));
            ua = (bundle.getString("address"));
            img = (bundle.getString("image"));
        }
        upname.setText(un);
        upemail.setText(ue);
        upuname.setText(uun);
        upphone.setText(up);
        upaddress.setText(ua);
        final String imgPath = BASE_URL + "uploads/" + img;
        imagePath = imgPath;
        StrictMode();
        try {
            java.net.URL url = new java.net.URL(imgPath);
            prof.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        upphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+up));
                startActivity(intent);
            }
        });
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
