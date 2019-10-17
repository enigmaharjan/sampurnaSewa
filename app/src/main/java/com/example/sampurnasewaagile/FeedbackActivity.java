package com.example.sampurnasewaagile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Api.Api;
import Model.Feedback;
import Model.RegisterResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {
    private EditText etfeedback;
    private Button btnsubmit;
    String bookid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        etfeedback=findViewById(R.id.etfeedback);
        btnsubmit=findViewById(R.id.btnsubmit);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bookid=(bundle.getString("bookid"));
        }
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                String feedback = etfeedback.getText().toString();
                Feedback feedback1=new Feedback(bookid,feedback);
                Call<RegisterResponse> call = api.addFeedback(feedback1);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse registerResponse=response.body();
                        if (registerResponse.getMessage().equals("Success")){
                            Toast.makeText(FeedbackActivity.this, "Feedback created", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(FeedbackActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(FeedbackActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(FeedbackActivity.this, "Failed"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
