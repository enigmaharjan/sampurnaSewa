package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampurnasewaagile.Book;
import com.example.sampurnasewaagile.R;

import java.util.List;

import Model.Booking;
import Model.Job;

import static android.content.Context.MODE_PRIVATE;

public class MybookDetailAdapter extends RecyclerView.Adapter<MybookDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;

    public MybookDetailAdapter(Context mcontext, List<Booking> bookList) {
        this.mcontext = mcontext;
        this.bookList = bookList;
    }
    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mybook, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final  Booking booking =bookList.get(i);
        detailsViewHolder.tvjobName.setText(booking.getJobname());
        detailsViewHolder.tvjdate.setText(booking.getJobtime());
        detailsViewHolder.tvjtime.setText(booking.getJobdate());
        detailsViewHolder.tvjprob.setText(booking.getJobproblem());

    }

    @Override
    public int getItemCount() {
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvjobName);
            tvjdate = itemView.findViewById(R.id.tvjdate);
            tvjtime = itemView.findViewById(R.id.tvjtime);
            tvjprob = itemView.findViewById(R.id.tvjprob);

        }

    }
}
