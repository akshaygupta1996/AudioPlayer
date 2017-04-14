package com.valdioveliu.valdio.audioplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;


/**
 * Created by Valdio Veliu on 16-08-06.
 */
public class CustomTouchListener implements RecyclerView.OnItemTouchListener {

    //Gesture detector to intercept the touch events
    GestureDetector gestureDetector;
    private ClickListener clickListener;

    public CustomTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clickListener!=null){
                    try {
                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {

        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, recyclerView.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
