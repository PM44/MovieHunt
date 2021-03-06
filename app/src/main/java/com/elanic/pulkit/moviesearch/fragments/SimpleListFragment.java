package com.elanic.pulkit.moviesearch.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.elanic.pulkit.moviesearch.BaseActivity;
import com.elanic.pulkit.moviesearch.EndlessRecyclerViewScrollListener;
import com.elanic.pulkit.moviesearch.R;
import com.elanic.pulkit.moviesearch.adapters.RecyclerViewAdapter;
import com.elanic.pulkit.moviesearch.apimodel.Movies;
import com.elanic.pulkit.moviesearch.apimodel.Search;
import com.elanic.pulkit.moviesearch.apimodel.omdbapi;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleListFragment extends Fragment implements BaseActivity.SimpleFragmentInteraction {
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
        ((BaseActivity) getActivity()).simpleFragmentInteraction = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_simple_list, container, false);
        recList = (RecyclerView) view.findViewById(R.id.questionList_recycler);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getBaseContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        RecyclerViewAdapter recyclerViewAdapter= new RecyclerViewAdapter(createList(), getActivity(), BaseActivity.FragmentType.LIST);
        alphaAdapter = new ScaleInAnimationAdapter(recyclerViewAdapter);
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
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        RecyclerViewAdapter ca = new RecyclerViewAdapter(movieList, getActivity(), BaseActivity.FragmentType.LIST);
        alphaAdapter = new ScaleInAnimationAdapter(ca);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recList.setAdapter(alphaAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
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
            }
        });
    }
}
