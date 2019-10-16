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
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompbookDetailAdapter extends RecyclerView.Adapter<CompbookDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    public CompbookDetailAdapter(Context mcontext, List<Booking> bookList) {
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
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Booking booking = bookList.get(i);
            detailsViewHolder.tvjobName.setText(booking.getJobname());
            detailsViewHolder.tvjdate.setText(booking.getJobdate());
            detailsViewHolder.tvjtime.setText(booking.getJobtime());
            detailsViewHolder.tvjprob.setText(booking.getJobproblem());
            detailsViewHolder.tvjuser.setText(booking.getUserid());
        }

    @Override
    public int getItemCount() {
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob,tvjuser;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvalljobName);
            tvjdate = itemView.findViewById(R.id.tvalljdate);
            tvjtime = itemView.findViewById(R.id.tvalljtime);
            tvjprob = itemView.findViewById(R.id.tvalljprob);
            tvjuser=itemView.findViewById(R.id.tvallUserid);
        }

    }
}
