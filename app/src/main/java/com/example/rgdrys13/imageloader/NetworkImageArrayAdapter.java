package com.example.rgdrys13.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rgdrys13 on 10/4/2016.
 */

public class NetworkImageArrayAdapter extends ArrayAdapter<String> {

    protected String[] urls;
    protected Bitmap placeholder;

    public NetworkImageArrayAdapter(Context context, int resource, String[] urls, Bitmap placeholder) {
        super(context, resource, urls);

        this.urls = urls;
        this.placeholder = placeholder;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public String getItem(int position) {
        return urls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
}

    @Override
    /**
     * i = array index
     * reusedView = recycled view if not null
     * parent = parent layout file
     */
    public View getView(int i, View reusedView, ViewGroup parent) {

        if (reusedView == null){
            reusedView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tv = (TextView) reusedView.findViewById(R.id.list_item_text);
        tv.setText(getItem(i));

        ImageView iv = (ImageView) reusedView.findViewById(R.id.list_item_image);

        iv.setImageBitmap(placeholder);

        return reusedView;
    }
}
