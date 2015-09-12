package com.palyekar.amey.popularmoviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;


/**
 * A placeholder fragment containing a simple view.
 */
public class movieDetailActivityFragment extends Fragment {

    private ImageView l_iv_movieposter;
    private TextView l_tv_title, l_tv_vote, l_tv_synopsis, l_tv_release;
    private static final String TmdbAPIKey =  "fb822635b777a1da00cae23438ffb6da";
    private static final String posterUrlMain = "http://image.tmdb.org/t/p/w500/";

    public movieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent myIntent = getActivity().getIntent();
        Integer movieNameId = 0;



        if (myIntent!=null && myIntent.hasExtra("movieNameId")) {
            movieNameId = myIntent.getIntExtra("movieNameId", 0);
            Log.d("Album", "Album2" + movieNameId);
        }
        if(!isConnected(getActivity())) {
            buildDialog(getActivity()).show();
        }
        else {
            moviesAsyncTask atask = new moviesAsyncTask();
            atask.execute(movieNameId);
        }

        return rootview;
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

    public class moviesAsyncTask extends AsyncTask<Integer, Void, movieDetails> {

        @Override
        protected void onPostExecute(movieDetails movie) {
            super.onPostExecute(movie);

            l_tv_release = (TextView) getActivity().findViewById(R.id.tv_detail_release);
            l_tv_release.setText(movie.getReleaseDate());
            l_tv_synopsis = (TextView) getActivity().findViewById(R.id.tv_detail_synopsis);
            l_tv_synopsis.setText(movie.getSynopsis());
            l_tv_title = (TextView) getActivity().findViewById(R.id.tv_detail_title);
            l_tv_title.setText(movie.getTitle());
            l_tv_vote = (TextView) getActivity().findViewById(R.id.tv_detail_vote);
            l_tv_vote.setText(String.valueOf(movie.getVote()));
            l_iv_movieposter = (ImageView) getActivity().findViewById(R.id.iv_detail_poster);

            Picasso.with(getActivity())
                    .load(movie.getPosterUrl())
                    .into(l_iv_movieposter);
        }

        @Override
        protected movieDetails doInBackground(Integer... params) {
            //      String posterUrl;
            //      MovieResultsPage movies = new MovieResultsPage();
            String lreleasedate="", ltitle="", lSynopsis="";
            final MovieDb movie = new TmdbApi(TmdbAPIKey).getMovies().getMovie(params[0], "en");


            String posterID = posterUrlMain + movie.getBackdropPath();
            Log.d("Detail",params[0] + " " + posterID);
            if (movie.getReleaseDate() != null) {
                lreleasedate = movie.getReleaseDate();
            }
            if (movie.getOriginalTitle() != null) {
                ltitle = movie.getOriginalTitle();
            }
            if (movie.getOverview() != null) {
                lSynopsis = movie.getOverview();
            }
            float lvote = movie.getVoteAverage();
            //Log.d("rating",String.valueOf(movie.getVoteAverage()));
            //Log.d("rating",String.valueOf(movie.getUserRating()));

            return (new movieDetails(ltitle, lreleasedate, lvote, lSynopsis, posterID));
        }
    }
}
