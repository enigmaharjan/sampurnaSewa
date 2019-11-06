package Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.UserActivity;

import Api.Api;
import Model.LoginResponse;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginPage extends Fragment {
    private EditText etemailLogin,etPasswordLogin;
    private TextView btnLogin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_login_page, container, false);

        etemailLogin = view.findViewById(R.id.etemailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    checkUser();
                }
            }
        });
        return view;
    }

    private void checkUser() {

        final String email = etemailLogin.getText().toString();
        final String password = etPasswordLogin.getText().toString();
        final Api api = Url.getInstance().create(Api.class);
        final User2 user2 = new User2(email, password);

        Call<LoginResponse> call = api.getResponse(user2);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.isStatus()) {
                    String id = (response.body().getUserid());
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userid", id);
                    editor.apply();
                    Toast.makeText(getContext(), "Welcome " , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Invalid id or pw", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), "fail "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etemailLogin.getText().toString()))) {
            etemailLogin.setError("Please enter email");
            etemailLogin.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPasswordLogin.getText().toString()))) {
            etPasswordLogin.setError("Please enter Password");
            etPasswordLogin.requestFocus();
            return true;
        }
        return false;
    }
}