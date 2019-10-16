package Api;
import java.util.List;

import Model.Admin;
import Model.Booking;
import Model.Booking2;
import Model.BookingResponse;
import Model.Feedback;
import Model.Job;
import Model.JobResponse;
import Model.LoginResponse;
import Model.RegisterResponse;
import Model.User;
import Model.User2;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @POST("api/v1/authuser")
    Call<LoginResponse> getResponse(@Body User2 user2);

    @GET("api/v1/job")
    Call<List<Job>> getJobs();

    @GET("api/v1/feedback")
    Call<List<Feedback>> getFeedback();

    @POST("api/v1/booking")
    Call<BookingResponse> addbook(@Body Booking booking);

    @POST("api/v1/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @POST("api/v1/feedback")
    Call<RegisterResponse> addFeedback(@Body Feedback feedback);

    @PUT("api/v1/user")
    Call<RegisterResponse> updateuser(@Body User user);

    @PUT("api/v1/booking")
    Call<BookingResponse> updatebook(@Body Booking booking);

    @PUT("api/v1/booking")
    Call<BookingResponse> confirmbook(@Body Booking booking);

    @PUT("api/v1/booking")
    Call<BookingResponse> completedbook(@Body Booking2 booking2);

    @POST("api/v1/authadmin")
    Call<LoginResponse> getAdmin(@Body Admin admin);

    @POST("api/v1/job")
    Call<JobResponse> addjob(@Body Job job);

    @GET("api/v1/booked/{userid}/{confirmation}")
    Call<List<Booking>> getbook(@Path("userid") String userid,@Path("confirmation") String confirmation);

    @GET("api/v1/user/{userid}")
    Call<List<User2>> getuser(@Path("userid") String userid);

    @GET("api/v1/booking/{jobname}/{confirmation}/{completed}")
    Call<List<Booking>> getallbooking(@Path("jobname") String jobname,@Path("confirmation") String confirmation,@Path("completed") String completed);

    @GET("api/v1/booking/{jobname}/{confirmation}")
    Call<List<Booking>> getallbook(@Path("jobname") String jobname,@Path("confirmation") String confirmation);

    @HTTP(method = "DELETE", path = "api/v1/booking/{bookid}", hasBody = true)
    Call<Void> deletebook(@Path("bookid") String bookid);
}