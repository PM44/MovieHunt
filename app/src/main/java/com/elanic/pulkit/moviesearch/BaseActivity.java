package com.elanic.pulkit.moviesearch;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {

    private String type = "movie";
    private String movie = "A";
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private Search[] movieList;
    private EditText searchField;
    private RelativeLayout relativeLayout;
    private ImageButton searchButton;
    private ImageButton backButton;
    private TextView textView;
    public SimpleFragmentInteraction simpleFragmentInteraction;
    public GridFragmentInteraction gridFragmentInteraction;

    public interface SimpleFragmentInteraction {
        public void getMovie(String movie);
    }

    public interface GridFragmentInteraction {
        public void getMovieList(Search[] movies);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_STANDARD);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(view, new android.support.v7.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        if (haveNetworkConnection() != true)
            TastyToast.makeText(getApplicationContext(), "Please Connect To Internet", TastyToast.LENGTH_LONG, TastyToast.WARNING);
        relativeLayout = (RelativeLayout) findViewById(R.id.lol);
        relativeLayout.setBackgroundColor(Color.parseColor("#388FF5"));
        searchButton = (ImageButton) findViewById(R.id.imageButton);
        backButton = (ImageButton) findViewById(R.id.imageButton2);
        textView = (TextView) findViewById(R.id.bar_title1);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font1.ttf");
        textView.setTypeface(typeFace);
        searchField = (EditText) findViewById(R.id.editText4);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchField.getText().toString().equals("")) {
                } else {
                    movieTitle(searchField.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        int pg_number = 0;
        viewPager.setAdapter(mAdapter);
        if (getIntent().getExtras() != null) {
            try {
                pg_number = Integer.parseInt(getIntent().getExtras().getString("pagenumber"));
            } catch (NumberFormatException num) {
            }
        }
        viewPager.setCurrentItem(pg_number);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void movieTitle(String movie) {
        simpleFragmentInteraction.getMovie(movie);
        /*omdbapi.Factory.getInstance().getInfo(movie, type).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.body().getSearch() != null) {
                    movieList = response.body().getSearch();
                    simpleFragmentInteraction.getMovieList(movieList);
                    gridFragmentInteraction.getMovieList(movieList);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("failed", t.getMessage());
            }
        });*/
    }

    public void back(View v) {
        relativeLayout.setBackgroundColor(Color.parseColor("#388FF5"));
        backButton.setVisibility(View.INVISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        searchField.setCursorVisible(false);
    }

    public void search(View v) {
        relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        searchButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.VISIBLE);
        textView.setVisibility(View.INVISIBLE);
        searchField.setCursorVisible(true);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
