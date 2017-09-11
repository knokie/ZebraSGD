package com.zebra.sgdsample.zebrasgd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Looper;
import android.util.Log;

import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.SGD;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String theBtMacAddress = "A0:E6:F8:61:76:E1";
        sendZplOverBluetooth(theBtMacAddress);


    }


    private void sendZplOverBluetooth(final String theBtMacAddress) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    // Instantiate insecure connection for given Bluetooth&reg; MAC Address.
                    Connection thePrinterConn = new BluetoothConnectionInsecure(theBtMacAddress);

                    // Initialize
                    Looper.prepare();

                    // Open the connection - physical connection is established here.
                    thePrinterConn.open();

                    // Setting device language to ZPL via SGD
                    SGD.SET("device.languages", "hybrid_xml_zpl", thePrinterConn);

                    // Reading device language
                    String deviceLanguages = SGD.GET("device.languages", thePrinterConn);

                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(500);

                    // Output device language to log
                    Log.i("Device Languages", deviceLanguages);

                    // Close the insecure connection to release resources.
                    thePrinterConn.close();
                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Handle communications error here.
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
