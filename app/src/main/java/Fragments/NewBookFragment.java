package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import java.util.List;

import Adapter.JobDetailAdapter;
import Api.Api;
import Model.Job;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewBookFragment extends Fragment {
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate((R.layout.fragment_home),container,false);

        recyclerView = view.findViewById(R.id.recyclerView);

        showUsers();


        return view;

    }
    private void showUsers(){

        Retrofit retrofit= Url.getInstance();
        Api api = retrofit.create(Api.class);

        Call<List<Job>> listCall=api.getJobs();
        listCall.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                List<Job> jobs = response.body();
                JobDetailAdapter detailsAdapter = new JobDetailAdapter(getActivity(), jobs);
                recyclerView.setAdapter(detailsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Toast.makeText(getContext(), "Sorry dear User", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
