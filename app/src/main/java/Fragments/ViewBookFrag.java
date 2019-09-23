package Fragments;


import android.content.SharedPreferences;
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

import Adapter.AllbookDetailAdapter;
import Adapter.MybookDetailAdapter;
import Api.Api;
import Model.Booking;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBookFrag extends Fragment {
    private RecyclerView recyclerView;



    public ViewBookFrag() {
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

        Call<List<Booking>> listCall= api.getallbook();
        listCall.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                Toast.makeText(getContext(), "load Bookings", Toast.LENGTH_SHORT).show();
                List<Booking> booking = response.body();
                AllbookDetailAdapter allbookDetailAdapter = new AllbookDetailAdapter(getActivity(), booking);
                recyclerView.setAdapter(allbookDetailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed"+t, Toast.LENGTH_LONG).show();

            }
        });
    }
}
