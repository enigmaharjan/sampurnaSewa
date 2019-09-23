package Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sampurnasewaagile.Book;
import com.example.sampurnasewaagile.R;

import java.util.List;

import Adapter.MybookDetailAdapter;
import Api.Api;
import Model.Booking;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ViewBookingFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view= inflater.inflate((R.layout.fragment_viewbooking),container,false);

        recyclerView = view.findViewById(R.id.recyclerViewmybook);

        showBook();


        return view;

    }
    private void showBook(){

        Retrofit retrofit= Url.getInstance();
        Api api = retrofit.create(Api.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", MODE_PRIVATE);
        final String userid = sharedPreferences.getString("userid", "");
        Call<List<Booking>> listCall= api.getbook(userid);
        listCall.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {

                Toast.makeText(getContext(), "load Bookings", Toast.LENGTH_SHORT).show();
                List<Booking> booking = response.body();
                MybookDetailAdapter mybookDetailAdapter = new MybookDetailAdapter(getActivity(), booking);
                recyclerView.setAdapter(mybookDetailAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Toast.makeText(getContext(), "failed "+ t, Toast.LENGTH_SHORT).show();

                Log.d("My error ", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
