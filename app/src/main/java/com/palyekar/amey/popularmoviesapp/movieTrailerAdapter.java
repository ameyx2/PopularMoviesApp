package com.palyekar.amey.popularmoviesapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amey on 9/13/2015.
 */
public class movieTrailerAdapter extends ArrayAdapter<trailersRowItem> {

    Context context;

    public movieTrailerAdapter(Context context, int resource, List<trailersRowItem> items) {
        super(context, resource, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView thumbnail;
        TextView trailername;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("adapter1","getView");
        ViewHolder holder = null;
        trailersRowItem rowitem = getItem(position);

        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = minflater.inflate(R.layout.trailers_listviewitem, null);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.trailers_thumbnail);
            holder.trailername = (TextView) convertView.findViewById(R.id.trailers_name);
            convertView.setTag(holder);
            Log.d("adapter1","convertiewnull");

        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.d("adapter1","convertiewnotnull");
        }
        holder.trailername.setText(rowitem.getTrailername());
        holder.thumbnail.setImageResource(rowitem.getImageID());

        return convertView;


    }
}
