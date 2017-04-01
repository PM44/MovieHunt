package com.elanic.pulkit.moviesearch.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elanic.pulkit.moviesearch.BaseActivity;
import com.elanic.pulkit.moviesearch.R;
import com.elanic.pulkit.moviesearch.apimodel.Search;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pulkit on 1/4/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder> {
    private ArrayList<Search> movieList;
    private Activity parentAct;
    private BaseActivity.FragmentType fragmentType;

    public RecyclerViewAdapter(ArrayList<Search> movieList1, Activity activity, BaseActivity.FragmentType type) {
        movieList = movieList1;
        parentAct = activity;
        fragmentType = type;
    }

    @Override
    public RecyclerViewAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerViewAdapter.ListViewHolder listViewHolder = null;
        if (fragmentType == BaseActivity.FragmentType.GRID) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_card, parent, false);
            listViewHolder = new RecyclerViewAdapter.ListViewHolder(itemView);
        } else if (fragmentType == BaseActivity.FragmentType.LIST) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_simple, parent, false);
            listViewHolder = new RecyclerViewAdapter.ListViewHolder(itemView);
        }
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ListViewHolder holder, int position) {
        holder.title.setText((String) movieList.get(position).getTitle());
        holder.description.setText((String) movieList.get(position).getYear());
        Picasso.with(parentAct).load(movieList.get(position).getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title;
        protected TextView description;
        protected ImageView poster;

        public ListViewHolder(final View vi) {
            super(vi);
            if (fragmentType == BaseActivity.FragmentType.GRID) {
                title = (TextView) vi.findViewById(R.id.textView);
                description = (TextView) vi.findViewById(R.id.textView2);
                poster = (ImageView) vi.findViewById(R.id.imageView);
            } else if (fragmentType == BaseActivity.FragmentType.LIST) {
                title = (TextView) vi.findViewById(R.id.textView6);
                description = (TextView) vi.findViewById(R.id.textView7);
                poster = (CircleImageView) vi.findViewById(R.id.imageView4);
                Typeface typeFace = Typeface.createFromAsset(parentAct.getAssets(), "font2.ttf");
                title.setTypeface(typeFace);
                description.setTypeface(typeFace);
            }
            vi.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            TastyToast.makeText(parentAct, movieList.get(getPosition()).getTitle(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

        }
    }
}
