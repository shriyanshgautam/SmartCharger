package com.shriyansh.battery;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shriyansh on 4/5/15.
 */
public class BatteryReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Level",Toast.LENGTH_SHORT).show();
        Vibrator v= (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        //start service to connect to arduino and signal
        context.startService(new Intent(context,MyService.class));

    }
}
