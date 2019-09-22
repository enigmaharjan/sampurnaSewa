package com.example.sampurnasewaagile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import Api.Api;
import Model.Booking;
import Model.BookingResponse;
import Url.Url;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Book extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView jobtype, jtime, jdate, problem;
    private Button btnbook;
    String jobname, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        jobtype = findViewById(R.id.jobtype);
        jdate = findViewById(R.id.jobdate);
        jtime = findViewById(R.id.jobtime);
        problem = findViewById(R.id.problem);
        btnbook = findViewById(R.id.btnbook);
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String uid = sharedPreferences.getString("userid", "");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobname = bundle.getString("jobname");
            jobtype.setText(jobname);
        }

        jtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);
                final int second = cal.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Book.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        jtime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        jdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Book.this, Book.this, year, month, day);
                datePickerDialog.show();
            }
        });
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                String bookid = "10000000";
                String jobname = jobtype.getText().toString();
                String jobtime = jtime.getText().toString();
                String jobdate = jdate.getText().toString();
                String jobproblem = problem.getText().toString();
                String userid = uid;

                Booking booking = new Booking(bookid, jobname, jobtime, jobdate, jobproblem, userid);
                Call<BookingResponse> call = api.addbook(booking);
                call.enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        BookingResponse bookingResponse = response.body();
                        if (bookingResponse.getMessage().equals("Success")) {
                            Toast.makeText(Book.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(Book.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Book.this, "Sorry", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        Toast.makeText(Book.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String mon = "";
        switch (month) {
            case 0:
                mon = "jan";
                break;
            case 1:
                mon = "feb";
                break;
            case 2:
                mon = "mar";
                break;
            case 3:
                mon = "Apr";
                break;
            case 4:
                mon = "May";
                break;
            case 5:
                mon = "Jun";
                break;
            case 6:
                mon = "Jul";
                break;
            case 7:
                mon = "Aug";
                break;
            case 8:
                mon = "Sep";
                break;
            case 9:
                mon = "Oct";
                break;
            case 10:
                mon = "Nov";
                break;
            case 11:
                mon = "Dec";
                break;
        }
        String date = "" + mon + "," + dayOfMonth + "-" + year;
        jdate.setText(date);
    }

}