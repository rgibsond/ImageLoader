package com.example.rgdrys13.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rgdrys13 on 9/27/2016.
 */

public class Utility {

    //taken from https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //taken from https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    //taken from https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    public static Bitmap decodeSampledBitmapFromFilePath(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path);
    }

    //A function to return the list of file names and paths in the pictures directory
    public static ArrayList<String> getPictureFiles(){

        File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        ArrayList<String> pics = new ArrayList();

        for (File f: pictures.listFiles()){
            pics.add(f.getPath());
        }
        return pics;
    }

    /**
     * http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
     * Modified to use HttpUrlConnection instead og AndroidHttpClient
     * @param urls
     * @return
     */
    static Bitmap downloadBitmap(String urls, int w, int h) {

        // Get the dimensions of the the URL image
        BitmapFactory.Options options = downloadBitmapOptions(urls);

        if (options == null)
            return null;

        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.w("ImageDownloader", "Error while retrieving bitmap: " + connection.getResponseMessage());
                return null;
            }

            InputStream inputStream = null;

            try {
                inputStream = new BufferedInputStream(connection.getInputStream());
                final Bitmap bitmap = Utility.decodeSampledBitmap(inputStream, options, w, h);
                return bitmap;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + urls);
            Log.w("ImageDownloader", "Error while retrieving bitmap: " + e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
     * Modified to use HttpUrlConnection instead og AndroidHttpClient
     * @param urls
     * @return
     */
    static BitmapFactory.Options downloadBitmapOptions(String urls) {

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("ImageDownloader", "Error while retrieving bitmap: " + connection.getResponseMessage());
                return null;
            }

            InputStream inputStream = null;

            try {
                inputStream = new BufferedInputStream(connection.getInputStream());

                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStream, null, options);
                return options;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (connection != null)
                    connection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ImageDownloader", "Error while retrieving bitmap options from " + urls);
            Log.e("ImageDownloader", "Error while retrieving bitmap options: " + e.toString());
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    public static Bitmap decodeSampledBitmap(
            InputStream is,
            BitmapFactory.Options options,
            int reqWidth, int reqHeight) {

        // Calculate new inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }
}
