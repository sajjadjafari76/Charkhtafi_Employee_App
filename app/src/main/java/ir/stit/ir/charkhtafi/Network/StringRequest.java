package ir.stit.ir.charkhtafi.Network;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import java.util.HashMap;
import java.util.Map;

import ir.stit.ir.charkhtafi.Globals;

public class StringRequest extends com.android.volley.toolbox.StringRequest {

    private Map<String, String> params;

    public StringRequest(Map<String, String> params, int type, ResponseAction responseAction) {
        super((type == 0) ? Method.POST : Method.GET, Globals.ApiURL,
                response -> {
                    if (response != null) {
                        responseAction.onResponseAction(response);
                    }
                }, error -> {
                    if (error != null) {
                        responseAction.onErrorAction(error);
                    }
                });

        this.params = params;

        this.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public interface ResponseAction {
        public void onResponseAction(String jsonObject);

        public void onErrorAction(VolleyError error);

    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("AUTHORIZE", "hpnwE4gXmffbX0hNPOb5qVkLwcJVrFZh3cpDQC8W69uPVeIMS2rooh7RRdJnSRs2n4SH9ypAEClRa471lrBnsCxjgtQhxsTMoaVjMD9M9KmPWjUleeV7wqrPbAi1PCyNZmAGpUU3t0A0wlB59RTXv1KCG064P7zTzqdYHftEOL68jDgfWnC0iouBeEdWMtOa8iMoRI3vTpzNJhTol0Kf4qZiesbxshDsgDrGzY3ntzlZQqzcxcWlYx4TJZFHaI0PUmawLS4Lav2RwQbqIz1pRInAcwxasH8UdcFMoCPs9vxJ3bH8JSFVHaWg6mUYls5fg4du3Y5spMRQItwsMUnk14p5tsDzp6vpvmctMGwDct4Xb2MconyQ6f3JDvXkQfiqDzTUKSSsN5uAchh0joWgnKStD9mb36SZcFwZ3oo6LduB5vG0CECn3fRDlh9P9MIDl6nL2uDOEeNDx2G85e21Eu0MsfbIyQpjqI52OLp5BWLdDgq9Mjzto4IoJwAyTHGuj2kq4Rani0bfEGXuCEz3OTslbQEwiA2OAi4T1dhHfwvOYDOWMTEMPstrQKJD158Ls0dGmAX3CY03Wq23vidMiJV343ugQeNsXXfSqIZBopFwSIvco9pTDqFffz132N5g");
        return params;
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}

