package com.redmart.android.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.redmart.android.R;


/**
 * Created by ramindu.weeraman on 27/3/18.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();


    private static Utils instance;

    private Utils() {

    }

    /**
     * Create Singleton Utils class
     */
    public static synchronized Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }


    public Typeface getThisFont(Context context, int textStyleIndex) {
        final int FONT_REGULAR = 1;
        final int FONT_LIGHT = 2;
        final int FONT_MEDIUM = 3;
        final int FONT_MEDIUM_ITALIC = 4;

        Typeface typeface;
        switch (textStyleIndex) {
            case FONT_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
                break;
            case FONT_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
                break;
            case FONT_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
            case FONT_MEDIUM_ITALIC:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
                break;
            default:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
        }
        return typeface;
    }


    public void showLongToast(Activity activity, String msg) {
        try {
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e(TAG, "showLongToast error");
        }
    }


    public void loadThisImage(Context context, String urlImage, ImageView imageView) {
        Log.d("TAG", "URL : " + urlImage);
        Glide.with(context)
                .load(urlImage)
                .placeholder(R.color.white)
                .into(imageView);
    }


    @SuppressLint("DefaultLocale")
    public String formatAmountToString(Double price) {
        return price == null ? null : String.format("$ %.2f", price);
    }

    @SuppressLint("DefaultLocale")
    public String formatAmountToString(Long price) {
        return price == null ? null : String.format("$ %.2f", price);
    }
}
