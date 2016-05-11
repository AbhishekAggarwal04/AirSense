package com.example.anirudh.airsense;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Anirudh on 11-Apr-16.
 */
public class YesScreen extends Activity {

    static RadarView mRadarView;
    static String newIP = "";
    static int ipCounter = 0;
    static String connectIp = "";
    static TextView generalTextView;
    static TextView te;
    static Button scanButton;
    static ProgressBar progressBar;
    static int progressBarStatus;

    private Handler mHandler = new Handler();

    void tryToConnect(){
        connectIp = newIP + ipCounter + "";
        Log.i("wifi",connectIp);
        try {
            if(ipCounter<256){
                progressBarStatus = (int) (((float)ipCounter/(float) 255)*(float)100);
                mHandler.post(new Runnable() {
                    public void run() {
                        progressBar.setProgress(progressBarStatus);
                    }
                });
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(connectIp,12002),100);
                MainFunction.sockTCP = socket;
                MainFunction.globalIP = connectIp;
                YesScreen.ipCounter = 256;
                Log.i("wifi","connected!!!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRadarView.stopAnimation();
                        generalTextView.setText("Connected");
                    }
                });
                startActivity(new Intent(YesScreen.this, MainFunction.class));
                return;
            }
            else if(ipCounter == 256){
                //new UIThread().execute();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        YesScreen.progressBar.setVisibility(View.INVISIBLE);
                        YesScreen.mRadarView.stopAnimation();
                        YesScreen.scanButton.setVisibility(View.VISIBLE);
                        YesScreen.te.setVisibility(View.VISIBLE);
                        YesScreen.generalTextView.setText("Scan the QR code");
                    }
                });
            }
        }
        catch (SocketTimeoutException e){
            ipCounter++;
            Log.i("wifi",e.toString());
            tryToConnect();
        } catch (ConnectException e) {
            ipCounter++;
            Log.i("wifi",e.toString());
            tryToConnect();
        }
        catch (Exception e){
            ipCounter++;
            Log.i("wifi",e.toString());
            tryToConnect();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yesscreen);

        mRadarView = (RadarView) findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
        mRadarView.startAnimation();

        generalTextView = (TextView) findViewById(R.id.textView3);

        te = (TextView) findViewById(R.id.infoText);
        te.setVisibility(View.INVISIBLE);

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setVisibility(View.INVISIBLE);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator it = new IntentIntegrator(YesScreen.this);
                it.initiateScan();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        /*new Thread(new Runnable() {
            public void run() {
                /*while (progressBarStatus < 100) {
                    progressBarStatus = mProgressStatus+5;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                while(YesScreen.ipCounter<255){
                    progressBarStatus = (int) (((float)ipCounter/(float) 255)*(float)100);
                    Log.i("myprogress",progressBarStatus+"");
                }

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
            }).start();*/



        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = null;
        ip = String.format("%d.%d.%d.%d",(ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        newIP = ip.substring(0,ip.lastIndexOf('.'));
        newIP = newIP + ".";
        Log.i("wifi",newIP);
        new Thread(new Runnable() {
            @Override
            public void run() {
                tryToConnect();
            }
        }).start();

    }

    // for QR Scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String re="";
        if (scanResult != null) {
            try {
                re = scanResult.getContents();
                if(re.isEmpty()==false){
                    Log.i("code", re);
                    String str_temp[]=re.split(":");
                    MainFunction.globalIP = str_temp[0];
                    MainFunction.port1 = Integer.parseInt(str_temp[1]);
                    MainFunction.port2= Integer.parseInt(str_temp[2]);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MainFunction.sockTCP = new Socket(MainFunction.globalIP,MainFunction.port2);
                                Log.i("socket","socket made");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    Thread.sleep(1000);
                    MainFunction.makeConnections();
                    Log.i("connect","connection made");
                    startActivity(new Intent(YesScreen.this, MainFunction.class));
                    Log.i("activity","activity started");
                    //notificationManager.notify(123,notificationStarMouse);
                }
            }
            catch(Exception e){
                Log.i("code",e.toString());
            }
        }

        else{
            Log.i("scan2","else code");
            new Intent(this, MainActivity.class);
            //editTextTemporary.setFocusable(true);
        }
        // else continue with any other code you need in the method
    }
}
