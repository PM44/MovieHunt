package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ListViewHolder> {
    private Search[] movieList;
    private Activity parentAct;
    private SimpleListAdapter.ListViewHolder h1;
    private int lastPosition = -1;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick();

    }

    SimpleListAdapter(Search[] movieList1, Activity activity, OnItemClickListener listener) {
        movieList = movieList1;
        parentAct = activity;
        this.listener = listener;
    }


    @Override
    public SimpleListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_simple, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleListAdapter.ListViewHolder holder, final int position) {
        h1 = holder;
        holder.title.setText((String) movieList[position].getTitle());
        holder.description.setText((String) movieList[position].getYear());

        for (int i = 0; i < getItemCount(); i++) {
            //animate(itemView, i);
        }
    }
    @Override
    public int getItemCount() {
        Log.d("itemCount", movieList.length + "");
        return movieList.length;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title;
        protected TextView description;
        public ListViewHolder(final View vi) {
            super(vi);
            title = (TextView) vi.findViewById(R.id.textView6);
            description = (TextView) vi.findViewById(R.id.textView7);
            vi.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }
}
