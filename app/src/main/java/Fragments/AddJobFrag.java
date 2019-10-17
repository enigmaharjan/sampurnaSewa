package Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import Api.Api;
import Model.Job;
import Model.JobResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddJobFrag extends Fragment {
    private EditText jname, jdetail, mincharge;
    private TextView add_job;
    private Spinner avail;

    public AddJobFrag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_job, container, false);
        jname = view.findViewById(R.id.jobname);
        jdetail = view.findViewById(R.id.jobdetail);
        mincharge = view.findViewById(R.id.minimumcharge);
        add_job = view.findViewById(R.id.add_job);
        avail = view.findViewById(R.id.avail);
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                final String jobname=jname.getText().toString();
                final String jobdetail=jdetail.getText().toString();
                final String minimumcharge=mincharge.getText().toString();
                final String availability= avail.getSelectedItem().toString();
                String jobimage = "name";
                Job job= new Job(jobname,jobdetail,minimumcharge,jobimage,availability);
                Call<JobResponse> call= api.addjob(job);
                call.enqueue(new Callback<JobResponse>() {
                    @Override
                    public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                        JobResponse jobResponse=response.body();
                        if (jobResponse.getMessage().equals("Successfully Registered")){
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            jname.setText(""); jdetail.setText(""); mincharge.setText("");
                        } else{
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JobResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Sorry", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }
}

