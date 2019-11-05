package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import java.util.List;

import Api.Api;
import Model.Booking;
import Model.Booking2;
import Model.BookingResponse;
import Model.Feedback;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking2> booking2s;
    String username;

    public FeedbackAdapter(Context mcontext, List<Booking2> booking2s) {
        this.mcontext = mcontext;
        this.booking2s = booking2s;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder detailsViewHolder, int i) {
        final Booking2 booking2 = booking2s.get(i);
        Retrofit retrofit = Url.getInstance();
        String userid = booking2.getUserid();
        Api api = retrofit.create(Api.class);
        Call<List<User>> listCall = api.getusername(userid);
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> list = response.body();
                for (User user : list) {
                    username= user.getUsername();
                    detailsViewHolder.tvunamef.setText(username);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        detailsViewHolder.tvfeedback.setText(booking2.getFeedback());
        detailsViewHolder.tvjnamef.setText(booking2.getJobname());
        }

    @Override
    public int getItemCount() {
        return booking2s.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvfeedback,tvjnamef,tvunamef;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfeedback = itemView.findViewById(R.id.tvfeed);
            tvjnamef= itemView.findViewById(R.id.tvjnamef);
            tvunamef= itemView.findViewById(R.id.tvunamef);
        }

    }
}
