package hackupc.skytrips.connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by whdinata on 10/8/16.
 */
public class STRequestQueue {
    private static STRequestQueue stRequestQueue;
    private static Context context;
    private RequestQueue requestQueue;

    private STRequestQueue(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized STRequestQueue getInstance(Context context){
        if(stRequestQueue == null){
            stRequestQueue = new STRequestQueue(context);
        }

        return stRequestQueue;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
