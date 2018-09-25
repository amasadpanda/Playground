package p.root.firebasetest;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Tim on 3/29/18.
 */

// Not sure about making this extends Application, probably bad practice to make it global
public class Network{

    private static RequestQueue requestToServer = null;
    private static String SERVER_URL = null;

    private static Context context = null;

    public RequestQueue getRequestQueue()
    {
        if(requestToServer == null)
        {
            initRequestQueue();
        }
      return requestToServer;
    }

    public static <T> void addRequest(Request<T> r)
    {

        requestToServer.add(r);
    }

    // This is bad, the response is not parsed or used. Should only be used to notify the server
    // of something
    public static void addMapRequest(final Map<String, String> map)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        }){
            protected Map<String, String> getParams()
            {
                return map;
            }
        };
    }

    private static void initRequestQueue()
    {
        requestToServer = Volley.newRequestQueue(GlobalThings.getAppContext());
        SERVER_URL = "localhost:8080";
    }

}

class LOL implements Response.Listener{

    @Override
    public void onResponse(Object response) {

    }
}
