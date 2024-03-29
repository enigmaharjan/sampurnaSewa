package Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import java.io.File;
import java.io.IOException;

import Api.Api;
import Model.ImageResponse;
import Model.Job;
import Model.JobResponse;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddJobFrag extends Fragment {
    private EditText jname, jdetail, mincharge;
    private TextView add_job;
    private Spinner avail;
    private ImageView jobImage;
    private String imagePath, jobimg;

    public AddJobFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_job, container, false);
        jname = view.findViewById(R.id.jobname);
        jdetail = view.findViewById(R.id.jobdetail);
        mincharge = view.findViewById(R.id.minimumcharge);
        add_job = view.findViewById(R.id.add_job);
        avail = view.findViewById(R.id.avail);
        jobImage = view.findViewById(R.id.jobImage);
        checkPermission();
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    addjob();
                }
            }


        });
        jobImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        return view;
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
                Toast.makeText(getActivity(), "Please Select An Image", Toast.LENGTH_SHORT).show();
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
        CursorLoader loader = new CursorLoader(getActivity(), uri,projection,null,null,null);
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
            jobImage.setImageBitmap(bitmap);

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
            jobimg = imageResponseResponse.body().getFilename();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error " + e , Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    private  void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 0);
        }
    }
    private void addjob() {
        SaveImageOnly();
        Api api = Url.getInstance().create(Api.class);
        final String jobname = jname.getText().toString();
        final String jobid="00000000000000000";
        final String jobdetail = jdetail.getText().toString();
        final String minimumcharge = mincharge.getText().toString();
        final String availability = avail.getSelectedItem().toString();
        String jobimage = jobimg;
        Job job = new Job(jobid,jobname, jobdetail, minimumcharge, jobimage, availability);
        Call<JobResponse> call = api.addjob(job);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                JobResponse jobResponse = response.body();
                if (jobResponse.getMessage().equals("Successfully Registered")) {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    jname.setText("");
                    jdetail.setText("");
                    mincharge.setText("");
                } else {
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Sorry", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isEmpty() {
        if (TextUtils.isEmpty((jname.getText().toString()))) {
            jname.setError("Please enter job Name");
            jname.requestFocus();
            return true;
        }else if (TextUtils.isEmpty((jdetail.getText().toString()))) {
            jdetail.setError("Please enter job Detail");
            jdetail.requestFocus();
            return true;
        }
        else if (TextUtils.isEmpty((mincharge.getText().toString()))) {
            mincharge.setError("Please enter job Detail");
            mincharge.requestFocus();
            return true;
        }
        return false;
    }

}

