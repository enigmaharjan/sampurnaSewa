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
import com.example.sampurnasewaagile.UpdateJob;

import java.util.List;

import Model.Job;

public class JDetailAdapteradmin extends RecyclerView.Adapter<JDetailAdapteradmin.DetailsViewHolder> {
    Context mcontext;
    List<Job> jobList;

    public JDetailAdapteradmin(Context mcontext, List<Job> jobList) {
        this.mcontext = mcontext;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.job, viewGroup, false);
        return new DetailsViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Job job=jobList.get(i);

        detailsViewHolder.tvjname.setText(job.getJobname());
        detailsViewHolder.tvjdesc.setText(job.getJobdetail());
        detailsViewHolder.tvjprice.setText(job.getMinimumcharge());
        String avail;
        avail=job.getAvailability();
        if (avail.equals("Available")){
            detailsViewHolder.tvjavail.setText("Yes");
        }else {
            detailsViewHolder.tvjavail.setText("No");
        }

        detailsViewHolder.tvjname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, UpdateJob.class);
                intent.putExtra("jobname", job.getJobname());
                intent.putExtra("jobdetail", job.getJobdetail());
                intent.putExtra("min", job.getMinimumcharge());
                intent.putExtra("jobid", job.getJobid());
                intent.putExtra("avail", job.getAvailability());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvjname,tvjdesc,tvjprice,tvjavail;


        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem=itemView.findViewById(R.id.imgProfile);
            tvjname = itemView.findViewById(R.id.tvjName);
            tvjdesc = itemView.findViewById(R.id.tvjdesc);
            tvjprice = itemView.findViewById(R.id.tvjprice);
            tvjavail= itemView.findViewById(R.id.tvjavail);
        }

    }
}
