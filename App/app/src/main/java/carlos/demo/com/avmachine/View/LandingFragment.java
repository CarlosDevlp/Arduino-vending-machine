package carlos.demo.com.avmachine.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bq.robotic.droid2ino.utils.Callback;

import carlos.demo.com.avmachine.Controller.LandingController;
import carlos.demo.com.avmachine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {

    private static LandingFragment mLandingFragment;
    private LandingController mLandingController;
    private Callback mCallback;
    public LandingFragment() {

    }

    public static LandingFragment getInstance(){
        if(mLandingFragment==null)
            mLandingFragment=new LandingFragment();

        return mLandingFragment;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_landing, container, false);
        mLandingController = new LandingController(v);
        mLandingController.setCallback(mCallback);
        return v;
    }


    public LandingFragment setCallback(Callback callback){
        if(mLandingController!=null)
            mLandingController.setCallback(callback);
        else
            mCallback=callback;
        return this;
    }

    public void searchCliente(String uidCard){
        mLandingController.searchCliente(uidCard);
    }

}
