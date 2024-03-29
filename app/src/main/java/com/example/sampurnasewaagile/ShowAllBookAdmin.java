package com.example.sampurnasewaagile;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import Adapter.AllbookAdminDetailAdapter;
import Api.Api;
import Model.Booking;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowAllBookAdmin extends AppCompatActivity {
    private RecyclerView recyclerView;
    String jobname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_book);
        recyclerView = findViewById(R.id.recyclerViewallbooking);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh6);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showallBooking();
                pullToRefresh.setRefreshing(false);
            }
        });
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobname=(bundle.getString("jobname"));
        }
        showallBooking();
    }



        private void showallBooking(){

            Retrofit retrofit= Url.getInstance();
            Api api = retrofit.create(Api.class);
            String confirmation="0";
            Call<List<Booking>> listCall= api.getallbook(jobname,confirmation);
            listCall.enqueue(new Callback<List<Booking>>() {
                @Override
                public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                    List<Booking> booking = response.body();
                    AllbookAdminDetailAdapter allbookAdminDetailAdapter = new AllbookAdminDetailAdapter(ShowAllBookAdmin.this, booking);
                    recyclerView.setAdapter(allbookAdminDetailAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllBookAdmin.this));
                }
                @Override
                public void onFailure(Call<List<Booking>> call, Throwable t) {
                    Toast.makeText(ShowAllBookAdmin.this, "Failed"+t, Toast.LENGTH_LONG).show();

                }
            });
        }
    }
