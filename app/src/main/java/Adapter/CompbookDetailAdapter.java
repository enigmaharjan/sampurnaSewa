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

public class ConfbookDetailAdapter extends RecyclerView.Adapter<ConfbookDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    public ConfbookDetailAdapter(Context mcontext, List<Booking> bookList) {
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
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Booking booking = bookList.get(i);
            detailsViewHolder.tvjobName.setText(booking.getJobname());
            detailsViewHolder.tvjdate.setText(booking.getJobdate());
            detailsViewHolder.tvjtime.setText(booking.getJobtime());
            detailsViewHolder.tvjprob.setText(booking.getJobproblem());
            detailsViewHolder.tvjuser.setText(booking.getUserid());
            final String bid = booking.getBookid();
        detailsViewHolder.btncompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Api api = Url.getInstance().create(Api.class);
                    String bookid = bid;
                    String completed = "1";
                    Booking2 booking2 = new Booking2(bookid, completed);
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
        return bookList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate,tvjtime,tvjprob,tvjuser;
        Button btncompleted;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvjobName = itemView.findViewById(R.id.tvalljobName);
            tvjdate = itemView.findViewById(R.id.tvalljdate);
            tvjtime = itemView.findViewById(R.id.tvalljtime);
            tvjprob = itemView.findViewById(R.id.tvalljprob);
            tvjuser=itemView.findViewById(R.id.tvallUserid);
            btncompleted=itemView.findViewById(R.id.btncompleted);
        }

    }
}
