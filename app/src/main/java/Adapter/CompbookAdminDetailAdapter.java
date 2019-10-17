package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampurnasewaagile.R;

import java.util.List;

import Api.Api;
import Model.Booking;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompbookAdminDetailAdapter extends RecyclerView.Adapter<CompbookAdminDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    String username;

    public CompbookAdminDetailAdapter(Context mcontext, List<Booking> bookList) {
        this.mcontext = mcontext;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.compbook, viewGroup, false);
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
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate, tvjtime, tvjprob, tvjuser;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvalljobName);
            tvjdate = itemView.findViewById(R.id.tvalljdate);
            tvjtime = itemView.findViewById(R.id.tvalljtime);
            tvjprob = itemView.findViewById(R.id.tvalljprob);
            tvjuser = itemView.findViewById(R.id.tvallUserid);
        }

    }
}
