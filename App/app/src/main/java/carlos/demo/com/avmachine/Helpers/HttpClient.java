package carlos.demo.com.avmachine.Helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import carlos.demo.com.avmachine.R;

public class HttpClient {
    private RequestQueue mQueue;
    private static HttpClient mHttpClient;
    private static Context mContext;
    public String WEBSERVICE_DOMAIN;
    private HttpClient(Context context){
        mQueue = Volley.newRequestQueue(context);
        WEBSERVICE_DOMAIN=context.getString(R.string.web_ervice_dominio);
    }

    public static void setContext(Context context){
        mContext=context;
    }

    public static  HttpClient getInstance(){
        if(mHttpClient==null)
            mHttpClient=new HttpClient(mContext);
        return  mHttpClient;
    }


    public void addRequest(Request request){
        mQueue.add(request);
    }

}
