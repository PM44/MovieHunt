package com.elanic.pulkit.moviesearch;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pulkit on 27/3/17.
 */

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ListViewHolder> {
    private Search[] movieList;
    private Activity parentAct;
    private GridViewAdapter.ListViewHolder h1;
    private int lastPosition = -1;
    private final GridViewAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick();

    }

    GridViewAdapter(Search[] movieList1, Activity activity, GridViewAdapter.OnItemClickListener listener) {
        movieList = movieList1;
        parentAct = activity;
        this.listener = listener;
    }


    @Override
        public GridViewAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_card, parent, false);
        return new ListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final GridViewAdapter.ListViewHolder holder, final int position) {
        h1 = holder;
        holder.title.setText((String) movieList[position].getTitle());
        holder.description.setText((String) movieList[position].getYear());
        Picasso.with(parentAct).load(movieList[position].getPoster()).into(holder.poster);
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
        protected ImageView poster;

        public ListViewHolder(final View vi) {
            super(vi);
            title = (TextView) vi.findViewById(R.id.textView);
            description = (TextView) vi.findViewById(R.id.textView2);
            poster = (ImageView) vi.findViewById(R.id.imageView);
            vi.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }
}
