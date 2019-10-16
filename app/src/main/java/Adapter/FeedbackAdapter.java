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
import Model.Feedback;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Feedback> feedbacks;
    public FeedbackAdapter(Context mcontext, List<Feedback> feedbacks) {
        this.mcontext = mcontext;
        this.feedbacks = feedbacks;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Feedback feedback = feedbacks.get(i);
            detailsViewHolder.tvfeedback.setText(feedback.getFeedback());
        }

    @Override
    public int getItemCount() {
        return feedbacks.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView tvfeedback;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfeedback = itemView.findViewById(R.id.tvFeedback);
        }

    }
}
