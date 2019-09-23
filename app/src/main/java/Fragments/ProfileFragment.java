package Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.UpdateProfile;
import com.example.sampurnasewaagile.ViewPagerActivity;

import java.util.List;

import Api.Api;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private TextView btnlogout;
    private TextView tvUName, tvUuserName, tvUEmail, tvUAddress, tvUphone,usernameProf;
    private Button btnEditProfile;
    String name, tvUN, tvUE, tvUp, tvUA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate((R.layout.fragment_profile), container, false);
        usernameProf = view.findViewById(R.id.usernameProf);
        tvUName = view.findViewById(R.id.tvUName);
        tvUuserName = view.findViewById(R.id.tvUuserName);
        tvUEmail = view.findViewById(R.id.tvUEmail);
        tvUAddress = view.findViewById(R.id.tvUAddress);
        tvUphone = view.findViewById(R.id.tvUphone);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnlogout = view.findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");


        Api api = Url.getInstance().create(Api.class);
        Call<List<User2>> userCall = api.getuser(userid);
        userCall.enqueue(new Callback<List<User2>>() {
            @Override
            public void onResponse(Call<List<User2>> call, Response<List<User2>> response) {
                List<User2> list =response.body();
                for(User2 user2: list){
                    name=user2.getName();
                    tvUN=user2.getUsername();
                    tvUE=user2.getEmail();
                    tvUp=user2.getPhone();
                    tvUA=user2.getAddress();
                    usernameProf.setText(tvUN);
                    tvUName.setText(name);
                    tvUuserName.setText(tvUN);
                    tvUEmail.setText(tvUE);
                    tvUAddress.setText(tvUp);
                    tvUphone.setText(tvUA);
                }
            }

            @Override
            public void onFailure(Call<List<User2>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), UpdateProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
