
package com.eatour.hyunjongkim.weatherfood;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


import com.eatour.hyunjongkim.weatherfood.lib.MyToast;

import java.util.ArrayList;
import java.util.List;

// アプリ実行の時の権限処理
public class PermissionActivity extends AppCompatActivity {
    private static final int PERMISSION_MULTI_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        // SDK Versionが　23以下の場合、権限要請なくIndexActivityに移動
        if (Build.VERSION.SDK_INT < 23) {
            goIndexActivity();
        } else {
            // SDK Versionが　23以上の場合、権限要請後　➡　IndexActivity
            if (checkAndRequestPermissions()) {
                goIndexActivity();
            }
        }
    }

    // 権限の確認後、権限がない場合権限の要請（必要な権限全部あり：true）
    private boolean checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET
        };

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_MULTI_CODE);
            return false;
        }

        return true;
    }

    // 権限要請の結果を受け取るメソッド
    // requestCode  要請コード
    // permissions  権限の種類
    // grantResults 権限の結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 0) return;

        switch (requestCode) {
            case PERMISSION_MULTI_CODE:
                checkPermissionResult(permissions, grantResults);

                break;
        }
    }

    // 権限処理の結果によりIndexActivityの実行か権限設定の要請のダイアログを見せるかを決定
    // permissions  権限の種類
    // grantResults 権限の結果
    private void checkPermissionResult(String[] permissions, int[] grantResults) {
        boolean isAllGranted = true;

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
            }
        }

        // 権限全部あり
        if (isAllGranted) {
            goIndexActivity();
        } else {
            showPermissionDialog();
        }
    }

    // IndexActivityの実行
    private void goIndexActivity() {
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);

        //IndexActivityの実行後、Activityを終了させる
        finish();
    }

    // 権限設定の画面に移動を選択するダイアログの表示
    private void showPermissionDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.permission_setting_title);
        dialog.setMessage(R.string.permission_setting_message);
        dialog.setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MyToast.s(PermissionActivity.this, R.string.permission_setting_restart);
                PermissionActivity.this.finish();

                goAppSettingActivity();
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                PermissionActivity.this.finish();
            }
        });
        dialog.show();
    }

    // 権限が設定できるActivityを実行
    private void goAppSettingActivity() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}
