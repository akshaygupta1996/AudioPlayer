package com.valdioveliu.valdio.audioplayer;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Akshay on 13-04-2017.
 */

public class themeUtils {
    private static int cTheme;


    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;

        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);



    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:

            case 0:

                activity.setTheme(R.style.AppTheme);

                break;

            case 1:

                activity.setTheme(R.style.AppTheme_Yellow);

                break;

            case 2:

                activity.setTheme(R.style.AppTheme_Dark);
                break;

            case 3:
                activity.setTheme(R.style.AppTheme_Blue);
                break;

        }

    }

}

