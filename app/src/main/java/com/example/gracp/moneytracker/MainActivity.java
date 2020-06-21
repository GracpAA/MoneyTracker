package com.example.gracp.moneytracker;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gracp.moneytracker.api.Api;
import com.example.gracp.moneytracker.api.Result;
import com.example.gracp.moneytracker.App;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabs;
    private final static int LOADER_LOGOUT = 4;
    Api api;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        //startActivity(new Intent(this, AuthActivity.class));

        isLogged();

        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getResources()));
        tabs.setupWithViewPager(pager);
    }

   // @Override
   // protected void onResume() {
     //   super.onResume();
     //   if(!((App)getApplication()).isLoggedIn()){
     //       startActivity(new Intent(this, AuthActivity.class));
     //   }
     //   else {
     //       pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getResources()));
     //       tabs.setupWithViewPager(pager);
     //   }
     // }

    private void isLogged(){
        if(!((App)getApplication()).isLoggedIn()){
            startActivity(new Intent(this, AuthActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout: logout();  break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){

        getLoaderManager().restartLoader(LOADER_LOGOUT,null, new LoaderManager.LoaderCallbacks() {
            @Override
            public Loader<Result> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader(getApplicationContext()) {
                    @Override
                    public Result loadInBackground() {
                        try {
                            return api.logout().execute().body();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader loader, Object data) {
            ((App)getApplication()).remuveAuthToken();
            isLogged();
            }

            @Override
            public void onLoaderReset(Loader loader) {

            }
        }).forceLoad();

        }


}