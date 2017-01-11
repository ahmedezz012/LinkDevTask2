package com.ezz.linkdevtask.helpers;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezz.linkdevtask.constants.Constants;

/**
 * Created by Ahmed.Ezz on 1/4/2017.
 */
public class Services {
    private static Services ourInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private Services(Context context) {
          this.context = context;
          requestQueue = getRequestQueue();
    }
    public static synchronized Services getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Services(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
    public void makeRequestTogetListNews(Response.Listener<String> response,Response.ErrorListener error)
    {
        if( response!=null && error !=null) {
            addToRequestQueue(new StringRequest(Request.Method.GET, Constants.NewsListURL, response, error));
        }
    }
    public void makeRequestTogetNewDetails(String Newid,Response.Listener<String> response,Response.ErrorListener error)
    {
        if(!TextUtils.isEmpty(Newid) && response!=null && error !=null) {
            addToRequestQueue(new StringRequest(Request.Method.GET, Constants.NewDetailURL + Newid, response, error));
        }
    }
}
