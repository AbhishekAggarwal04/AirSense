package com.example.anirudh.airsense;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Anirudh on 11-Apr-16.
 */
public class MainFunction extends Activity implements SensorEventListener {

    static Button leftClickButtonWest, rightClickButtonEast, keyboardOnOffButton, startStopButton, scrollBarButton;
    static Button keyboard_up, keyboard_down, keyboard_left, keyboard_right, keyboard_esc, keyboard_forwardslash, keyboard_pipe, keyboard_backslash, keyboard_home, keyboard_end, keyboard_function_keys, pgUpButton, keyboard_tab, keyboard_ctrl, keyboard_alt, shiftButton, pgDnButton;
    static ZanyEditText editTextTemporary;
    SensorManager mSensorManager;
    Sensor senGyroscope;

    static float old_x = 0;

    private Toolbar mToolbar;

    public static boolean switchClickButtonToggle=false;

    // port for UDP = 12001
    // port for TCP = 12002
    static int port1 = 12001;
    static int port2 = 0;
    static String globalIP="";
    static String temp;

    static DatagramSocket sock;
    static Socket sockTCP;

    static boolean send =false;
    static boolean sendKey=false;
    static boolean sendTCP=false;

    static DataOutputStream dos;

    static int clickSend=0;

    static NotificationManager notificationManager;
    static Notification notificationStarMouse,notificationKeyboard;
    /*
    1 = Left Click Down
    2 = Left Click Up
    3 = Right Click Down
    4 = Right Click Up
     */

    //added another comment

    public static float gx = 0.0f;
    public static float gy = 0.0f;
    public static float gz = 0.0f;

    protected static void makeConnections(){
        try {
            MainFunction.sock = new DatagramSocket();
            MainFunction.send = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("Thread","thread running");
                    try {
                        do{MainFunction.dos=new DataOutputStream(MainFunction.sockTCP.getOutputStream());
                        MainFunction.sendTCP=true;
                        Log.i("stream","got stream");}
                        while(MainFunction.sockTCP==null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("connect",e.toString());
                    }
                }
            }).start();

        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("connect",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("connect",e.toString());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectscreen);

        try {
            MainFunction.sock = new DatagramSocket();
            MainFunction.send = true;
            makeConnections();

        } catch (SocketException e) {
            e.printStackTrace();
        }









        keyboard_up = (Button) findViewById(R.id.keyboard_up);
        keyboard_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    new Asynch2("up_press@99").execute();
                    Asynch2.temp=true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    new Asynch2("up_unpress@99").execute();
                    Asynch2.temp=true;
                }
                return false;
            }
        });

        keyboard_down = (Button) findViewById(R.id.keyboardArrowDown);
        keyboard_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("hello","hello");
                /*if(event.getAction() == MotionEvent.ACTION_DOWN){
                    new Asynch2("down_press@99").execute();
                    Asynch2.temp=true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    new Asynch2("down_unpress@99").execute();
                    Asynch2.temp=true;
                }*/
                return false;
            }
        });

        keyboard_left = (Button) findViewById(R.id.keyboardArrowLeft);
        keyboard_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("touched","touched");
                /*if(event.getAction() == MotionEvent.ACTION_DOWN){
                    new Asynch2("left_press@99").execute();
                    Asynch2.temp=true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    new Asynch2("left_unpress@99").execute();
                    Asynch2.temp=true;
                }*/
                return false;
            }
        });

        keyboard_right = (Button) findViewById(R.id.keyboard_right);
        keyboard_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    new Asynch2("right_press@99").execute();
                    Asynch2.temp=true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    new Asynch2("right_unpress@99").execute();
                    Asynch2.temp=true;
                }
                return false;
            }
        });

        keyboard_esc = (Button) findViewById(R.id.keyboard_esc);
        keyboard_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("touched","esc pressed");
            }
        });

        keyboard_backslash = (Button) findViewById(R.id.keyboard_backslash);
        keyboard_backslash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("touched","backslash");
            }
        });

        keyboard_forwardslash = (Button) findViewById(R.id.keyboard_forwardslash);
        keyboard_forwardslash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("touched","forwardslash");
            }
        });

        keyboard_pipe = (Button) findViewById(R.id.keyboard_pipe);
        keyboard_pipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("touched","pipe");
            }
        });

        keyboard_home = (Button) findViewById(R.id.keyboard_home);
        keyboard_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("touched","home");
            }
        });

        keyboard_end = (Button) findViewById(R.id.keyboard_end);
        keyboard_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableLayout.setVisibility(View.INVISIBLE);


        leftClickButtonWest = (Button) findViewById(R.id.leftButtonClickWest);
        leftClickButtonWest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    MainFunction.clickSend = 1;
                    new Asynch().execute();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    MainFunction.clickSend = 2;
                    new Asynch().execute();
                }
                return false;
            }
        });

        rightClickButtonEast= (Button) findViewById(R.id.rightButtonClickEast);
        rightClickButtonEast.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    MainFunction.clickSend = 3;
                    new Asynch().execute();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    MainFunction.clickSend = 4;
                    new Asynch().execute();
                }
                return false;
            }
        });

        startStopButton = (Button) findViewById(R.id.startStopButton);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainFunction.send==true){
                    MainFunction.send=false;
                }
                else{
                    MainFunction.send=true;
                }
            }
        });

        keyboardOnOffButton = (Button) findViewById(R.id.keyboardOnOffButton);
        keyboardOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendKey==false){
                    sendKey=true;
                    editTextTemporary.requestFocus();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    //notificationManager.notify(234,notificationKeyboard);
                }
                else{
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                    sendKey=false;
                    //notificationManager.cancel(234);
                }
            }
        });


        scrollBarButton = (Button) findViewById(R.id.scrollButton);
        scrollBarButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    float new_x=event.getY();
                    if(new_x-old_x>=5){
                        //sroll send up
                        MainFunction.clickSend = 7;
                        new Asynch().execute();
                        old_x=new_x;
                    }
                    else if(new_x-old_x<=-5){
                        //scroll send down
                        MainFunction.clickSend = 8;
                        new Asynch().execute();
                        old_x=new_x;
                    }
                }
                return false;
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senGyroscope=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this,senGyroscope,1);


        editTextTemporary = (ZanyEditText) findViewById(R.id.myEditText);
        editTextTemporary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new Asynch2(s).execute();
                Asynch2.temp=true;

                //MainActivity.editTextTemporary.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextTemporary.setSingleLine();
        editTextTemporary.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("key_new", "kuch hua!!!");
                new Asynch2("enter@99").execute();
                Asynch2.temp=true;
                Asynch2.temp2=true;
                //MainActivity.editTextTemporary.requestFocus();
                //MainActivity.editTextTemporary.setText("");
                return false;
            }
        });




    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Do your thing
            MainFunction.clickSend = 3;
            new Asynch().execute();
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            MainFunction.clickSend = 1;
            new Asynch().execute();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Do your thing
            MainFunction.clickSend = 4;
            new Asynch().execute();
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            MainFunction.clickSend = 2;
            new Asynch().execute();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            MainFunction.gx = event.values[0];
            MainFunction.gy = event.values[1];
            MainFunction.gz = event.values[2];

            try {
                MainFunction.clickSend = 10;
                new Asynch().execute();
            }
            catch (Exception e){
                Log.i("hello",e.toString());
                Log.i("hello","Server Closed");
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
