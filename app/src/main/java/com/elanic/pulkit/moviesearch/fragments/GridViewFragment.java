package com.elanic.pulkit.moviesearch.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.elanic.pulkit.moviesearch.BaseActivity;
import com.elanic.pulkit.moviesearch.EndlessRecyclerViewScrollListener;
import com.elanic.pulkit.moviesearch.GridItemDecoration;
import com.elanic.pulkit.moviesearch.R;
import com.elanic.pulkit.moviesearch.adapters.GridViewAdapter;
import com.elanic.pulkit.moviesearch.apimodel.Movies;
import com.elanic.pulkit.moviesearch.apimodel.Search;
import com.elanic.pulkit.moviesearch.apimodel.omdbapi;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewFragment extends Fragment implements BaseActivity.GridFragmentInteraction {

    private RecyclerView recList;
    private View view;
    private String movieName;
    private ArrayList<Search> movieList;
    private ArrayList<Search> movies;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    ScaleInAnimationAdapter alphaAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) getActivity()).gridFragmentInteraction = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grid_view, container, false);
        recList = (RecyclerView) view.findViewById(R.id.grid_recycler);
        recList.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(getActivity().getBaseContext(), 2);
        recList.setLayoutManager(llm);
        recList.addItemDecoration(new GridItemDecoration(2, 15, true));
        GridViewAdapter gridViewAdapter = new GridViewAdapter(createList(), getActivity(), new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });
        alphaAdapter = new ScaleInAnimationAdapter(gridViewAdapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(4000);
        alphaAdapter.setFirstOnly(false);
        recList.setAdapter(alphaAdapter);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
            }
        });
        return view;
    }

    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
        }
    }

    public ArrayList<Search> createList() {
        movieList = new ArrayList<Search>(0);
        return movieList;
    }

    public void getMovie(String movie) {
        movieList.clear();
        alphaAdapter.notifyDataSetChanged();
        movieName = movie;
        loadNextDataFromApi(1);
        recList.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(getActivity().getBaseContext(), 2);
        recList.setLayoutManager(llm);
        recList.setLayoutManager(llm);
        GridViewAdapter ca = new GridViewAdapter(movieList, getActivity(), new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        alphaAdapter = new ScaleInAnimationAdapter(ca);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recList.setAdapter(alphaAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page + 1);
            }
        };
        recList.addOnScrollListener(scrollListener);
    }

    public void loadNextDataFromApi(final int offset) {
        omdbapi.Factory.getInstance().getInfo(movieName, "movie", offset).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {

                if (response.body().getSearch() != null) {
                    movies = response.body().getSearch();
                    for (int i = 0; i < movies.size(); i++) {
                        movieList.add(movies.get(i));
                    }
                    alphaAdapter.notifyItemInserted(movieList.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e("failed", t.getMessage());
            }
        });
    }
}
