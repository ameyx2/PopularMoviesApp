package com.palyekar.amey.popularmoviesapp;

/**
 * Created by Amey on 9/8/2015.
 */
public class movieGridItem {

    public String posterimageURL;
    public String movieName;
    public int id;

    movieGridItem (String pimgURL, String pmname, int pid){
        this.posterimageURL = pimgURL;
        this.movieName = pmname;
        this.id = pid;
    }
}
