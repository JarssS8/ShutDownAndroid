package com.jars.shutdownapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Action;

public class ConnectionThread extends Thread {


    private Action action;
    private Context mContext;

    public ConnectionThread(Action action, Context mContext) {
        this.action = action;
        this.mContext = mContext;
    }

    @Override
    public void run() {
        if (isConnected()) {
            if (action.getType() != null) {
                try {

                    Socket socket = new Socket("192.168.18.7", 3000);
                    new Socket();
                    socket.setSoTimeout(6000);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    objectOutputStream.writeObject(action);
                    String response = (String) objectInputStream.readObject();
                    objectOutputStream.close();
                    objectInputStream.close();
                } catch (IOException ex) {
                    Log.d("THREAD", "Server is OFF");
                    System.out.println(ex.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.d("THREAD", "Class not found");
                }
            }
        }
        else{
            Log.d("THREAD","No connection to internet");
        }
    }

    public boolean isConnected() {
        boolean connection = false;
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            connection = activeNetworkInfo != null && activeNetworkInfo.isConnected();


        } catch (Exception e) {
            Log.e("Connection", e.getMessage());
        }
        return connection;
    }


}
