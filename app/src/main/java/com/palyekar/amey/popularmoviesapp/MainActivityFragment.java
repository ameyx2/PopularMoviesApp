package com.palyekar.amey.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridview;
    private static final String TmdbAPIKey =  "API_KEY";

    //movieGridItem[] moviegriditems = new movieGridItem[] {};
    List<movieGridItem> moviegriditems = new ArrayList<movieGridItem>();
    movieGridAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        moviesAsyncTask atask = new moviesAsyncTask();
        atask.execute();

        gridview = (GridView) v.findViewById(R.id.gridView);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,fillArray);
        adapter = new movieGridAdapter(getActivity(),moviegriditems);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int movieNameid = moviegriditems.get(position).id;
                Intent myIntent = new Intent(getActivity(),movieDetailActivity.class)
                        .putExtra("movieNameId",movieNameid);
                startActivity(myIntent);
             //   Toast.makeText(getActivity(),,Toast.LENGTH_SHORT).show();
            }
        });

        return v;
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

            if (pref == R.id.action_mostpopular) {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getPopularMovieList("en", 1);
            }
            else if (pref == R.id.action_highestrated) {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getTopRatedMovies("en", 1);
            } else {
                movies = new TmdbApi(TmdbAPIKey).getMovies().getPopularMovieList("en", 1);
            }


            List<MovieDb> mlist = movies.getResults();
            moviegriditems.clear();
            for (MovieDb movie : mlist) {
                posterID = "http://image.tmdb.org/t/p/w185/" + movie.getPosterPath();
                moviename = movie.getTitle();
                movieid = movie.getId();
                //Log.d("Movies", "moviesx1" + posterID + " " + moviename + " " + movieid);
                moviegriditems.add(new movieGridItem(posterID, moviename,movieid));
            }

            return null;
        }
    }

}
