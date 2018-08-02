package carlos.demo.com.avmachine.Controller;

import android.util.Log;
import android.view.View;
import carlos.demo.com.avmachine.Model.Cliente;
import carlos.demo.com.avmachine.R;

/**
 * Created by Carlos on 25/11/2016.
 */
public class LandingController extends ViewController{

    public LandingController(View root) {
        super(root);
        //mClienteList=new ArrayList<>();
        (root.findViewById(R.id.btn_add_cards)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.execute(null);
            }
        });
    }

    public void searchCliente(String uidCard){
        try{
            Cliente cliente;
            cliente=Cliente.searchCliente(uidCard);
            //Log.d("---Aviso---","cliente con saldo: "+cliente.getSaldo());
            mCallback.execute(cliente.toStringArray());

        }catch(Exception err){
            showMessage("Tarjeta Inválida");
            Log.d("---Error---",err.toString());
        }

    }
}
