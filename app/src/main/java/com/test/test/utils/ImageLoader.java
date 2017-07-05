package com.test.test.utils;

import android.widget.ImageView;

import java.net.URL;

public interface ImageLoader {
    void load(String url, ImageView view);
    void load(URL url, ImageView view);
}
