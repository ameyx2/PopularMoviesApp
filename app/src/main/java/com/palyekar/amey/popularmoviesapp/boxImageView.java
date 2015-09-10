package com.palyekar.amey.popularmoviesapp;

import android.app.ActivityManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Amey on 9/9/2015.
 */
public class boxImageView extends ImageView {
    public boxImageView(Context context) {
        super(context);
    }

    public boxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width

 /*       ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        String className = am.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.d("topActivity", "CURRENT Activity ::" + className);

        if (className == "com.palyekar.amey.popularmoviesapp.movieDetailActivity") {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        }*/

    }
}
