package com.example.anirudh.airsense;
//UDP thread
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Anirudh on 11-Apr-16.
 */
class Asynch extends AsyncTask {
    protected Object doInBackground(Object [] var){
        try{
            if(MainFunction.send){
                byte [] tempBytes;
                InetAddress inet;
                DatagramPacket dataPack;
                switch (MainFunction.clickSend){
                    case 0:
                        StringBuilder string = new StringBuilder();
                        MainFunction.temp=string.append(MainFunction.gx).append(",").append(MainFunction.gy).append(",").append(MainFunction.gz).append(",").toString();
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        break;
                    case 1:
                    {
                        MainFunction.temp="LeftDown";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }

                    case 2:{

                        MainFunction.temp="LeftUp";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 3:{

                        MainFunction.temp="RightDown";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 4:{
                        MainFunction.temp="RightUp";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 5:{
                        MainFunction.temp="LeftClick";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 6:{
                        MainFunction.temp="RightClick";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 7:{
                        MainFunction.temp="scroll_up";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 8:{
                        MainFunction.temp="scroll_down";
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        MainFunction.clickSend=0;
                        break;
                    }
                    case 10:{
                        string = new StringBuilder();
                        MainFunction.temp=string.append(MainFunction.gx).append(",").append(MainFunction.gy).append(",").append(MainFunction.gz).append(",").toString();
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        break;
                    }





                    default:{
                        string = new StringBuilder();
                        MainFunction.temp=string.append(MainFunction.gx).append(",").append(MainFunction.gy).append(",").append(MainFunction.gz).append(",").toString();
                        tempBytes=MainFunction.temp.getBytes();
                        inet=InetAddress.getByName(MainFunction.globalIP);
                        dataPack=new DatagramPacket(tempBytes,tempBytes.length,inet,MainFunction.port1);
                        MainFunction.sock.send(dataPack);
                        break;
                    }
                }
            }
        }
        //catch (Exception e){}
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }
}

