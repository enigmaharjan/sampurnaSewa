package Fragments;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.UpdateProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Api.Api;
import Model.Booking;
import Model.User;
import Url.Url;
import channel.CreateChannel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static Url.Url.BASE_URL;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private TextView tvUName, tvUuserName, tvUEmail, tvUAddress, tvUphone,btnEditProfile;
    String name, tvUN, tvUE, tvUp, tvUA,profileImageName,imagePath;
    private ImageView imageProfile;
    int number,number2;
    NotificationManagerCompat notificationManagerCompact;
    String confirmation;
    String completed,lol;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate((R.layout.fragment_profile), container, false);
        tvUName = view.findViewById(R.id.tvUName);
        tvUuserName = view.findViewById(R.id.tvUuserName);
        tvUEmail = view.findViewById(R.id.tvUEmail);
        tvUAddress = view.findViewById(R.id.tvUAddress);
        tvUphone = view.findViewById(R.id.tvUphone);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        imageProfile = view.findViewById(R.id.profileImage);
        notificationManagerCompact= NotificationManagerCompat.from(getContext());
        CreateChannel channel = new CreateChannel(getContext());
        channel.createChannel();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");
        final String sharedNumber1 = sharedPreferences.getString("number1","0");
        final String sharedNumber2 = sharedPreferences.getString("number2","0");

        Api api = Url.getInstance().create(Api.class);
         confirmation="1";
         completed="0";
        Call<List<Booking>> listCall=api.getcompleted(userid,confirmation,completed);
        listCall.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                List<Booking> bookingList=response.body();
                for (Booking booking :bookingList){
                    number += 1;
                }
                if (Integer.parseInt(sharedNumber1)<number) {
                    Notification notification = new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                            .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                            .setContentTitle("Sampurna Sewa")
                            .setContentText("Your Booking has been confirmed")
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();
                    notificationManagerCompact.notify(1, notification);
                } else {
                    lol="lol";
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {

            }
        });
        completed="2";
        confirmation="1";
        Call<List<Booking>> listCall1=api.getcompleted(userid,confirmation,completed);
        listCall1.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                List<Booking> bookingList=response.body();
                for (Booking booking :bookingList){
                    number2 += 1;
                }
        if (Integer.parseInt(sharedNumber2)<number){
            Notification notification2 = new NotificationCompat.Builder(getContext(), CreateChannel.CHANNEL_1)
                    .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                    .setContentTitle("Sampurna Sewa")
                    .setContentText("Your Booking has been Rejected")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManagerCompact.notify(2, notification2);
        } else {
            lol="lol";
        }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {

            }
        });

        Call<List<User>> userCall = api.getuser(userid);
        userCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                for (User user : list) {
                    name = user.getName();
                    profileImageName = user.getImagename();
                    tvUN = user.getUsername();
                    tvUE = user.getEmail();
                    tvUp = user.getPhone();
                    tvUA = user.getAddress();
                    tvUName.setText(name);
                    tvUuserName.setText(tvUN);
                    tvUEmail.setText(tvUE);
                    tvUAddress.setText(tvUA);
                    tvUphone.setText(tvUp);
                    final String imgPath = BASE_URL + "uploads/" + profileImageName;
                    imagePath = imgPath;
                    StrictMode();
                    try {
                        java.net.URL url = new java.net.URL(imgPath);
                        imageProfile.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }
        private void StrictMode(){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }