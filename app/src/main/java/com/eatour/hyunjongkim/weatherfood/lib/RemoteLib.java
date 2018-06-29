
package com.eatour.hyunjongkim.weatherfood.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// ネットワーク関連 Lib
public class RemoteLib {
    public static final String TAG = RemoteLib.class.getSimpleName();
    private volatile static RemoteLib instance;
    public static RemoteLib getInstance() {
        if (instance == null) {
            synchronized (RemoteLib.class) {
                if (instance == null) {
                    instance = new RemoteLib();
                }
            }
        }
        return instance;
    }

    // ネットワークの接続確認
    public boolean isConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (info != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

