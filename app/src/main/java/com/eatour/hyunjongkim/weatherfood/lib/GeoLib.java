package com.eatour.hyunjongkim.weatherfood.lib;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.eatour.hyunjongkim.weatherfood.IndexActivity;
import com.eatour.hyunjongkim.weatherfood.PermissionActivity;
import com.eatour.hyunjongkim.weatherfood.R;
import com.eatour.hyunjongkim.weatherfood.model.GeoModel;

import static android.content.Context.LOCATION_SERVICE;

// 位置情報 Lib
public class GeoLib {
    public final String TAG = GeoLib.class.getSimpleName();
    private volatile static GeoLib instance;

    public static GeoLib getInstance() {
        if (instance == null) {
            synchronized (GeoLib.class) {
                if (instance == null) {
                    instance = new GeoLib();
                }
            }
        }

        return instance;
    }

    // ユーザーの現在位置
    public void setLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Location location = null;

        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        // 許可を得たとき
        if (result == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }

        // 現在の位置を保存
        if (location != null) {
            GeoModel.knownLatitude = location.getLatitude();
            GeoModel.knownLongitude = location.getLongitude();

        } else {
            //初期値（東京）
            GeoModel.knownLatitude = 35.689521;
            GeoModel.knownLongitude = 139.691704;
        }
    }

    public boolean checkGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        //GPSがオフの場合、設定を要求するダイアログの表示
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showDialog(context);

            return false;
        }

        return true;
    }

    // GPS要求のダイアログ
    private void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.title_gps_check_dialog));
        builder.setMessage(context.getResources().getString(R.string.msg_gps_check_dialog));
        builder.setPositiveButton(context.getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        context.startActivity(intent);
                    }
                });
        builder.setNegativeButton(context.getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        context.startActivity(intent);
                    }
                });

        builder.show();
    }
}


