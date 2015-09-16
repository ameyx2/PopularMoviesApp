package com.palyekar.amey.popularmoviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Reviews;
import info.movito.themoviedbapi.model.Video;


/**
 * A placeholder fragment containing a simple view.
 */
public class movieDetailActivityFragment extends Fragment {


    private static final String TmdbAPIKey =  "fb822635b777a1da00cae23438ffb6da";
    private static final String posterUrlMain = "http://image.tmdb.org/t/p/w500/";

    private FragmentTabHost mTabHost;
/*    private DetailPagerAdapter mDetailPagerAdapter;
    private ViewPager mViewPager;*/

    public movieDetailActivityFragment() {
    }

    public static movieDetailActivityFragment newInstance (int index) {
        movieDetailActivityFragment f = new movieDetailActivityFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index",0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent myIntent = getActivity().getIntent();
        Integer movieNameId = 0;

        if (container == null) {
            return null;
        }

/*        final SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
        final int pref = settings.getInt("movieNameId", 0);*/

        if (myIntent!=null && myIntent.hasExtra("movieNameId")) {
            movieNameId = myIntent.getIntExtra("movieNameId", 0);
            Log.d("Album", "Album2" + movieNameId);
        }
/*
        if (movieNameId == 0) {
            movieNameId = pref;
        }*/


/*

        mDetailPagerAdapter = new DetailPagerAdapter(getActivity().getSupportFragmentManager());
*/
/*        if (getResources().getConfiguration().orientation == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mViewPager = (ViewPager) getActivity().findViewById(R.id.detail_pager1);
        }
        else{*//*

            mViewPager = (ViewPager) rootview.findViewById(R.id.detail_pager);
   //     }

        if (mDetailPagerAdapter == null) Log.d("detail1","null");
        if (mViewPager == null) Log.d("detail2", "null");

        mViewPager.setAdapter(mDetailPagerAdapter);

        //final ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        final ActionBar actionBar = ;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i=0; i < mDetailPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mDetailPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
*/

        mTabHost = (FragmentTabHost) rootview.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmenta").setIndicator("Movie Detail"), DetailFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Reviews"), ReviewsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Trailers"), TrailersFragment.class, null);

        return rootview;
    }



/*

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }


    public class DetailPagerAdapter extends FragmentPagerAdapter {

        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment;

            switch (position) {
                case 0:
                    fragment = new DetailFragment();
                    return fragment;
                case 1:
                    fragment = new ReviewsFragment();
                    return fragment;
                case 2:
                    fragment = new TrailersFragment();
                    return fragment;
            }

            return null;
        }



        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Movie Details".toUpperCase();
                case 1:
                    return "Reviews".toUpperCase();
                case 2:
                    return "Trailers".toUpperCase();
            }
            return null;
        }
    }*/

    public static class DetailFragment extends Fragment {

        private ImageView l_iv_movieposter;
        private TextView l_tv_title, l_tv_vote, l_tv_synopsis, l_tv_release;

        public DetailFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_detail, container, false);

            final SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
            final SharedPreferences.Editor editor = settings.edit();
            final Set<String> set = settings.getStringSet("favorites", new HashSet<String>());
            final int pref = settings.getInt("movieNameId", 0);
            movieDetailActivityFragment m1 = new movieDetailActivityFragment();

            ImageView iv_favorite = (ImageView) rootview.findViewById(R.id.imageView_favorite);

            if (set.contains(String.valueOf(pref))) {
                float i = (float) 1.0;
                iv_favorite.setAlpha(i);
            }
            else {
                float i = (float) 0.4;
                iv_favorite.setAlpha(i);
            }

            iv_favorite.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    float imgalpha = v.getAlpha();

                    if (imgalpha == 1.0) {
                        //change transparency
                        float i = (float) 0.4;
                        v.setAlpha(i);
                        set.remove(String.valueOf(pref));
                        editor.putStringSet("favorites", set).commit();

                    } else {
                        float i = (float) 1.0;
                        v.setAlpha(i);
                        set.add(String.valueOf(pref));
                        editor.putStringSet("favorites", set).commit();
                    }
                }
            });


            if(!m1.isConnected(getActivity())) {
                m1.buildDialog(getActivity()).show();
            }
            else {
                moviesAsyncTask atask = new moviesAsyncTask();
                atask.execute(pref);
            }

            return rootview;
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

    public static class ReviewsFragment extends Fragment {

        private List<String> l_reviews_list;
        private List<String> reviews = new ArrayList<>();
        private ArrayAdapter<String> l_reviewsadapter;
        Integer pref;

        public ReviewsFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_reviews, container, false);

            String[] l_data= new String[]{};

            SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
            pref = settings.getInt("movieNameId", 0);
            Log.d("movieNameId11",pref.toString());
            movieDetailActivityFragment m1 = new movieDetailActivityFragment();

            if(!m1.isConnected(getActivity())) {
                m1.buildDialog(getActivity()).show();
            }
            else {
                reviewAsyncTask l_reviews_asynctask = new reviewAsyncTask();
                l_reviews_asynctask.execute(pref);
            }


            l_reviews_list = new ArrayList<String>(Arrays.asList(l_data));

            l_reviewsadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,l_reviews_list);

            ListView l_lview = (ListView)rootview.findViewById(R.id.list_view_reviews);
            l_lview.setAdapter(l_reviewsadapter);

            return rootview;
        }

        public class reviewAsyncTask extends AsyncTask<Integer,Void,Void> {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                l_reviewsadapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Integer... params) {

                //final MovieDb movie = new TmdbApi(TmdbAPIKey).getMovies().getMovie(params[0], "en");
                List<Reviews> reviewItem = new TmdbApi(TmdbAPIKey).getReviews().getReviews(pref,"en",0).getResults();
                // List<Reviews> reviewItem = movie.getReviews();
                l_reviews_list.clear();
//                Log.d("Review11", reviewItem.get(1).getContent().toString());
                if (!reviewItem.isEmpty()) {
                    for (Reviews rev : reviewItem) {
                        String content = (rev.getContent()).replaceAll("\n","") + "\nAuthor - " + rev.getAuthor();
                        Log.d("Review", content);
                        if (content != null) {
                            l_reviews_list.add(content);
                        }
                    }
                }
                else {
                    l_reviews_list.add("No Review found");
                }


                return null;
            }
        }

    }

    public static class TrailersFragment extends Fragment {

        private List<trailersRowItem> l_trailers_list;
        //    private List<String> reviews = new ArrayList<>();
        private movieTrailerAdapter l_trailersadapter;
        Integer pref;

        TextView tv_trailername;

        public TrailersFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_movietrailers, container, false);

            String[] l_data= new String[]{};

            SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", 0);
            pref = settings.getInt("movieNameId", 0);
            Log.d("movieNameId12",pref.toString());
            movieDetailActivityFragment m1 = new movieDetailActivityFragment();

            if (!m1.isConnected(getActivity())) {
                m1.buildDialog(getActivity()).show();
            }
            else {
                trailerAsyncTask l_trailer_asynctask = new trailerAsyncTask();
                l_trailer_asynctask.execute(pref);
            }
            l_trailers_list = new ArrayList<>();

            //   l_trailers_list = new ArrayList<String>(Arrays.asList(l_data));

            l_trailersadapter = new movieTrailerAdapter(getActivity(), R.layout.trailers_listviewitem, l_trailers_list);

            ListView l_lview = (ListView)rootview.findViewById(R.id.list_view_trailers);
            l_lview.setAdapter(l_trailersadapter);

            l_lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Log.d("Videos",l_trailers_list.get(position).getVideoUrl());
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(l_trailers_list.get(position).getVideoUrl())));                }
            });

            return rootview;
        }

        public class trailerAsyncTask extends AsyncTask<Integer,Void,Void> {

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                l_trailersadapter.notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Integer... params) {

                List<Video> videoItem = new TmdbApi(TmdbAPIKey).getMovies().getVideos(params[0], "");
                l_trailers_list.clear();
                //  Log.d("Review11", videoItem.get(0).getName().toString());
                if (!videoItem.isEmpty()) {
                    for (Video vid : videoItem) {
                        l_trailers_list.add(new trailersRowItem(R.drawable.youtubeicon,vid.getName(),"https://www.youtube.com/watch?v=" + vid.getKey()));
                        Log.d("Review", vid.getName());
                    }
                }
                else {
                    l_trailers_list.add(new trailersRowItem(0,"No Trailers Available",""));
                }
                return null;
            }
        }

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

}
