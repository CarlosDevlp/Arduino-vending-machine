package carlos.demo.com.avmachine.Model;

import java.util.ArrayList;

/**
 * Created by Carlos on 25/11/2016.
 */
public class Cliente {
    private String mNombre;
    private float mSaldo;
    private String mUidCard;

    public Cliente() {
        
    }


    public Cliente(String args[]) {
        mNombre = args[0];
        mSaldo = Float.parseFloat(args[1]);
        mUidCard = args[2];
    }

    public Cliente(String nombre, float saldo, String uidCard) {
        mNombre = nombre;
        mSaldo = saldo;
        mUidCard = uidCard;
    }


    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public float getSaldo() {
        return mSaldo;
    }

    public void setSaldo(float saldo) {
        mSaldo = saldo;
    }

    public String getUidCard() {
        return mUidCard;
    }

    public void setUidCard(String uidCard) {
        mUidCard = uidCard;
    }

    public String []toStringArray(){
        return new String[]{mNombre,mSaldo+"",mUidCard};
    }

    public void save() throws Exception{

        DBHelper dbHelper= DBHelper.getInstance();
        if(dbHelper.rowExists("CLIENTE","UID_CARD","'"+mUidCard+"'"))
            dbHelper.update("CLIENTE",new String[]{"SALDO"},
                    new String[]{mSaldo+""},"UID_CARD='"+mUidCard+"'");
        else
            dbHelper.insert("CLIENTE",new String[]{"NOMBRE","UID_CARD","SALDO"},
                                  new String[]{"'"+mNombre+"'","'"+mUidCard+"'",mSaldo+""});
    }


    public static Cliente searchCliente(String uidCard) throws Exception{
        ArrayList<String> aux;
        DBHelper dbHelper= DBHelper.getInstance();
        aux=dbHelper.responseToArray(dbHelper.select("CLIENTE",new String[]{"NOMBRE","SALDO","UID_CARD"},"UID_CARD='"+uidCard+"'")).get(0);
        return new Cliente(aux.get(0),Float.parseFloat(aux.get(1)),aux.get(2));
    }
}
