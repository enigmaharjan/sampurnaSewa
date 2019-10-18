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
import android.provider.Browser;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.ViewPagerActivity;

import java.io.File;
import java.io.IOException;

import Api.Api;
import Model.ImageResponse;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RegisterPage extends Fragment {
    private EditText regemail, regpassword, regfullname, regusername, regphoneno, regaddress, regconfpassword;
    private TextView btnSignIn, btnSignUp;
    private ImageView regImage;
    private String imagePath, imgname;

    public RegisterPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_register_page, container, false);
        btnSignUp = view.findViewById(R.id.sign_up_button);
        regemail = view.findViewById(R.id.regemaillog);
        regaddress = view.findViewById(R.id.regaddresslog);
        regfullname = view.findViewById(R.id.regfullnamelog);
        regphoneno = view.findViewById(R.id.regphonenolog);
        regusername = view.findViewById(R.id.regusernamelog);
        regpassword = view.findViewById(R.id.regpasswordlog);
        regconfpassword = view.findViewById(R.id.regconfpasswordlog);
        btnSignIn = view.findViewById(R.id.sign_in_button);
        regImage = view.findViewById(R.id.profileImage);
        checkPermission();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    addUser();
                }
            }
        });
        regImage.setOnClickListener(new View.OnClickListener() {
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
            regImage.setImageBitmap(bitmap);

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
            imgname = imageResponseResponse.body().getFilename();
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

    private void addUser() {
        SaveImageOnly();
        Api api = Url.getInstance().create(Api.class);
        String userid = "10000";
        String name = regfullname.getText().toString();
        String username = regusername.getText().toString();
        String email = regemail.getText().toString();

        String password = regpassword.getText().toString();

        String address = regaddress.getText().toString();
        String phone = regphoneno.getText().toString();
        String imagename = imgname;

        User user = new User(userid, name, username,  email, password, address,phone, imagename);
        Call<RegisterResponse> listCall = api.addUsers(user);
        listCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getMessage().equals("Successfully Registered")) {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(), ViewPagerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Email already registered", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Register fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((regfullname.getText().toString()))) {
            regfullname.setError("Please enter Name");
            regfullname.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regusername.getText().toString()))) {
            regusername.setError("Please enter User Name");
            regusername.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regemail.getText().toString()))) {
            regemail.setError("Please enter Email");
            regemail.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regpassword.getText().toString()))) {
            regpassword.setError("Please enter Password");
            regpassword.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regconfpassword.getText().toString()))) {
            regconfpassword.setError("Please enter Password");
            regconfpassword.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regphoneno.getText().toString()))) {
            regphoneno.setError("Please enter Phone no");
            regphoneno.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((regaddress.getText().toString()))) {
            regaddress.setError("Please enter Address");
            regaddress.requestFocus();
            return true;
        }
        return false;
    }
}