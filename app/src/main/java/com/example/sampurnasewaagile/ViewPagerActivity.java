package com.example.sampurnasewaagile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import Adapter.ViewPagerAdapter;
import Fragments.LoginPage;
import Fragments.RegisterPage;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        tabLayout=findViewById(R.id.tabId);
        viewPager=findViewById(R.id.viewPager);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        adapter.LoginPage(new LoginPage(),"Sign In");
        adapter.LoginPage(new RegisterPage(),"Sign up");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }

}