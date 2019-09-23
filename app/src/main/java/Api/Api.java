package Api;
import java.util.List;

import Model.Admin;
import Model.Booking;
import Model.BookingResponse;
import Model.Job;
import Model.JobResponse;
import Model.LoginResponse;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @POST("api/v1/authuser")
    Call<LoginResponse> getResponse(@Body User2 user2);

    @GET("api/v1/job")
    Call<List<Job>> getJobs();


    @POST("api/v1/booking")
    Call<BookingResponse> addbook(@Body Booking booking);

    @POST("api/v1/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @POST("api/v1/authadmin")
    Call<LoginResponse> getAdmin(@Body Admin admin);

    @POST("api/v1/job")
    Call<JobResponse> addjob(@Body Job job);


    @GET("api/v1/booked/{userid}")
    Call<List<Booking>> getbook(@Path("userid") String userid);

    @GET("api/v1/booking")
    Call<List<Booking>> getallbook();
}