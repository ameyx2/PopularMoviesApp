package com.palyekar.amey.popularmoviesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class movieDetailActivity extends FragmentActivity  {

    private static final String TmdbAPIKey =  "fb822635b777a1da00cae23438ffb6da";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieDetailActivityFragment frag = new movieDetailActivityFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, frag);

        if (getResources().getConfiguration().orientation == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            movieDetailActivityFragment detailFrag = new movieDetailActivityFragment();
            detailFrag.setArguments(getIntent().getExtras());
            //getFragmentManager().beginTransaction().add(android.R.id.content, detailFrag).commit();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, detailFrag).commit();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                finish();
            }
        });

        return builder;
    }



}
