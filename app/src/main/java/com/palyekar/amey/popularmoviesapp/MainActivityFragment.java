package com.palyekar.amey.popularmoviesapp;

import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridview;
    boolean mDualPane;
    private static final String TmdbAPIKey =  "fb822635b777a1da00cae23438ffb6da";
    int movieNameid;
    //movieGridItem[] moviegriditems = new movieGridItem[] {};
    List<movieGridItem> moviegriditems = new ArrayList<movieGridItem>();
    movieGridAdapter adapter;
    int mCurSelection;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View detailframe = getActivity().findViewById(R.id.frame_large_detail);
        mDualPane = detailframe != null && detailframe.getVisibility() == View.VISIBLE;
     //   boolean i = (detailframe != null);
    //    boolean j = (detailframe.getVisibility() == View.VISIBLE);
      //  Log.d("DualPane1",String.valueOf(i));
      //  Log.d("DualPane2",String.valueOf(j));
        Log.d("DualPane",String.valueOf(mDualPane));

        if (savedInstanceState != null) {
            mCurSelection = savedInstanceState.getInt("curSelection",0);
        }

        if (mDualPane) {
          //  gridview.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
            showDetails(mCurSelection);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        if(!isConnected(getActivity())) {
            buildDialog(getActivity()).show();
        }
        else {
            moviesAsyncTask atask = new moviesAsyncTask();
            atask.execute();
        }
/*        moviesAsyncTask atask = new moviesAsyncTask();
        atask.execute();*/


        gridview = (GridView) v.findViewById(R.id.gridView);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,fillArray);
        adapter = new movieGridAdapter(getActivity(),moviegriditems);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                movieNameid = moviegriditems.get(position).id;

                SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("movieNameId", movieNameid).commit();
                Log.d("movieNameId", ((Integer) movieNameid).toString());
                showDetails(position);
             //   Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void showDetails(int index) {
        mCurSelection = index;
        Log.d("CurrSelection", String.valueOf(mCurSelection));

        if (mDualPane) {
            Log.d("DualPane3",String.valueOf(mDualPane));
            gridview.setItemChecked(index, true);
            movieDetailActivityFragment detailFrag = (movieDetailActivityFragment) getFragmentManager().findFragmentById(R.id.frame_large_detail);
            if (detailFrag == null || detailFrag.getShownIndex() != index ) {
                detailFrag = movieDetailActivityFragment.newInstance(index);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.frame_large_detail, detailFrag);
                }
                else {
                    ft.replace(R.id.frame_large_detail, detailFrag);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        } else {
            Log.d("DualPane4",String.valueOf(mDualPane));
            Intent myIntent = new Intent(getActivity(),movieDetailActivity.class)
                    .putExtra("movieNameId",movieNameid)
                    .putExtra("index", index);
            startActivity(myIntent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curSelection",mCurSelection);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            }
            else return false;
        } else return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.nointernettitle);
        builder.setMessage(R.string.nointernetmsg);

        builder.setPositiveButton(R.string.nointernetpopupbtnclose, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                getActivity().finish();
            }
        });

        return builder;
    }

    public class moviesAsyncTask extends AsyncTask<Void, Void, Void> {
        private String posterID;
        private String moviename;
        private int movieid;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Log.d("fragment","onpost");
            adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... params) {

            MovieResultsPage movies = new MovieResultsPage();

            SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
            Long pref = settings.getLong("SortOrder", 0);
            if  (pref == R.id.action_favorites) {
                Set<String> set = settings.getStringSet("favorites", new HashSet<String>());
                if (set.isEmpty()) return null;
                else {
                    Iterator iterator = set.iterator();
                    while(iterator.hasNext()) {
                        String movieID = (String) iterator.next();
                        final MovieDb movie = new TmdbApi(TmdbAPIKey).getMovies().getMovie(Integer.parseInt(movieID), "en");
                        posterID = getActivity().getString(R.string.tmdbimgurlw185) + movie.getPosterPath();
                        moviename = movie.getTitle();
                        movieid = movie.getId();
                        moviegriditems.add(new movieGridItem(posterID, moviename, movieid));
                    }
                }
                return null;
            }
            else if (pref == R.id.action_mostpopular) {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getPopularMovieList(getActivity().getString(R.string.tmdblanguageEN), 1);
            }
            else if (pref == R.id.action_highestrated) {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getTopRatedMovies(getActivity().getString(R.string.tmdblanguageEN), 1);
            }
            else {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getPopularMovieList(getActivity().getString(R.string.tmdblanguageEN), 1);
            }


            List<MovieDb> mlist = movies.getResults();
            moviegriditems.clear();
            for (MovieDb movie : mlist) {
                posterID = getActivity().getString(R.string.tmdbimgurlw185) + movie.getPosterPath();
                moviename = movie.getTitle();
                movieid = movie.getId();
                //Log.d("Movies", "moviesx1" + posterID + " " + moviename + " " + movieid);
                moviegriditems.add(new movieGridItem(posterID, moviename,movieid));
            }

            return null;
        }
    }

}
