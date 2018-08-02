package carlos.demo.com.avmachine.Controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import carlos.demo.com.avmachine.Helpers.HttpClient;
import carlos.demo.com.avmachine.Model.Cliente;
import carlos.demo.com.avmachine.R;
import carlos.demo.com.avmachine.View.MainViewActivity;

/**
 * Created by Carlos on 25/11/2016.
 */
public class RegisterCardController extends ViewController{

    private Cliente mCliente;
    private EditText mEdtName;
    private EditText mEdtCardUI;
    private EditText mEdtCredit;
    private Button mBtnRegister;
    private final String LOG_TAG="RegisterCardController";
    private final String LOG_TAG_ERROR="RegisterCardController-Error";


    public RegisterCardController(View root){

        super(root);

        mEdtName=(EditText) mRoot.findViewById(R.id.edt_name);
        mEdtCardUI=(EditText) mRoot.findViewById(R.id.edt_card_uid);
        mEdtCredit=(EditText) mRoot.findViewById(R.id.edt_credit);
        mBtnRegister= (Button) mRoot.findViewById(R.id.btn_register);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCliente = new Cliente(mEdtName.getText().toString(), Float.parseFloat(mEdtCredit.getText().toString()), mEdtCardUI.getText().toString());
                    mCliente.save();
                    mCallback.execute(null);
                    registrarNuevoClienteEnServidor();
                }catch (Exception err){
                    showMessage("Datos Inválidos");
                }
            }
        });

    }

    private void registrarNuevoClienteEnServidor(){
        /*
         {
            "uid":"xyz789",
            "nombre":"Dota2",
            "saldo":125
        }
         */
        HttpClient httpClient=HttpClient.getInstance();
        JSONObject clienteJson=new JSONObject();
        try{
            clienteJson.put("uid",mEdtCardUI.getText().toString());
            clienteJson.put("nombre",mEdtName.getText().toString());
            clienteJson.put("saldo",mEdtCredit.getText().toString());

            httpClient.addRequest(new JsonObjectRequest(
                                          Request.Method.POST,
                                          httpClient.WEBSERVICE_DOMAIN + "/cliente",
                                          clienteJson,
                                          new Response.Listener<JSONObject>() {
                                              @Override
                                              public void onResponse(JSONObject response) {
                                                  try{
                                                    if(response.getInt("error")==0){
                                                        showMessage("El cliente se ha registrado exitosamente");
                                                    }
                                                  }catch(JSONException err){}

                                              }
                                          },
                                          new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {
                                                  showMessage("No se ha podido registrar el cliente en el servidor");
                                              }
                                          }){
                                      /**
                                       * Passing some request headers
                                       * */
                                      @Override
                                      public Map<String, String> getHeaders() throws AuthFailureError {
                                          HashMap<String, String> headers = new HashMap<String, String>();
                                          headers.put("Content-Type", "application/json; charset=utf-8");
                                          return headers;
                                      }
                                  }
            );
        }catch(JSONException err){}




    }


    public void setCardUID(final String cardUID){
        MainViewActivity.mMe.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEdtCardUI.setText(cardUID);
            }
        });

    }



}
