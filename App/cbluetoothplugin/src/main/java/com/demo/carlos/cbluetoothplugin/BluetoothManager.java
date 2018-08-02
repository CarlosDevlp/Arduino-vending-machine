package com.demo.carlos.cbluetoothplugin;

import com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity;
import com.bq.robotic.droid2ino.utils.Callback;

/**
 * Created by Carlos on 14/08/2016.
 */
public class BluetoothManager extends BaseBluetoothConnectionActivity {
    //bluetooth
    private final String BLUETOOTH_DEVICE="20:15:05:29:53:32";
    private StringBuffer mOutStringBuffer;// String buffer for outgoing messages
    private static BluetoothManager mBluetoothManager;


    private static void getInstance(){
        if(mBluetoothManager==null)
            mBluetoothManager=new BluetoothManager();

    }
    //métodos estáticos
    private static void startScanning(){
        mBluetoothManager.start();
    }

    public static void sentMessage(String txt){
        mBluetoothManager.sendMessage(txt);
    }




    private void start(){
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        requestDeviceConnection();

        mBluetoothConnection.setCallback(new Callback() {
            @Override
            public void execute(String[] args) {


            }
        });

    }


    @Override
    public void onNewMessage(String message) {

    }
}
