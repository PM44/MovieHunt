package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.GridView;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class GridViewFragment extends Fragment implements BaseActivity.GridFragmentInteraction{

    public RecyclerView recList;
    View view;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public GridViewFragment() {
        // Required empty public constructor
    }

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
        GridLayoutManager llm = new GridLayoutManager(getActivity().getBaseContext(),2);
        recList.setLayoutManager(llm);
        recList.addItemDecoration(new GridItemDecoration(2,15, true));
        GridViewAdapter gridViewAdapter = new GridViewAdapter(createList(),getActivity(),new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(gridViewAdapter);
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
    public Search[] createList() {
        Search[] s1 = new Search[0];
        return s1;
    }


    public void getMovieList(Search[] movies) {
        Search[] movieList = movies;
        recList.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(getActivity(),2);
        recList.setLayoutManager(llm);
        GridViewAdapter ca = new GridViewAdapter(movies, getActivity(), new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(ca);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(4000);
        alphaAdapter.setFirstOnly(false);
        recList.setAdapter(alphaAdapter);
    }
}
