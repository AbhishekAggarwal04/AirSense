package com.example.anirudh.airsense;
//TCP thread
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Anirudh on 11-Apr-16.
 */
//Thread for TCP Connections

class Asynch2 extends AsyncTask<Void,Void,Long > {

    static boolean temp=false;
    static boolean temp2=false;
    static String str;
    Asynch2(CharSequence s){
        str=s+"";
        //MainActivity.editTextTemporary.setText("");
    }


    @Override
    protected Long doInBackground(Void... params) {
        try {
            if(MainFunction.sendTCP) {
                MainFunction.dos.writeUTF(str);
                Log.i("send",str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Long temp){
        //Log.i("hello","execute");
        if(Asynch2.temp){
            Log.i("hello","execute");
            MainFunction.editTextTemporary.setText("");
            Asynch2.temp=false;
        }
        if(Asynch2.temp2){
            try {
                //Thread.sleep(200);
                MainFunction.keyboardOnOffButton.performClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Asynch2.temp2=false;
        }
    }
}

