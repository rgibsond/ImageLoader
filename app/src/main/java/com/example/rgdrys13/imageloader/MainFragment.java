package com.example.rgdrys13.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainFragment extends Fragment {
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_main, container);

        Resources res = getResources();

        for (int i = 0; i < 4; i++){
            int id = res.getIdentifier("i"+i, "id", getActivity().getPackageName());
            ImageButton ib = (ImageButton) root.findViewById(id);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable d = ((ImageView)view).getDrawable();
                    ((MainActivity)getActivity()).showMainImage(d);
                }
            });
        }

        return root;
    }
}