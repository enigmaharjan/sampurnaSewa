package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.ShowAllBook;
import com.example.sampurnasewaagile.ShowConfBook;

import java.util.List;

import Model.Job;

public class ConfJobDetailAdapteradmin extends RecyclerView.Adapter<ConfJobDetailAdapteradmin.DetailsViewHolder> {
    Context mcontext;
    List<Job> jobList;

    public ConfJobDetailAdapteradmin(Context mcontext, List<Job> jobList) {
        this.mcontext = mcontext;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jobadmin, viewGroup, false);
        return new DetailsViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Job job=jobList.get(i);

        detailsViewHolder.tvjname.setText(job.getJobname());

        detailsViewHolder.tvjname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, ShowConfBook.class);
                intent.putExtra("jobname", job.getJobname());
                mcontext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvjname;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem=itemView.findViewById(R.id.imgProfile);
            tvjname = itemView.findViewById(R.id.tvjNameadmin);
        }

    }
}