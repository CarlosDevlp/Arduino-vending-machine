package carlos.demo.com.avmachine.Controller;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bq.robotic.droid2ino.utils.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import carlos.demo.com.avmachine.Helpers.HttpClient;
import carlos.demo.com.avmachine.Model.Cliente;
import carlos.demo.com.avmachine.R;
import carlos.demo.com.avmachine.View.MainViewActivity;

/**
 * Created by Carlos on 25/11/2016.
 */
public class RegisterVendingController extends ViewController {
    private Cliente mCliente;
    private Button mBtnBuy;
    private CheckBox mChItem1;
    private CheckBox mChItem2;
    private CheckBox mChItem3;
    private TextView mTxtTotal;
    private TextView mTxtGreetings;
    private TextView mTxtCredit;
    private String command;
    private String mProductos;
    private DecimalFormat mDecimalFormat;
    private float mTotal;
    public RegisterVendingController(View root) {
        super(root);
         mTotal= 0.0f;
         mBtnBuy = (Button) root.findViewById(R.id.btn_buy);
         mChItem1 = (CheckBox) root.findViewById(R.id.ch_item1);
         mChItem2 = (CheckBox) root.findViewById(R.id.ch_item2);
         mChItem3 = (CheckBox) root.findViewById(R.id.ch_item3);
         mTxtTotal = (TextView) root.findViewById(R.id.txt_total);
         mTxtGreetings= (TextView) root.findViewById(R.id.txt_greetings);
         mTxtCredit= (TextView) root.findViewById(R.id.txt_credit);
         mProductos="";

        mChItem1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float amount=0.1f;
                calculateTotal((isChecked?1:-1)*amount);
            }
        });

        mChItem2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float amount=0.1f;
                calculateTotal((isChecked?1:-1)*amount);
            }
        });

        mChItem3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                float amount=0.2f;
                calculateTotal((isChecked?1:-1)*amount);
            }
        });

        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command="1t";
                boolean valid=false;
                if(mChItem2.isChecked()) {
                    command += "a";
                    mProductos+="Halls Mentho Lyptus, ";
                    valid=true;
                }
                if(mChItem3.isChecked()) {
                    command += "b";
                    mProductos+="Chiclets Adams, ";
                    valid=true;
                }
                command+="s";
                if(valid) {
                    askQuestion("¿Quieres seguir comprando?", new Callback() {
                        //yes or no
                        @Override
                        public void execute(String[] args) {
                            try {
                                mCliente.setSaldo(mCliente.getSaldo()-mTotal);
                                mCliente.save();
                                if (args[0].equals("no")) {//finaliza sesión
                                    command += "0";
                                }
                                else{
                                    MainViewActivity.mMe.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mTxtCredit.setText(" Saldo Actual S/."+
                                                    mCliente.getSaldo());
                                        }
                                    });
                                }

                                registrarVentaEnServidor();

                                MainViewActivity.mMe.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mChItem1.setChecked(false);
                                        mChItem2.setChecked(false);
                                        mChItem3.setChecked(false);
                                    }
                                });

                                mCallback.execute(new String[]{command});
                            }catch (Exception err){
                                showMessage("Error del sistema");
                            }
                        }
                    });
                }else
                    showMessage("Debe escoger un producto a comprar");
            }
        });

        mDecimalFormat=new DecimalFormat("0.00");
    }

    private void registrarVentaEnServidor(){
        /*
            {
                "uid":"xyz789",
                "fecha":"12/12/2012",
                "monto":12.5,
                "productos":"caramelo con leche"
            }
        */

        HttpClient httpClient=HttpClient.getInstance();
        JSONObject ventaJson=new JSONObject();
        try{
            Date hoy = java.util.Calendar.getInstance().getTime();
            ventaJson.put("uid",mCliente.getUidCard());
            ventaJson.put("fecha", new SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
                                                .format(hoy));
            ventaJson.put("monto",mTotal);
            ventaJson.put("productos",mProductos);

            httpClient.addRequest(new JsonObjectRequest(
                      Request.Method.POST,
                      httpClient.WEBSERVICE_DOMAIN + "/venta",
                      ventaJson,
                      new Response.Listener<JSONObject>() {
                          @Override
                          public void onResponse(JSONObject response) {
                              try{
                                  if(response.getInt("error")==0){
                                      showMessage("La venta se ha registrado exitosamente");
                                  }
                              }catch(JSONException err){}

                          }
                      },
                      new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              showMessage("No se ha podido registrar la venta en el servidor");
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

    private void calculateTotal(float amount){
        mTotal+=amount;

        MainViewActivity.mMe.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtTotal.setText("Total: "+ mDecimalFormat.format(mTotal));
            }
        });

    }

    public Cliente getCliente(){
        return  mCliente;
    }
    public void setCliente(String args[]){
        mCliente=new Cliente(args);

        MainViewActivity.mMe.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxtGreetings.setText("Bienvenido "+mCliente.getNombre()+", ¿Qué deseas comprar?");
                mTxtCredit.setText(" Saldo Actual S/."+mCliente.getSaldo());
            }
        });


    }
}
