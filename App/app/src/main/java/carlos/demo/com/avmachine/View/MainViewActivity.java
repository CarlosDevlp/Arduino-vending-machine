package carlos.demo.com.avmachine.View;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity;
import com.bq.robotic.droid2ino.utils.Callback;

import carlos.demo.com.avmachine.Helpers.HttpClient;
import carlos.demo.com.avmachine.Model.DBHelper;
import carlos.demo.com.avmachine.R;

public class MainViewActivity extends BaseBluetoothConnectionActivity {
    private StringBuffer mOutStringBuffer;// String buffer for outgoing messages
    private final String  LOG_TAG="MainViewActivity";
    private String mRfidUID;
    private String mActualfragment;
    public static Context mContext;
    public static MainViewActivity mMe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_activiy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestDeviceConnection();
                view.setVisibility(View.GONE);
            }
        });

        DBHelper.setContext(this);
        mMe=this;
            //isConnected()
        mOutStringBuffer = new StringBuffer("");

        //carlosUID="aed210d1";
        //sincronizar de una vez
        //requestDeviceConnection();
        mContext=this;
        mRfidUID="";

        mBluetoothConnection.setCallback(new Callback() {
            @Override
            public void execute(String[] args) {
                Log.d("app-command-from-avm",args[0]);

                mRfidUID+=args[0];
                if(mRfidUID.contains("x") && mRfidUID.contains("y")){
                    mRfidUID=mRfidUID.replace("x","");
                    mRfidUID=mRfidUID.replace("y","");
                    Log.d(LOG_TAG,mRfidUID);
                    //entrara a la cuenta del usuario
                    if(mActualfragment.equals("LandingFragment"))
                        LandingFragment.getInstance().searchCliente(mRfidUID);
                    else
                        RegisterCardFragment.getInstance().setCardUid(mRfidUID);

                    mRfidUID="";
                }

            }
        });


        HttpClient.setContext(this);

        showLandingFragment();
    }

    /*private void logInToCostumerAccount(){
        if(mRfidUID.equals(carlosUID)){
            showRegisterVendingFragment();
            sendMessage("1");
        }else
            sendMessage("0");
        mRfidUID="";
    }*/


    private void showLandingFragment(){
        mActualfragment="LandingFragment";
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,LandingFragment.getInstance().setCallback(new Callback() {
            @Override
            public void execute(String[] args) {
                if(args!=null) {
                    showRegisterVendingFragment();
                    RegisterVendingFragment.getInstance().setCliente(args);
                }else
                    showRegisterCardFragment();

            }
        })).commit();
    }

    private void showRegisterVendingFragment(){
        mActualfragment="VendingFragment";
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,RegisterVendingFragment.getInstance().setCallback(new Callback() {
                    @Override
                    public void execute(String[] args) {
                        sendMessage(args[0]);
                        //enviar y cerrar sesión si contiene cero
                        if(args[0].contains("0"))
                            showLandingFragment();
                    }
                }))
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .commit();
    }


    private void showRegisterCardFragment(){
        mActualfragment="CardFragment";
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,RegisterCardFragment
                                    .getInstance()
                                    .setCallback(new Callback() {
                                        @Override
                                        public void execute(String[] args) {
                                           //volver al landing
                                            showLandingFragment();
                                        }
                                    }))
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .commit();
    }

    @Override
    public void onNewMessage(String message) {

    }


}
