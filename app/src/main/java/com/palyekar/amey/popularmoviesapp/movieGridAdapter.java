package com.palyekar.amey.popularmoviesapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amey on 9/8/2015.
 */
public class movieGridAdapter extends ArrayAdapter<movieGridItem> {


    public movieGridAdapter(Activity context, List<movieGridItem> movieItem) {
        super(context, 0, movieItem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //    ImageView imageview;
        View v = convertView;
        ImageView iv;
        TextView tv;
        movieGridItem movieGriditem = getItem(position);

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
            v.setTag(R.id.iv_griditem_poster, v.findViewById(R.id.iv_griditem_poster));
            v.setTag(R.id.tv_griditem_title, v.findViewById(R.id.tv_griditem_title));
        }

        iv = (ImageView) v.getTag(R.id.iv_griditem_poster);
        tv = (TextView) v.getTag(R.id.tv_griditem_title);

        tv.setText(movieGriditem.movieName);
        Picasso.with(getContext())
                .load(movieGriditem.posterimageURL)
                .into(iv);
        //Log.d("baseadapter", "base");
        return v;
    }
}
