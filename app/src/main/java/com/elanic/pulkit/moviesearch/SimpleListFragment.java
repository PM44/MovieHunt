package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class SimpleListFragment extends Fragment implements BaseActivity.SimpleFragmentInteraction {
    public RecyclerView recList;
    View view;

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
        alphaAdapter.setDuration(1000);
        alphaAdapter.setFirstOnly(false);
        return view;
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
        recList.setAdapter(ca);
    }
}
