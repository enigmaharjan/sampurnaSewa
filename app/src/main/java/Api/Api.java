package Api;
import android.content.SharedPreferences;

import java.util.List;

import Model.Admin;
import Model.Booking;
import Model.BookingResponse;
import Model.Job;
import Model.JobResponse;
import Model.LoginResponse;
import Model.RegisterResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @POST("api/v1/authuser")
    Call<LoginResponse> getResponse(@Body User user);

    @GET("api/v1/job")
    Call<List<Job>> getJobs();

    @GET("api/v1/booking")
    Call<List<Booking>> getbook();

    @POST("api/v1/booking")
    Call<BookingResponse> addbook(@Body Booking booking);

    @POST("api/v1/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @POST("api/v1/authadmin")
    Call<LoginResponse> getAdmin(@Body Admin admin);

    @POST("api/v1/job")
    Call<JobResponse> addjob(@Body Job job);
}