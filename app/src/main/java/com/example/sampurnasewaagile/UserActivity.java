package com.example.sampurnasewaagile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import Fragments.NewBookFragment;
import Fragments.ProfileFragment;
import Fragments.ViewUserBookingFragment;
import Fragments.ViewUConfBookingFragment;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView tvTitle;
    private NavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        drawerLayout = findViewById(R.id.drawerLayout2);
        tvTitle = findViewById(R.id.tvTitle2);

        nav = findViewById(R.id.nav2);

        nav.findViewById(R.id.myprofile).setOnClickListener(this);
        nav.findViewById(R.id.bookJobs).setOnClickListener(this);
        nav.findViewById(R.id.myPending).setOnClickListener(this);
        nav.findViewById(R.id.myconfirmed).setOnClickListener(this);
        nav.findViewById(R.id.viewLogout2).setOnClickListener(this);

        showFragment(new ProfileFragment(), "My Profile");
    }

    public void openDrawer2(View view) {
        drawerLayout.openDrawer(Gravity.START);
    }

    private void showFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, fragment);
        transaction.commit();
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myprofile:
                showFragment(new ProfileFragment(), "My Profile");
                break;
            case R.id.bookJobs:
                showFragment(new NewBookFragment(), "Select Jobs");
                break;
            case R.id.myPending:
                showFragment(new ViewUserBookingFragment(), "My Pending Book");
                break;
            case R.id.myconfirmed:
                showFragment(new ViewUConfBookingFragment(), "My Confirmed Book");
                break;
            case R.id.viewLogout2:
                Intent intent = new Intent(UserActivity.this, ViewPagerActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
    }
}