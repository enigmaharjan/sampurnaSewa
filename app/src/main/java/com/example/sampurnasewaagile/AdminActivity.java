package com.example.sampurnasewaagile;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

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


        showFragment(new ViewJobFrag(), "All Jobs");
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
                showFragment(new ViewJobFrag(), "View Jobs");
                break;
            case R.id.viewBook:
                showFragment(new ViewAdminBookFrag(), "View Book Request");
                break;
            case R.id.viewConfirmed:
                showFragment(new ViewAdminConfBookFrag(), "View Confirmed Book");
                break;
            case  R.id.viewCompleted:
                showFragment(new ViewAdminCompletedFrag(),"Completed book");
                break;
            case R.id.viewFeedback:
                showFragment(new ViewFeedbackFrag(), "View Feedback");
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
    }
}