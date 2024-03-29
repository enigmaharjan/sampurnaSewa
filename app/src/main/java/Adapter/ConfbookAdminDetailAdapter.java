package Adapter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.AdminActivity;
import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.UpdateProfile;
import com.example.sampurnasewaagile.ViewProfile;

import java.util.List;

import Api.Api;
import Model.Booking;
import Model.Booking2;
import Model.BookingResponse;
import Model.User;
import Model.User2;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfbookAdminDetailAdapter extends RecyclerView.Adapter<ConfbookAdminDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    String username;

    public ConfbookAdminDetailAdapter(Context mcontext, List<Booking> bookList) {
        this.mcontext = mcontext;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.confbook, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder detailsViewHolder, int i) {
        final Booking booking = bookList.get(i);
        Retrofit retrofit = Url.getInstance();
        String userid = booking.getUserid();
        Api api = retrofit.create(Api.class);
        Call<List<User>> listCall = api.getusername(userid);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                for (User user : list) {
                    username = user.getUsername();
                    detailsViewHolder.tvjuser.setText(username);

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        detailsViewHolder.tvjobName.setText(booking.getJobname());
        detailsViewHolder.tvjdate.setText(booking.getJobdate());
        detailsViewHolder.tvjtime.setText(booking.getJobtime());
        detailsViewHolder.tvjprob.setText(booking.getJobproblem());
        detailsViewHolder.tvjuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                String userid = booking.getUserid();
                Call<List<User>> userCall = api.getuser(userid);
                userCall.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        List<User> list = response.body();
                        for (User user2 : list) {
                            String name = user2.getName();
                            String username = user2.getUsername();
                            String email = user2.getEmail();
                            String phone = user2.getPhone();
                            String address = user2.getAddress();
                            String image = user2.getImagename();
                            Toast.makeText(mcontext, "" + image, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mcontext, ViewProfile.class);
                            intent.putExtra("image", image);
                            intent.putExtra("name", name);
                            intent.putExtra("username", username);
                            intent.putExtra("email", email);
                            intent.putExtra("phone", phone);
                            intent.putExtra("address", address);
                            mcontext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(mcontext, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        final String bid = booking.getBookid();
        detailsViewHolder.btncompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                String bookid = bid;
                String completed = "1";
                String feedback = "null";
                Booking2 booking2 = new Booking2(bookid, completed, feedback);
                Call<BookingResponse> listCall = api.completedbook(booking2);
                listCall.enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        BookingResponse bookingResponse = response.body();
                        if (bookingResponse.getMessage().equals("Success")) {
                            Toast.makeText(mcontext, "Completed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mcontext, "lol", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        Toast.makeText(mcontext, "aa" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate, tvjtime, tvjprob, tvjuser;
        Button btncompleted;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvalljobName);
            tvjdate = itemView.findViewById(R.id.tvalljdate);
            tvjtime = itemView.findViewById(R.id.tvalljtime);
            tvjprob = itemView.findViewById(R.id.tvalljprob);
            tvjuser = itemView.findViewById(R.id.tvallUserid);
            btncompleted = itemView.findViewById(R.id.btncompleted);
        }

    }
}
