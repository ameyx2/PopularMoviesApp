package com.palyekar.amey.popularmoviesapp;

/**
 * Created by Amey on 9/13/2015.
 */
public class trailersRowItem {
    private String trailername;
    private int imageID;
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }


    public String getTrailername() {
        return trailername;
    }

    public void setTrailername(String trailername) {
        this.trailername = trailername;
    }

    public trailersRowItem(int imageID, String trlname, String videoUrl) {
        this.imageID = imageID;
        this.trailername = trlname;
        this.videoUrl = videoUrl;
    }
    public trailersRowItem() {

    }

}
