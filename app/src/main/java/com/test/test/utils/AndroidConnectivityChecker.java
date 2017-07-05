package com.test.test.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

public class AndroidConnectivityChecker implements ConnectivityChecker {
    ConnectivityManager connectivityManager;

    @Inject
    public AndroidConnectivityChecker(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public boolean check() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
