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

import Adapter.ConfJobDetailAdapteradmin;
import Adapter.FeedbackAdapter;
import Api.Api;
import Model.Feedback;
import Model.Job;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFeedbackFrag extends Fragment {
    private RecyclerView recyclerView;

    public ViewFeedbackFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_feedback, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewfeedback);
        showallFeed();
        return view;
    }
    private void showallFeed() {

        Retrofit retrofit = Url.getInstance();
        Api api = retrofit.create(Api.class);
        Call<List<Feedback>> listCall= api.getFeedback();
        listCall.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                Toast.makeText(getContext(), "load Feedback", Toast.LENGTH_SHORT).show();
                List<Feedback> feedbacks = response.body();
                FeedbackAdapter feedbackAdapter = new FeedbackAdapter(getActivity(), feedbacks);
                recyclerView.setAdapter(feedbackAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed"+t, Toast.LENGTH_LONG).show();

            }
        });
    }
}