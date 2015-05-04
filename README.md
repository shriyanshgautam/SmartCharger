# SmartCharger
Disconnecting charger automtically for Smarphones through Android and Arduino

This project uses arduino to connect and disconnect the charger connected to smartphone when charging is full.
The intent "android.intent.action.BATTERY_CHANGED" is fired whenever the percentage to battery level increases or decreases.
The broadcast receiver is registered to receive that broadcast and check for a full charge and send a signal 'L' if it is fully charged.
The signal is transmited in background service through bluetooth to arduino and using the transistor we disconnect the charger.

Currently the broadcast receiver is for "android.intent.action.ACTION_POWER_CONNECTED", so that whenever a charger is connected the signal is transmitted to led at pin 13 in arduino as HIGH.

Prerequisite state : 
1. HC-05 Bluetooth module must be paired to the smaprtphone.
2. Bluetooth Adapter of Smartphone must me in ON state.

