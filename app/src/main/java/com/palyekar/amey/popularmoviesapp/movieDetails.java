package com.palyekar.amey.popularmoviesapp;

/**
 * Created by Amey on 9/9/2015.
 */
public class movieDetails {

    private String title;
    private String releaseDate;
    private float vote;
    private String Synopsis;
    private String posterUrl;

    protected movieDetails(String ptitle, String preleaseDate, float pvote, String pSynopsis, String pposterUrl){
        this.title = ptitle;
        this.releaseDate = preleaseDate;
        this.vote = pvote;
        this.Synopsis = pSynopsis;
        this.posterUrl =pposterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getVote() {
        return vote;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

}
