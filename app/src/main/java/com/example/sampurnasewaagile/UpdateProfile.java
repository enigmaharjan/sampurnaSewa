package com.example.sampurnasewaagile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Api.Api;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    private EditText uptname,uptuname,uptemail,uptpassword,uptaddress,uptphone;
    private Button btnedit,btncancel;
    String name,username,email,password,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        uptname=findViewById(R.id.uptname);
        uptuname=findViewById(R.id.uptuname);
        uptemail=findViewById(R.id.uptemail);
        uptpassword=findViewById(R.id.uptpassword);
        uptaddress=findViewById(R.id.uptaddress);
        uptphone=findViewById(R.id.uptphone);
        btncancel=findViewById(R.id.btncancel);
        btnedit=findViewById(R.id.btnedit);

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");


        Api api = Url.getInstance().create(Api.class);
        Call<List<User2>> userCall = api.getuser(userid);
        userCall.enqueue(new Callback<List<User2>>() {
            @Override
            public void onResponse(Call<List<User2>> call, Response<List<User2>> response) {
                List<User2> list =response.body();
                for(User2 user2: list){
                    name=user2.getName();
                    username=user2.getUsername();
                    email=user2.getEmail();
                    password=user2.getEmail();
                    phone=user2.getPhone();
                    address=user2.getAddress();
                    uptname.setText(name);
                    uptuname.setText(username);
                    uptemail.setText(email);
                    uptpassword.setText(password);
                    uptaddress.setText(address);
                    uptphone.setText(phone);
                }
            }

            @Override
            public void onFailure(Call<List<User2>> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                final String uid = sharedPreferences.getString("userid", "");
                Api api = Url.getInstance().create(Api.class);
                String userid = uid;
                String name = uptname.getText().toString();
                String username = uptuname.getText().toString();
                String email = uptemail.getText().toString();

                String password = uptpassword.getText().toString();

                String address = uptaddress.getText().toString();
                String phone = uptphone.getText().toString();
                String imagename = "no image";

                User user = new User(userid, name, username,  email, password, address,phone, imagename);
                Call<RegisterResponse> listCall = api.updateuser(user);
                listCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse = response.body();
                        if (registerResponse.getMessage().equals("success")) {
                            Toast.makeText(UpdateProfile.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UpdateProfile.this, UserActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateProfile.this, "sorry", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(UpdateProfile.this, "failed to update", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}
