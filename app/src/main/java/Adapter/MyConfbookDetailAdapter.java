package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import java.util.List;

import Api.Api;
import Model.Booking;
import Model.Feedback;
import Model.RegisterResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

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
        final Booking booking = bookList.get(i);
        detailsViewHolder.bookid = booking.getBookid();
        detailsViewHolder.tvjobName.setText(booking.getJobname());
        detailsViewHolder.tvjdate.setText(booking.getJobdate());
        detailsViewHolder.tvjtime.setText(booking.getJobtime());
        detailsViewHolder.tvjprob.setText(booking.getJobproblem());
        detailsViewHolder.tvj.setText(booking.getFeedback());

        if (booking.getCompleted().equals("0")) {
            detailsViewHolder.tvjstatus.setText("Pending");
        } else if (booking.getCompleted().equals("1")) {
            detailsViewHolder.tvjstatus.setText("Completed");
        } else if (booking.getCompleted().equals("2")) {
            detailsViewHolder.tvjstatus.setText("Rejected");
        }
        else detailsViewHolder.tvjstatus.setText("");
        if (detailsViewHolder.tvjstatus.getText().equals("Completed") && detailsViewHolder.tvj.getText().equals("null")) {
            detailsViewHolder.btnfeedback.setVisibility(View.VISIBLE);
            detailsViewHolder.btnfeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater)
                            mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View popupView = inflater.inflate(R.layout.activity_feedback, null);
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true;
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                    final EditText etfeedback=popupView.findViewById(R.id.etfeedback);
                    final Button btnsubmit=popupView.findViewById(R.id.btnsubmit);
                    final String bookid=booking.getBookid();
                    btnsubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Api api = Url.getInstance().create(Api.class);
                            String feedback = etfeedback.getText().toString();
                            Feedback feedback1=new Feedback(bookid,feedback);
                            Call<RegisterResponse> call = api.addFeedback(feedback1);
                            call.enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    RegisterResponse registerResponse=response.body();
                                    if (registerResponse.getMessage().equals("Success")){
                                        Toast.makeText(mcontext, "Feedback created", Toast.LENGTH_SHORT).show();
                                        popupWindow.dismiss();
                                    }else {
                                        Toast.makeText(mcontext, "Something wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                    Toast.makeText(mcontext, "Failed"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvjobName, tvjdate, tvjtime, tvjprob, tvjstatus, btnfeedback, tvj;
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
