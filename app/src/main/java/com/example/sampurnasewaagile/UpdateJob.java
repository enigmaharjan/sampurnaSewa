package com.example.sampurnasewaagile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.Browser;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import Api.Api;
import Model.ImageResponse;
import Model.Job;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Url.Url.BASE_URL;

public class UpdateJob extends AppCompatActivity {
    private EditText jname, jdetail, mincharge;
    private TextView upt_job;
    private Spinner avail;
    private String joname, jodetail, mcharge, jimg, imagePath,joid,img;
    private ImageView ujobImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job);
        jname = findViewById(R.id.ujobname);
        jdetail = findViewById(R.id.ujobdetail);
        mincharge = findViewById(R.id.uminimumcharge);
        avail = findViewById(R.id.uavail);
        upt_job = findViewById(R.id.upt_job);
        ujobImage=findViewById(R.id.ujobImage);
        checkPermission();
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            joid = bundle.getString("jobid");
            joname = (bundle.getString("jobname"));
            jodetail = (bundle.getString("jobdetail"));
            mcharge = (bundle.getString("min"));
            jimg = (bundle.getString("img"));

        }
        jname.setText(joname);
        jdetail.setText(jodetail);
        mincharge.setText(mcharge);
        final String imgPath = BASE_URL + "uploads/" + jimg;
        imagePath = imgPath;
        StrictMode();
        try {
            java.net.URL url = new java.net.URL(imgPath);
            ujobImage.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ujobImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        upt_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatejob();

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
                    Toast.makeText(UpdateJob.this, "Please Select An Image", Toast.LENGTH_SHORT).show();
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
            CursorLoader loader = new CursorLoader(UpdateJob.this, uri,projection,null,null,null);
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
                ujobImage.setImageBitmap(bitmap);

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
                Toast.makeText(UpdateJob.this, "Error " + e , Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        private  void checkPermission() {
            if (ContextCompat.checkSelfPermission(UpdateJob.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(UpdateJob.this, new String[]
                        {
                                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 0);
            }
        }

    private void updatejob() {
        SaveImageOnly();
        Api api = Url.getInstance().create(Api.class);
        final String jobid = joid;
        final String jobname = jname.getText().toString();
        String jobdetail= jdetail.getText().toString();
        String minimumcharge = mincharge.getText().toString();
        String availability = avail.getSelectedItem().toString();
        String jobimage = img;
        Job job= new Job(jobid, jobname, jobdetail, minimumcharge,jobimage,availability);
        Call<RegisterResponse> listCall = api.updatejob(job);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("success")) {
                    Toast.makeText(UpdateJob.this, "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateJob.this,AdminActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdateJob.this, "sorry", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(UpdateJob.this, "failed to update", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
        }
