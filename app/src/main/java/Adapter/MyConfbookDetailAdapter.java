package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampurnasewaagile.FeedbackActivity;
import com.example.sampurnasewaagile.R;

import java.util.List;

import Model.Booking;

public class MyConfbookDetailAdapter extends RecyclerView.Adapter<MyConfbookDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;

    public MyConfbookDetailAdapter(Context mcontext, List<Booking> bookList) {
        this.mcontext = mcontext;
        this.bookList = bookList;
    }
    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myconfbook, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder detailsViewHolder, int i) {
        final  Booking booking =bookList.get(i);
        detailsViewHolder.bookid=booking.getBookid();
        detailsViewHolder.tvjobName.setText(booking.getJobname());
        detailsViewHolder.tvjdate.setText(booking.getJobdate());
        detailsViewHolder.tvjtime.setText(booking.getJobtime());
        detailsViewHolder.tvjprob.setText(booking.getJobproblem());
        detailsViewHolder.tvj.setText(booking.getFeedback());
        if (booking.getCompleted().equals("0")){
            detailsViewHolder.tvjstatus.setText("Pending");
        }else {
            detailsViewHolder.tvjstatus.setText("Completed");
        }
        if (detailsViewHolder.tvjstatus.getText().equals("Completed")&& detailsViewHolder.tvj.getText().equals("null")) {
            detailsViewHolder.btnfeedback.setVisibility(View.VISIBLE);
            detailsViewHolder.btnfeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mcontext, FeedbackActivity.class);
                    intent.putExtra("bookid", booking.getBookid());
                    mcontext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob,tvjstatus,btnfeedback,tvj;
        String bookid;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvjobName);
            tvjdate = itemView.findViewById(R.id.tvjdate);
            tvjtime = itemView.findViewById(R.id.tvjtime);
            tvjprob = itemView.findViewById(R.id.tvjprob);
            tvjstatus = itemView.findViewById(R.id.tvjstatus);
            btnfeedback = itemView.findViewById(R.id.btnfeedback);
            tvj = itemView.findViewById(R.id.tvj);
        }

    }
}
