package carlos.demo.com.avmachine.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.robotic.droid2ino.utils.Callback;


import carlos.demo.com.avmachine.Controller.RegisterVendingController;
import carlos.demo.com.avmachine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterVendingFragment extends Fragment {

    private RegisterVendingController mRegisterVendingController ;
    private static RegisterVendingFragment mRegisterVendingFragment;
    private Callback mCallback;
    private String[] mCliente;
    private int times;
    public RegisterVendingFragment() {
        // Required empty public constructor
        times=0;
    }

    public static RegisterVendingFragment getInstance(){
        if(mRegisterVendingFragment==null)
            mRegisterVendingFragment=new RegisterVendingFragment();
        return mRegisterVendingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_register_vending, container, false);
        mRegisterVendingController= new RegisterVendingController(v);
        mRegisterVendingController.setCallback(mCallback);
        if(mCliente!=null) {
            mRegisterVendingController.setCliente(mCliente);
        }
        return v;
    }


    public RegisterVendingFragment setCallback(Callback callback){
        if(mRegisterVendingController!=null)
            mRegisterVendingController.setCallback(callback);
        else
            mCallback=callback;
        return this;
    }
    
    public void setCliente(String[] args){
        /*if(mRegisterVendingController!=null) {
            mRegisterVendingController.setCliente(args);
            times++;
        }else*/
            mCliente=args;
    }
}
