package com.test.test.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URL;


public class GlideImageLoader implements ImageLoader {
    private Context context;

    public GlideImageLoader(Context context) {
        this.context = context;
    }

    public void load(String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .into(view);
    }

    @Override
    public void load(URL url, ImageView view) {
        load(url.toString(), view);
    }
}
