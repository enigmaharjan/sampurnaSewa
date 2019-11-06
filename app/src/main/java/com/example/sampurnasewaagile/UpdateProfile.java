package com.example.sampurnasewaagile;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Api.Api;
import Model.ImageResponse;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Url.Url.BASE_URL;

public class UpdateProfile extends AppCompatActivity {
    private EditText uptname, uptuname, uptemail, uptpassword, uptaddress, uptphone;
    private Button btnedit, btncancel;
    private ImageView upprofileImage;
    //    String name, username, email, password, address, phone,
    String imagePath, imgname,img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        uptname = findViewById(R.id.uptname);
        uptuname = findViewById(R.id.uptuname);
        uptemail = findViewById(R.id.uptemail);
        uptpassword = findViewById(R.id.uptpassword);
        uptaddress = findViewById(R.id.uptaddress);
        uptphone = findViewById(R.id.uptphone);
        btncancel = findViewById(R.id.btncancel);
        btnedit = findViewById(R.id.btnedit);
        btncancel= findViewById(R.id.btncancel);
        upprofileImage = findViewById(R.id.upprofileImage);
        checkPermission();
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfile.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");
        Api api = Url.getInstance().create(Api.class);
        Call<List<User>> userCall = api.getuser(userid);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                for (User user2 : list) {
                    String name = user2.getName();
                    String username = user2.getUsername();
                    String email = user2.getEmail();
//                    String password = user2.getPassword();
                    String phone = user2.getPhone();
                    String address = user2.getAddress();
                    uptname.setText(name);
                    uptuname.setText(username);
                    uptemail.setText(email);
                    uptpassword.setText("");
                    uptaddress.setText(address);
                    uptphone.setText(phone);
                    imgname = user2.getImagename();
                    final String imgPath = BASE_URL + "uploads/" + imgname;
                    imagePath = imgPath;
                    StrictMode();
                    try {
                        java.net.URL url = new java.net.URL(imgPath);
                        upprofileImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            private void StrictMode(){
                android.os.StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                android.os.StrictMode.setThreadPolicy(policy);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        upprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }

        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    update();
                }
            }
        });

    }

    private void BrowseImage() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(UpdateProfile.this, "Please Select An Image", Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode != RESULT_OK){
            return;
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        previewImage(imagePath);
//        imgCheck = 1;   //it is done if you have to update something in this fi
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(UpdateProfile.this, uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String imagPath) {
        File imgFile = new File(imagPath);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            upprofileImage.setImageBitmap(bitmap);

        }
    }

    private void SaveImageOnly(){
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(),requestBody);

        Api imageAPI = Url.getInstance().create(Api.class);
        Call<ImageResponse> call = imageAPI.uploadImage(body);
        StrictMode();
        try{
            Response<ImageResponse> imageResponseResponse = call.execute();
            img = imageResponseResponse.body().getFilename();
        } catch (IOException e) {
//            Toast.makeText(UpdateProfile.this, "Error " + e , Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    private  void checkPermission() {
        if (ContextCompat.checkSelfPermission(UpdateProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UpdateProfile.this, new String[]
                    {
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 0);
        }
    }

    private void update() {
        SaveImageOnly();
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
        String imagename = img;

        User user = new User(userid, name, username, email, password, address, phone, imagename);
        Call<RegisterResponse> listCall = api.updateuser(user);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(UpdateProfile.this, "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateProfile.this, UserActivity.class);
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

    private boolean isEmpty() {
        if (TextUtils.isEmpty((uptpassword.getText().toString()))) {
            uptpassword.setError("Please enter Name");
            uptpassword.requestFocus();
            return true;
        } else return false;
    }

}
