package com.eatour.hyunjongkim.weatherfood;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.eatour.hyunjongkim.weatherfood.lib.GeoLib;
import com.eatour.hyunjongkim.weatherfood.lib.RemoteLib;


public class IndexActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context = this;

        // インターネット接続確認
        if (!RemoteLib.getInstance().isConnected(context)) {
            showNoService();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!RemoteLib.getInstance().isConnected(context)) {
            showNoService();
            return;
        }

        if (GeoLib.getInstance().checkGPS(context)) {
            GeoLib.getInstance().setLastKnownLocation(this);
            startMain();
        }
    }

    // ネットワークの接続ができないときのダイアログ（終了する）
    private void showNoService() {
        TextView messageText = findViewById(R.id.message);
        messageText.setVisibility(View.VISIBLE);
        Button closeButton = findViewById(R.id.close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        closeButton.setVisibility(View.VISIBLE);
    }

    public void startMain() {
        Intent intent = new Intent(IndexActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
