package com.shriyansh.battery;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    BluetoothAdapter mBluetoothAdapter;
    public static final String TAG="akshita";
    BtInterface bt=null;
    static final int REQUEST_ENABLE_BT = 3;

    //This handler listens to messages from the bluetooth interface and adds them to the log
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String data = msg.getData().getString("receivedData");
            // addToLog(data);
        }
    };

    //this handler is dedicated to the status of the bluetooth connection
    final Handler handlerStatus = new Handler() {
        public void handleMessage(Message msg) {
            int status = msg.arg1;
            if(status == BtInterface.CONNECTED) {
                //addToLog("Connected");
            } else if(status == BtInterface.DISCONNECTED) {
                //addToLog("Disconnected");
            }
        }
    };

    /** indicates how to behave if the service is killed */
    int mStartMode;
    /** interface for clients that bind */
    IBinder mBinder;
    /** indicates whether onRebind should be used */
    boolean mAllowRebind;


    /** Called when the service is being created. */
    @Override
    public void onCreate() {

    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //first of all, we check if there is bluetooth on the phone
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.v(TAG, "Device does not support Bluetooth");
        } else {
            //Device supports BT
            if (!mBluetoothAdapter.isEnabled()) {
                //if Bluetooth not activated, then request it
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                //BT activated, then initiate the BtInterface object to handle all BT communication
                bt = new BtInterface(handlerStatus, handler);
                //start conncetion to arduino
                new Task().execute();
            }
        }
        return mStartMode;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */

    public class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           //conect to arduino
            bt.connect();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //send data to arduino
            bt.sendData("H");
            //close th connection
            bt.close();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //sleep for 3 seconds for connection to establish
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
