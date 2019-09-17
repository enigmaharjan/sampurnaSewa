package Api;

import java.util.List;

import Model.Job;
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

    @POST("api/v1/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @GET("api/v1/job")
    Call<List<Job>> getJobs();
}