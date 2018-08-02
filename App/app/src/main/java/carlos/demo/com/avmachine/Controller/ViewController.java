package carlos.demo.com.avmachine.Controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bq.robotic.droid2ino.utils.Callback;

import carlos.demo.com.avmachine.View.MainViewActivity;

/**
 * Created by Carlos on 25/11/2016.
 */
public class ViewController {
    protected View mRoot;
    protected Callback mCallback;

    protected ViewController(View root){
        mRoot=root;
    }

    public void setCallback(Callback callback){
        mCallback=callback;
    }

    protected void showMessage(String text){
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainViewActivity.mContext);
        alertDialog.setTitle("WALL-E-Shop");
        alertDialog.setMessage(text);


        MainViewActivity.mMe.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.create().show();
            }
        });
    }


    protected void askQuestion(String text,final Callback callbackAnswer){

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainViewActivity.mContext);
        alertDialog.setTitle("WALL-E-Shop");
        alertDialog.setMessage(text);
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(callbackAnswer!=null)
                    callbackAnswer.execute(new String[]{"yes"});
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(callbackAnswer!=null)
                    callbackAnswer.execute(new String[]{"no"});
            }
        });


        MainViewActivity.mMe.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.create().show();
            }
        });
    }

}
