package carlos.demo.com.avmachine.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.robotic.droid2ino.utils.Callback;

import carlos.demo.com.avmachine.Controller.RegisterCardController;
import carlos.demo.com.avmachine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCardFragment extends Fragment {

    private static RegisterCardFragment mRegisterCardFragment;
    private RegisterCardController mRegisterCardController;
    private Callback mCallback;

    public RegisterCardFragment() {
        // Required empty public constructor
    }

    public static RegisterCardFragment getInstance(){
        if(mRegisterCardFragment==null)
            mRegisterCardFragment=new RegisterCardFragment();
        return mRegisterCardFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_register_card, container, false);
        mRegisterCardController=new RegisterCardController(v);
        mRegisterCardController.setCallback(mCallback);
        return v;
    }


    public RegisterCardFragment setCallback(Callback callback){
        if(mRegisterCardController!=null)
            mRegisterCardController.setCallback(callback);
        else
            mCallback=callback;
        return this;
    }


    public void setCardUid(String cardUid){
        if(mRegisterCardController!=null)
            mRegisterCardController.setCardUID(cardUid);
    }
}
