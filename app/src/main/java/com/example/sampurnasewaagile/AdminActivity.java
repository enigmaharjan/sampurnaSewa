package com.example.sampurnasewaagile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Fragments.AddJobFrag;
import Fragments.ViewAdminBookFrag;
import Fragments.ViewAdminCompletedFrag;
import Fragments.ViewAdminConfBookFrag;
import Fragments.ViewFeedbackFrag;
import Fragments.ViewJobFrag;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView tvTitle;
    private NavigationView nav;
    int backButtonCount;

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        drawerLayout = findViewById(R.id.drawerLayout);
        tvTitle = findViewById(R.id.tvTitle);

        nav = findViewById(R.id.nav);

        nav.findViewById(R.id.addJobs).setOnClickListener(this);
        nav.findViewById(R.id.viewJobs).setOnClickListener(this);
        nav.findViewById(R.id.viewBook).setOnClickListener(this);
        nav.findViewById(R.id.viewConfirmed).setOnClickListener(this);
        nav.findViewById(R.id.viewFeedback).setOnClickListener(this);
        nav.findViewById(R.id.viewCompleted).setOnClickListener(this);
        nav.findViewById(R.id.viewLogout).setOnClickListener(this);


        showFragment(new ViewAdminBookFrag(), "Book Requests");
    }

    public void openDrawer(View view) {
        drawerLayout.openDrawer(Gravity.START);
    }

    private void showFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addJobs:
                showFragment(new AddJobFrag(), "Add Jobs");
                break;
            case R.id.viewJobs:
                showFragment(new ViewJobFrag(), "Show Jobs");
                break;
            case R.id.viewBook:
                showFragment(new ViewAdminBookFrag(), "Show Book Request");
                break;
            case R.id.viewConfirmed:
                showFragment(new ViewAdminConfBookFrag(), "Show Confirmed Book");
                break;
            case R.id.viewCompleted:
                showFragment(new ViewAdminCompletedFrag(), "Show Completed book");
                break;
            case R.id.viewFeedback:
                showFragment(new ViewFeedbackFrag(), "Show View Feedback");
                break;
            case R.id.viewLogout:
                SharedPreferences prefs = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userid", "null");
                editor.apply();
                Intent intent = new Intent(AdminActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
    }
}