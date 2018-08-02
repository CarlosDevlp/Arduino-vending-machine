package carlos.demo.com.avmachine.Model;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Carlos on 25/11/2016.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static DBHelper dbh;
    private static  Context ctx;
    private SQLiteDatabase db;
    private static final String DATABASE_NAME="avmachine";

    //constructor
    private DBHelper(){
        super(ctx,DATABASE_NAME,null,1);
        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
    public static void setContext(Context _ctx){
        ctx=_ctx;
    }
    public static DBHelper getInstance(){
        if(dbh==null && ctx!=null)
            dbh=new DBHelper();
        return dbh;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //tabla cliente
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS CLIENTE( " +
                        "UID_CARD  VARCHAR(15) PRIMARY KEY,  " +
                        "NOMBRE    VARCHAR(80)," +
                        "SALDO     INTEGER" +
                        " );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //tabla tutorial
        db.execSQL("DROP TABLE IF EXISTS CLIENTE;");
        onCreate(db);
    }
    public  void resetAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS CLIENTE;");
        onCreate(db);
    }

    public void insert(String table_name,String []columns,String []values){
        db = getWritableDatabase();
        String query="INSERT INTO ";
        String aux1,aux2;

        query+=table_name;

        aux1=aux2="";
        for(int i=0;i<columns.length;i++){
            if(i!=0) {
                aux1+=" , ";
                aux2+=" , ";
            }
            aux1+=columns[i];
            aux2+=values[i];
        }
        query+=" ("+aux1+") VALUES ("+aux2+")";
        db.execSQL(query);
        db.close();
    }
    public Cursor select(String table_name,String []columns,String condition){
        db = this.getWritableDatabase();
        String query="SELECT ";
        if(columns.length>0)
            for(int col=0;col<columns.length;col++){
                if(col!=0) query+=" , ";
                query+=columns[col];
            }
        else
            query+=" * ";

        query+=" FROM "+table_name;

        if(condition!=null && !condition.trim().equals(""))
            query+=" WHERE "+condition;
        //Log.d("query",query);
        //ANSWER ZONE
        return db.rawQuery(query, null);

    }
    public Cursor select(String[] tables_name,String[] tables_alias,String []columns_to_match,String []columns,String condition){

        db = this.getWritableDatabase();
        String query="SELECT ";
        if(columns.length>0)
            for(int col=0;col<columns.length;col++){
                if(col!=0) query+=" , ";
                query+=columns[col];
            }
        else
            query+=" * ";
        query+=" FROM "+tables_name[0]+" AS "+tables_alias[0];
        //join
        query+=" INNER JOIN "+tables_name[1]+" AS "+tables_alias[1];
        query+=" ON "+tables_alias[0]+"."+columns_to_match[0]+"="+tables_alias[1]+"."+columns_to_match[1];

        if(condition!=null && !condition.trim().equals(""))
            query+=" WHERE "+condition;

        //ANSWER ZONE
        return db.rawQuery(query, null);
    }


    public void update(String table_name,String []columns,String []values,String condition){
        db = getWritableDatabase();
        String query="UPDATE ";
        query+=table_name+" SET ";

        for(int i=0;i<columns.length;i++){
            if(i!=0) query+=" , ";
            query+=columns[i]+" = "+values[i];
        }
        if(condition!=null && !condition.trim().equals(""))
            query+=" WHERE "+condition;
        db.execSQL(query);
        db.close();
    }

    public void delete(String table_name,String condition){
        db = this.getWritableDatabase();
        String query="DELETE FROM ";
        query+=table_name;
        if(condition!=null && !condition.trim().equals(""))
            query+=" WHERE "+condition;
        db.execSQL(query);
        db.close();
    }

    public void delete(String[] tables_name,String[] tables_alias,String []columns_to_match,String condition){
        db = this.getWritableDatabase();
        String query="DELETE "+tables_alias[0]+" FROM ";
        query+=tables_name[0]+" AS "+tables_alias[0];
        query+=" INNER JOIN "+tables_name[1]+" AS "+tables_alias[1];
        query+=" ON "+tables_alias[0]+"."+columns_to_match[0]+"="+tables_alias[1]+"."+columns_to_match[1];

        if(condition!=null && !condition.trim().equals(""))
            query+=" WHERE "+condition;
        db.execSQL(query);
        db.close();
    }


    public ArrayList<ArrayList<String> > responseToArray(Cursor csr){
        ArrayList<ArrayList<String> > res= new ArrayList<>();
        csr.moveToFirst();

        int row=0;
        String []columns=csr.getColumnNames();
        while(!csr.isAfterLast()){
            res.add(new ArrayList<String>());
            for(String col : columns)
                res.get(row).add( csr.getString(csr.getColumnIndex(col)) );

            csr.moveToNext();
            row++;
        }
        if(db.isOpen())
            db.close();
        return res;
    }
    public ArrayList< Map<String, String > > responseToArrayOfMaps(Cursor csr){
        ArrayList< Map<String, String > > res =new ArrayList<>();


        csr.moveToFirst();
        int row=0;
        String []columns=csr.getColumnNames();
        while(!csr.isAfterLast()){
            res.add(new HashMap<String, String>());

            for(String col : columns)
                (res.get(row)).put(col, csr.getString(csr.getColumnIndex(col)));

            csr.moveToNext();
            row++;
        }

        if(db.isOpen())
            db.close();
        return res;
    }
    public int tableSize(String tb_name){
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, tb_name);
    }

    public boolean rowExists(String tb_name,String column_to_match,String value_to_match){
        Cursor csr=(select(tb_name,new String[]{"*"},column_to_match+" = "+value_to_match));
        return !( responseToArray(csr) ).isEmpty();
    }
    public boolean tableIsEmpty(String tb_name){
        SQLiteDatabase db = getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tb_name);
        return numRows<=0;
    }


}
