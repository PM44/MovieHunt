package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.elanic.pulkit.moviesearch.R.id.textView;


class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ListViewHolder> {
    private ArrayList<Search> movieList;
    private Activity parentAct;
    private SimpleListAdapter.ListViewHolder h1;
    private int lastPosition = -1;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick();

    }

    SimpleListAdapter(ArrayList<Search> movieList1, Activity activity, OnItemClickListener listener) {
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
        holder.title.setText((String) movieList.get(position).getTitle());
        holder.description.setText((String) movieList.get(position).getYear());
        Picasso.with(parentAct).load(movieList.get(position).getPoster()).into(holder.poster);
        for (int i = 0; i < getItemCount(); i++) {
            //animate(itemView, i);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("itemCount", movieList.size() + "");
        return movieList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title;
        protected TextView description;
        protected CircleImageView poster;

        public ListViewHolder(final View vi) {
            super(vi);
            title = (TextView) vi.findViewById(R.id.textView6);
            description = (TextView) vi.findViewById(R.id.textView7);
            poster = (CircleImageView) vi.findViewById(R.id.imageView4);
            Typeface typeFace = Typeface.createFromAsset(parentAct.getAssets(), "font2.ttf");
            title.setTypeface(typeFace);
            description.setTypeface(typeFace);
            vi.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TastyToast.makeText(parentAct, movieList.get(getPosition()).getTitle(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
        }
    }
}
