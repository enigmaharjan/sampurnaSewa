package Adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.AdminActivity;
import com.example.sampurnasewaagile.R;

import java.util.List;

import Api.Api;
import Model.Booking;
import Model.BookingResponse;
import Model.User;
import Url.Url;
import channel.CreateChannel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllbookAdminDetailAdapter extends RecyclerView.Adapter<AllbookAdminDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    String username;
    NotificationManagerCompat notificationManagerCompact;

    public AllbookAdminDetailAdapter(Context mcontext, List<Booking> bookList) {
        this.mcontext = mcontext;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allbook, viewGroup, false);
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
                    username= user.getUsername();
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
            final String bid = booking.getBookid();
        notificationManagerCompact=NotificationManagerCompat.from(mcontext);
        CreateChannel channel = new CreateChannel(mcontext);
        channel.createChannel();
            detailsViewHolder.btnconf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Api api = Url.getInstance().create(Api.class);
                    String bookid = bid;
                    String confirmation = "1";
                    Booking booking1 = new Booking(bookid, confirmation);
                    Call<BookingResponse> listCall = api.confirmbook(booking1);
                    listCall.enqueue(new Callback<BookingResponse>() {
                        @Override
                        public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                            BookingResponse bookingResponse = response.body();
                            if (bookingResponse.getMessage().equals("Success")) {
                                Toast.makeText(mcontext, "Confirmed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mcontext, AdminActivity.class);
                                mcontext.startActivity(intent);
                                // Builds your notification
                                Notification notification = new NotificationCompat.Builder(mcontext, CreateChannel.CHANNEL_1)
                                        .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                                        .setContentTitle("Sampurna Sewa")
                                        .setContentText("Your Booking has been confirmed")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();
                                notificationManagerCompact.notify(1, notification);


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
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob,tvjuser;
        Button btnconf;
        String bookid;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvalljobName);
            tvjdate = itemView.findViewById(R.id.tvalljdate);
            tvjtime = itemView.findViewById(R.id.tvalljtime);
            tvjprob = itemView.findViewById(R.id.tvalljprob);
            tvjuser=itemView.findViewById(R.id.tvallUserid);
            btnconf=itemView.findViewById(R.id.btnconf);
        }

    }
}
