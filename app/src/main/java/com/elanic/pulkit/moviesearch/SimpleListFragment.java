package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class SimpleListFragment extends Fragment implements BaseActivity.SimpleFragmentInteraction {
    public RecyclerView recList;
    View view;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public SimpleListFragment() {
        // Required empty public constructor
    }

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
        SimpleListAdapter simpleListAdapter = new SimpleListAdapter(createList(), getActivity(), new SimpleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(simpleListAdapter);
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
        Search s = new Search();
        s.setTitle("p");
        s.setYear("p");
        Search[] s1 = new Search[1];
        s1[0] = s;
        return s1;
    }


    public void getMovieList(Search[] movies) {
        Search[] movieList = movies;
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        SimpleListAdapter ca = new SimpleListAdapter(movies, getActivity(), new SimpleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(getContext(), BaseActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(ca);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        recList.setAdapter(alphaAdapter);
    }
}
