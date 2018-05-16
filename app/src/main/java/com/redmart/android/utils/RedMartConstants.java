package com.redmart.android.utils;
/**
 * Created by ramindu.weeraman on 27/3/18.
 */
public class RedMartConstants {
    public static final int LIST_ITEMS_PER_ONCE =20;
    public static final String BASE_URL = "https://api.redmart.com";
    public static final String IMAGE_BASE_URL = "http://media.redmart.com/newmedia/200p";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getImageUrl(String imagePath) {
        return IMAGE_BASE_URL + imagePath;
    }
}
