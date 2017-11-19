package com.example.gracp.moneytracker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        //startActivity(new Intent(this, AuthActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!((App)getApplication()).isLoggedIn()){
            startActivity(new Intent(this, AuthActivity.class));
        }
        else {
            pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getResources()));
            tabs.setupWithViewPager(pager);
        }
    }
}