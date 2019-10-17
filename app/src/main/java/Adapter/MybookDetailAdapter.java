package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.BookUpdate;
import com.example.sampurnasewaagile.UserActivity;
import com.example.sampurnasewaagile.R;

import java.util.List;

import Api.Api;
import Model.Booking;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        detailsViewHolder.tvjdate.setText(booking.getJobdate());
        detailsViewHolder.tvjtime.setText(booking.getJobtime());
        detailsViewHolder.tvjprob.setText(booking.getJobproblem());

        detailsViewHolder.btnupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, BookUpdate.class);
                intent.putExtra("jname", booking.getJobname());
                intent.putExtra("jdate", booking.getJobdate());
                intent.putExtra("jtime", booking.getJobtime());
                intent.putExtra("jprob", booking.getJobproblem());
                intent.putExtra("bookid", booking.getBookid());
                mcontext.startActivity(intent);
            }
        });
        detailsViewHolder.btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api= Url.getInstance().create(Api.class);
                String bookid=booking.getBookid();
                Call<Void> call=api.deletebook(bookid);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(mcontext, "deleted", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(mcontext, UserActivity.class);
                        mcontext.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(mcontext, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob,btnupt,btndel;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvjobName);
            tvjdate = itemView.findViewById(R.id.tvjdate);
            tvjtime = itemView.findViewById(R.id.tvjtime);
            tvjprob = itemView.findViewById(R.id.tvjprob);
            btnupt = itemView.findViewById(R.id.btnupt);
            btndel = itemView.findViewById(R.id.btndel);
        }

    }
}
