package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import Api.Api;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends Fragment {
    private EditText regemail, regpassword, regfullname, regusername, regphoneno, regaddress, regconfpassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;

    public RegisterPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_register_page, container, false);
        btnSignUp = view.findViewById(R.id.sign_up_button);
        regemail = view.findViewById(R.id.regemail);
        regaddress = view.findViewById(R.id.regaddress);
        regfullname = view.findViewById(R.id.regfullname);
        regphoneno = view.findViewById(R.id.regphoneno);
        regusername = view.findViewById(R.id.regusername);
        regpassword = view.findViewById(R.id.regpassword);
        regconfpassword = view.findViewById(R.id.regconfpassword);
        btnSignIn = view.findViewById(R.id.sign_in_button);

        progressBar = view.findViewById(R.id.progressbar);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    Api api = Url.getInstance().create(Api.class);
                    String userid = "10000";
                    String name = regfullname.getText().toString();
                    String username = regusername.getText().toString();
                    String password = regpassword.getText().toString();
                    String email = regemail.getText().toString();
                    String phone = regphoneno.getText().toString();
                    String address = regaddress.getText().toString();
                    String imagename = " name";


                    User user = new User(userid, name, username, password, email, phone, address, imagename);
                    Call<RegisterResponse> listCall = api.addUsers(user);
                    listCall.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse.getMessage().equals("Successfully Registered")) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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
            }
        });
        return view;
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