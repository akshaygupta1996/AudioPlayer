package com.valdioveliu.valdio.audioplayer;

import android.view.View;

import java.io.IOException;

/**
 * Created by Akshay on 14-04-2017.
 */

public interface ClickListener {

    public void onClick(View view, int position);
    public void onLongClick(View view,int position) throws IOException;
}
