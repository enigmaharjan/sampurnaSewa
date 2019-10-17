package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;
import com.example.sampurnasewaagile.ShowCompBook;
import com.example.sampurnasewaagile.ShowConfBook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Model.Job;

import static Url.Url.BASE_URL;

public class CompJobDetailAdapteradmin extends RecyclerView.Adapter<CompJobDetailAdapteradmin.DetailsViewHolder> {
    Context mcontext;
    List<Job> jobList;
    String jobImage, imagePath;

    public CompJobDetailAdapteradmin(Context mcontext, List<Job> jobList) {
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
        jobImage = job.getJobimage();
        final String imgPath = BASE_URL + "uploads/" + jobImage;
        imagePath = imgPath;
//            Toast.makeText(getApplicationContext(), imagePath, Toast.LENGTH_SHORT).show();
        StrictMode();
        try {
            java.net.URL url = new java.net.URL(imgPath);
            detailsViewHolder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mcontext, "Error " + e, Toast.LENGTH_SHORT).show();
        }

        detailsViewHolder.tvjname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, ShowCompBook.class);
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
            imgItem=itemView.findViewById(R.id.imgJobProfile);
            tvjname = itemView.findViewById(R.id.tvjNameadmin);
        }

    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
