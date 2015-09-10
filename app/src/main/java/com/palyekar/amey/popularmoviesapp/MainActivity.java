package com.palyekar.amey.popularmoviesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private MenuItem menu_mostpopular;
    private MenuItem menu_highestrated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu_mostpopular = menu.getItem(0).getSubMenu().getItem(0);
        menu_highestrated = menu.getItem(0).getSubMenu().getItem(1);

        SharedPreferences settings = this.getSharedPreferences("PREFS_NAME", 0);
        Long pref = settings.getLong("SortOrder", 0);

        if (pref == R.id.action_mostpopular) {
            menu_mostpopular.setEnabled(false);
            menu_highestrated.setEnabled(true);
        }
        else if (pref == R.id.action_highestrated) {
            menu_highestrated.setEnabled(false);
            menu_mostpopular.setEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SharedPreferences settings = this.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mostpopular) {
            editor.putLong("SortOrder", R.id.action_mostpopular).commit();
            this.recreate();
            return true;
        }
        if (id == R.id.action_highestrated) {
            editor.putLong("SortOrder", R.id.action_highestrated).commit();
            this.recreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
