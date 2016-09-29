package com.example.rgdrys13.imageloader;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageButton;

/**
 * Created by rgdrys13 on 9/29/2016.
 */

public class LoadImageTask extends AsyncTask<String, Void, Bitmap>{

    private ImageButton ib;
    private int w,h;

    public LoadImageTask(ImageButton v){
        ib = v;
        this.w = v.getWidth();
        this.h = v.getHeight();
    }

    @Override
    protected Bitmap doInBackground(String... path) {
        return Utility.decodeSampledBitmapFromFilePath(path[0], w, h);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ib.setImageBitmap(bitmap);
    }
}
