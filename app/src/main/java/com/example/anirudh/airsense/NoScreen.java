package com.example.anirudh.airsense;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anirudh on 11-Apr-16.
 */
public class NoScreen extends Activity{

    Button settingsButton, doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_screen);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });

        doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoScreen.this,YesScreen.class));
            }
        });

        try{ApManager.isApOn(NoScreen.this);
        ApManager.configApState(NoScreen.this);
        ApManager.setHotspotName("AirSense", NoScreen.this);}catch (Exception e){Log.i("excep",e.toString());}

        /*try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.i("wifiInfo", wifiInfo.toString());
        Log.i("SSID",wifiInfo.getBSSID());}catch (Exception e){Log.i("exception",e.toString());}*/


    }
}
