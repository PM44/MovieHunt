package com.elanic.pulkit.moviesearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elanic.pulkit.moviesearch.BaseActivity;
import com.elanic.pulkit.moviesearch.R;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by pulkit on 1/4/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder> {
    @Override
    public RecyclerViewAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ListViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
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
            TastyToast.makeText(parentAct, movieList.get(getPosition()).getTitle(), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

        }
    }
}
