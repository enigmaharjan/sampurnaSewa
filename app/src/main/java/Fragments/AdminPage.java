package Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.AdminActivity;
import com.example.sampurnasewaagile.R;

import Api.Api;
import Model.Admin;
import Model.LoginResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdminPage extends Fragment {
    private EditText etPasswordLoginadmin, etemailLoginadmin;
    private TextView btnLoginadmin;
    private TextView tvIncorrect;

    public AdminPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_admin_page, container, false);
        etemailLoginadmin = view.findViewById(R.id.etemailLoginadmin);
        etPasswordLoginadmin = view.findViewById(R.id.etPasswordLoginadmin);
        btnLoginadmin = view.findViewById(R.id.btnLoginAdmin);
        btnLoginadmin.setOnClickListener(new View.OnClickListener() {
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

        final String email = etemailLoginadmin.getText().toString();
        final String password = etPasswordLoginadmin.getText().toString();
        Api api = Url.getInstance().create(Api.class);

        Admin admin= new Admin(email,password);

        Call<LoginResponse> call  = api.getAdmin(admin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                {
                    if (response.body().isStatus()) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userid", "admin");
                        editor.apply();
                        Toast.makeText(getContext(), "Welcome Admin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), "sorry "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty((etemailLoginadmin.getText().toString()))) {
            etemailLoginadmin.setError("Please enter email");
            etemailLoginadmin.requestFocus();
            return true;
        } else if (TextUtils.isEmpty((etPasswordLoginadmin.getText().toString()))) {
            etPasswordLoginadmin.setError("Please enter Password");
            etPasswordLoginadmin.requestFocus();
            return true;
        }
        return false;
    }
}