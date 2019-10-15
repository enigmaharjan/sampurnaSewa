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
import Model.BookingResponse;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllbookDetailAdapter extends RecyclerView.Adapter<AllbookDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Booking> bookList;
    public AllbookDetailAdapter(Context mcontext, List<Booking> bookList) {
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
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Booking booking = bookList.get(i);
            detailsViewHolder.tvjobName.setText(booking.getJobname());
            detailsViewHolder.tvjdate.setText(booking.getJobdate());
            detailsViewHolder.tvjtime.setText(booking.getJobtime());
            detailsViewHolder.tvjprob.setText(booking.getJobproblem());
            detailsViewHolder.tvjuser.setText(booking.getUserid());
            final String bid = booking.getBookid();
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
