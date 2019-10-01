package com.example.sampurnasewaagile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import Adapter.AllbookDetailAdapter;
import Adapter.JobDetailAdapteradmin;
import Api.Api;
import Model.Booking;
import Model.Job;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowAllBook extends AppCompatActivity {
    private RecyclerView recyclerView;
    String jobname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_book);
        recyclerView = findViewById(R.id.recyclerViewallbooking);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobname=(bundle.getString("jobname"));
        }
        showallBooking();
    }



        private void showallBooking(){

            Retrofit retrofit= Url.getInstance();
            Api api = retrofit.create(Api.class);

            Call<List<Booking>> listCall= api.getallbook(jobname);
            listCall.enqueue(new Callback<List<Booking>>() {
                @Override
                public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                    Toast.makeText(ShowAllBook.this, "load Bookings", Toast.LENGTH_SHORT).show();
                    List<Booking> booking = response.body();
                    AllbookDetailAdapter allbookDetailAdapter = new AllbookDetailAdapter(ShowAllBook.this, booking);
                    recyclerView.setAdapter(allbookDetailAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllBook.this));
                }
                @Override
                public void onFailure(Call<List<Booking>> call, Throwable t) {
                    Toast.makeText(ShowAllBook.this, "Failed"+t, Toast.LENGTH_LONG).show();

                }
            });
        }
    }
