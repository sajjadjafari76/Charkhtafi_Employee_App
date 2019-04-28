package ir.stit.ir.charkhtafi.Network;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static ir.stit.ir.charkhtafi.Globals.ApiURL;


public class CustomRequest extends JsonObjectRequest{

    public CustomRequest(String url, JSONObject jsonRequest, final ResponseAction responseAction, int type) {
        super((type == 0) ? Method.POST : Method.GET, ApiURL + url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    responseAction.onResponseAction(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error!= null) {
                    responseAction.onErrorAction(error);
                }
            }
        });

        this.setRetryPolicy(new DefaultRetryPolicy(10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public CustomRequest(JSONObject jsonRequest, final ResponseAction responseAction, int type) {
        super((type == 0) ? Method.POST : Method.GET, ApiURL , jsonRequest,
                response -> {
                    if (response != null) {
                        responseAction.onResponseAction(response);
                    }
                }, error -> {
                    if (error!= null) {
                        responseAction.onErrorAction(error);
                    }
                });

        this.setRetryPolicy(new DefaultRetryPolicy(10000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

     public static abstract class ResponseAction {
         public abstract void onResponseAction(JSONObject jsonObject);
         public abstract void onErrorAction(VolleyError error);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("AUTHORIZE", "hpnwE4gXmffbX0hNPOb5qVkLwcJVrFZh3cpDQC8W69uPVeIMS2rooh7RRdJnSRs2n4SH9ypAEClRa471lrBnsCxjgtQhxsTMoaVjMD9M9KmPWjUleeV7wqrPbAi1PCyNZmAGpUU3t0A0wlB59RTXv1KCG064P7zTzqdYHftEOL68jDgfWnC0iouBeEdWMtOa8iMoRI3vTpzNJhTol0Kf4qZiesbxshDsgDrGzY3ntzlZQqzcxcWlYx4TJZFHaI0PUmawLS4Lav2RwQbqIz1pRInAcwxasH8UdcFMoCPs9vxJ3bH8JSFVHaWg6mUYls5fg4du3Y5spMRQItwsMUnk14p5tsDzp6vpvmctMGwDct4Xb2MconyQ6f3JDvXkQfiqDzTUKSSsN5uAchh0joWgnKStD9mb36SZcFwZ3oo6LduB5vG0CECn3fRDlh9P9MIDl6nL2uDOEeNDx2G85e21Eu0MsfbIyQpjqI52OLp5BWLdDgq9Mjzto4IoJwAyTHGuj2kq4Rani0bfEGXuCEz3OTslbQEwiA2OAi4T1dhHfwvOYDOWMTEMPstrQKJD158Ls0dGmAX3CY03Wq23vidMiJV343ugQeNsXXfSqIZBopFwSIvco9pTDqFffz132N5g");
        return params;
    }
//
//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        HashMap<String, String> params = new HashMap<>();
////        params.put("Content-Type", "application/json");
//        params.put("Accept", "sajjad");
//        Log.e("eeee", params.toString() + " |");
//        return params;
//    }
}
