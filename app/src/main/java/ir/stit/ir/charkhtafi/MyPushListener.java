package ir.stit.ir.charkhtafi;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import co.ronash.pushe.PusheListenerService;
import ir.stit.ir.charkhtafi.Utils.Tools;

public class MyPushListener extends PusheListenerService {
    @Override
    public void onMessageReceived(JSONObject customContent, JSONObject pushMessage) {
        if (customContent == null || customContent.length() == 0)
            return; //json is empty
        android.util.Log.i("Pushe", "Custom json Message: " + customContent.toString()); //print json to logCat
        try {
            if (customContent.getString("status").equals("1")) {
                if (customContent.getString("logoff").equals("true")) {
                    if (Tools.getInstance(getBaseContext()).read("UserId", "")
                            .equals(customContent.getString("userid"))) {
                        Tools.getInstance(getBaseContext()).write("UserId", "");
                        Tools.getInstance(getBaseContext()).write("UserType", "");
                        Tools.getInstance(getBaseContext()).write("UserName", "");

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }

        } catch (JSONException e) {
            android.util.Log.e("TAG", "Exception in parsing json", e);
        }
    }
}
