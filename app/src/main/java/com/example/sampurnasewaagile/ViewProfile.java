package com.example.sampurnasewaagile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfile extends AppCompatActivity {
    private TextView upemail,upuname,upname,upphone,upaddress,upnamehead;
    String un,uun,ue,up,ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        upname=findViewById(R.id.upname);
        upnamehead=findViewById(R.id.upunamehead);
        upuname=findViewById(R.id.upuname);
        upemail=findViewById(R.id.upemail);
        upphone=findViewById(R.id.upphone);
        upaddress=findViewById(R.id.upaddress);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            un=(bundle.getString("name"));
            uun=(bundle.getString("username"));
            ue=(bundle.getString("email"));
            up=(bundle.getString("phone"));
            ua=(bundle.getString("address"));
        }
        upname.setText(un);
        upemail.setText(ue);
        upuname.setText(uun);
        upphone.setText(up);
        upaddress.setText(ua);
        upnamehead.setText(uun);
        upphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewProfile.this, "Dialpad will open soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+up));
                startActivity(intent);
            }
        });
    }
}
