package com.example.sampurnasewaagile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Api.Api;
import Model.Job;
import Model.JobResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJob extends AppCompatActivity {
    private EditText jname, jdetail, mincharge;
    private Button add_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        jname = findViewById(R.id.jobname);
        jdetail = findViewById(R.id.jobdetail);
        mincharge = findViewById(R.id.minimumcharge);
        add_job=findViewById(R.id.add_job);

        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                final String jobname=jname.getText().toString();
                final String jobdetail=jdetail.getText().toString();
                final String minimumcharge=mincharge.getText().toString();
                String jobimage = " name";

                Job job= new Job(jobname,jobdetail,minimumcharge,jobimage);
                Call<JobResponse> call= api.addjob(job);
                call.enqueue(new Callback<JobResponse>() {
                    @Override
                    public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                        JobResponse jobResponse=response.body();
                        if (jobResponse.getMessage().equals("Successfully Registered")){
                            Toast.makeText(AddJob.this, "Success", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(AddJob.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JobResponse> call, Throwable t) {
                        Toast.makeText(AddJob.this, "Sorry", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
