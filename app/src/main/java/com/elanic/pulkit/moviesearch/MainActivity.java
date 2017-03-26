package com.elanic.pulkit.moviesearch;

import android.app.ActionBar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String type="movie";
    String movie="Sultan";
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        omdbapi.Factory.getInstance().getInfo(movie,type).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.body().getSearch() != null)
                {
                    Toast.makeText(getApplicationContext(),response.body().getSearch()+"",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("failed", t.getMessage());
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        int pg_number = 0;
        viewPager.setAdapter(mAdapter);
        if(getIntent().getExtras()!= null){
            try {
                pg_number = Integer.parseInt(getIntent().getExtras().getString("pagenumber"));
            }catch (NumberFormatException num){
            }
        }
        viewPager.setCurrentItem(pg_number);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
