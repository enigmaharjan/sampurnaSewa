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

public class BookUpdate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView upttime, uptdate, uptproblem;
    private TextView uptbook;
    String jobdate, jobtime, jobproblem, bid, jname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_update);
        upttime = findViewById(R.id.upttime);
        uptdate = findViewById(R.id.uptdate);
        uptproblem = findViewById(R.id.uptproblem);
        uptbook = findViewById(R.id.uptbook);
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String uid = sharedPreferences.getString("userid", "");
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jname = (bundle.getString("jname"));
            jobproblem = (bundle.getString("jprob"));
            jobdate = (bundle.getString("jdate"));
            jobtime = (bundle.getString("jtime"));
            bid = (bundle.getString("bookid"));
        }

        upttime.setText(jobtime);
        uptdate.setText(jobdate);
        uptproblem.setText(jobproblem);



        uptdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BookUpdate.this, BookUpdate.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+1000);
                datePickerDialog.show();
            }
        });
        upttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);
                final int second = cal.get(Calendar.SECOND);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BookUpdate.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        upttime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        uptbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api api = Url.getInstance().create(Api.class);
                String jobname=jname;
                String userid = uid;
                String bookid = bid;
                String jobtime = upttime.getText().toString();
                String jobdate = uptdate.getText().toString();
                String jobproblem = uptproblem.getText().toString();
                String confirmation="0";
                String completed="0";
                Booking booking = new Booking(bookid, jobname, jobtime, jobdate, jobproblem, userid,confirmation,completed);
                Call<BookingResponse> call = api.updatebook(booking);
                call.enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        BookingResponse bookingResponse = response.body();
                        if (bookingResponse.getMessage().equals("Success")) {
                            Toast.makeText(BookUpdate.this, "Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BookUpdate.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(BookUpdate.this, "Sorry failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        Toast.makeText(BookUpdate.this, "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
        uptdate.setText(date);
    }

}

