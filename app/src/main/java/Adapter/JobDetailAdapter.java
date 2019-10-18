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

import com.example.sampurnasewaagile.Book;
import com.example.sampurnasewaagile.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import Model.Job;
import retrofit2.Call;

import static Url.Url.BASE_URL;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.DetailsViewHolder> {
    Context mcontext;
    List<Job> jobList;

    public JobDetailAdapter(Context mcontext, List<Job> jobList) {
        this.mcontext = mcontext;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jobuser, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final Job job = jobList.get(i);

        detailsViewHolder.tvjname.setText(job.getJobname());
        detailsViewHolder.tvjdesc.setText(job.getJobdetail());
        detailsViewHolder.tvjprice.setText(job.getMinimumcharge());
        String image = job.getJobimage();
        final String imgPath = BASE_URL + "uploads/" + image;
        String imagePath = imgPath;
        StrictMode();
        try {
            java.net.URL url = new java.net.URL(imagePath);
            detailsViewHolder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        detailsViewHolder.tvjname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, Book.class);
                intent.putExtra("jobname", job.getJobname());
                mcontext.startActivity(intent);

            }
        });
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public int getItemCount() {
        return jobList.size();    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvjname, tvjdesc,tvjprice;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem=itemView.findViewById(R.id.imgProfile);
            tvjname = itemView.findViewById(R.id.tvjName);
            tvjdesc = itemView.findViewById(R.id.tvjdesc);
            tvjprice = itemView.findViewById(R.id.tvjprice);
            imgItem=itemView.findViewById(R.id.imgProfile);
        }

    }
}
