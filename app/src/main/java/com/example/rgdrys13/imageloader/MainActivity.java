package com.example.rgdrys13.imageloader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    ImageButton mainImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainImageView = (ImageButton) findViewById(R.id.main_image);
        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainImageView.setVisibility(View.GONE);
            }
        });

        Button next = (Button) findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExternalStorageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMainImage(Drawable d){

        mainImageView.setImageDrawable(d);
        mainImageView.setVisibility(View.VISIBLE);
    }
}
