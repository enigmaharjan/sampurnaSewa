package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sampurnasewaagile.R;

import java.util.List;

import Adapter.JobDetailAdapteradmin;
import Api.Api;
import Model.Job;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAdminBookFrag extends Fragment {
    private RecyclerView recyclerView;



    public ViewAdminBookFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_book, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewallbook);


        showallBook();


        return view;

}
    private void showallBook(){

        Retrofit retrofit= Url.getInstance();
        Api api = retrofit.create(Api.class);

        Call<List<Job>> listCall= api.getJobs();
        listCall.enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                Toast.makeText(getContext(), "load Bookings", Toast.LENGTH_SHORT).show();
                List<Job> booking = response.body();
                JobDetailAdapteradmin allbookDetailAdapter = new JobDetailAdapteradmin(getActivity(), booking);
                recyclerView.setAdapter(allbookDetailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed"+t, Toast.LENGTH_LONG).show();

            }
        });
    }
}
