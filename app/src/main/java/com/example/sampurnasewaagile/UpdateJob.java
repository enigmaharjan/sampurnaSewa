package com.example.sampurnasewaagile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Api.Api;
import Model.Job;
import Model.RegisterResponse;
import Model.User;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateJob extends AppCompatActivity {
    private EditText jname, jdetail, mincharge;
    private TextView upt_job;
    private Spinner avail;
    private String joname,jodetail,mcharge,jid,availability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job);
        jname = findViewById(R.id.ujobname);
        jdetail = findViewById(R.id.ujobdetail);
        mincharge = findViewById(R.id.uminimumcharge);
        avail = findViewById(R.id.uavail);
        upt_job = findViewById(R.id.upt_job);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            joname=(bundle.getString("jobname"));
            jodetail=(bundle.getString("jobdetail"));
            mcharge=(bundle.getString("min"));
            jid=(bundle.getString("jobid"));
            availability=(bundle.getString("avail"));
        }
        jname.setText(joname);
        jdetail.setText(jodetail);
        mincharge.setText(mcharge);
        upt_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Api api = Url.getInstance().create(Api.class);
                final String joid = bundle.getString("jobid");
                final String jobname = jname.getText().toString();
                String jobdetail= jdetail.getText().toString();
                String minimumcharge = mincharge.getText().toString();
                String availability = avail.getSelectedItem().toString();
                final String jobid=joid;
                Job job= new Job(jobid, jobname, jobdetail, minimumcharge,availability);
                Call<RegisterResponse> listCall = api.updatejob(job);
                listCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse = response.body();
                        if (registerResponse.getMessage().equals("success")) {
                            Toast.makeText(UpdateJob.this, "Updated", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UpdateJob.this,AdminActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateJob.this, "sorry", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(UpdateJob.this, "failed to update", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
        }
