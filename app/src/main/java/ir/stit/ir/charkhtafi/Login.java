package ir.stit.ir.charkhtafi;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import co.ronash.pushe.Pushe;
import ir.stit.ir.charkhtafi.AppController.AppController;
import ir.stit.ir.charkhtafi.ForgotPass.ForgotPassActivity;
import ir.stit.ir.charkhtafi.Network.StringRequest;
import ir.stit.ir.charkhtafi.Utils.Tools;
import ir.stit.ir.charkhtafi.Utils.Views.CFProvider;
import ir.stit.ir.charkhtafi.Utils.Views.CustomTextView;

public class Login extends AppCompatActivity {

    private TextInputLayout inputLayout1, inputLayout2;
    private LinearLayout Login_Root;
    private TextInputEditText Login_UserName, Login_Mobile;
    private Button Login_Btn;
    private AlertDialog progress;
    private ProgressBar Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        INI();
        ShowDialog();

        Login_Btn.setOnClickListener(v -> {
            LoginToApp(Login_UserName.getText().toString(), Login_Mobile.getText().toString());
        });
    }

    private void INI() {
        inputLayout1 = findViewById(R.id.Login_InputLayout1);
        inputLayout1.setTypeface(CFProvider.getIRANIANSANS(getApplicationContext()));
        inputLayout2 = findViewById(R.id.Login_InputLayout2);
        inputLayout2.setTypeface(CFProvider.getIRANIANSANS(getApplicationContext()));
        Login_Root = findViewById(R.id.Login_Root);
        Login_UserName = findViewById(R.id.Login_UserName);
        Login_UserName.setTypeface(CFProvider.getIRANIANSANS(getApplicationContext()));
        Login_Mobile = findViewById(R.id.Login_Mobile);
        Login_Mobile.setTypeface(CFProvider.getIRANIANSANS(getApplicationContext()));
        Login_Btn = findViewById(R.id.Login_Btn);
        Loading = findViewById(R.id.Login_Progress);
        TextView forgotPass = findViewById(R.id.Login_ForgotPass);
        forgotPass.setOnClickListener(v ->{
            startActivity(new Intent(getBaseContext(), ForgotPassActivity.class));
        });
    }

    private void ShowDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(R.layout.custom_progress);
        progress = dialog.create();
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater inflater = getLayoutInflater();
        progress.setContentView(inflater.inflate(R.layout.custom_progress, null));
        progress.setCancelable(true);
    }


    private void LoginToApp(String UserName, String Mobile) {

        if (!Tools.getInstance(getApplicationContext()).isOnline()) {
            Tools.getInstance(getApplicationContext())
                    .SnackBar(Login_Root, getResources().getString(R.string.InternetIsDisconnect), 1);
        } else if (UserName.equals("")) {
            Tools.getInstance(getApplicationContext())
                    .SnackBar(Login_Root, getResources().getString(R.string.UserNameNullError), 1);
        } else if (Mobile.equals("")) {
            Tools.getInstance(getApplicationContext())
                    .SnackBar(Login_Root, getResources().getString(R.string.MobileNullError), 1);
        } else {

            Loading.setVisibility(View.VISIBLE);
            Login_Btn.setText(" ");
            login(UserName, Mobile);

        }
    }

    public void login(String UserName, String Pass) {

        try {

            Map<String, String> params = new HashMap<>();
            params.put("OPR", "LOGIN");
            params.put("tel", UserName);
            params.put("pwd", Pass);
            params.put("pushId", Pushe.getPusheId(getBaseContext()));
            Log.e("pushId", Pushe.getPusheId(getBaseContext()) + " |");

            StringRequest loginRequest = new StringRequest(params, 0, new StringRequest.ResponseAction() {
                @Override
                public void onResponseAction(String response) {
                    Log.e("LoginResponse", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (object.has("error")) {
                            if (!object.getBoolean("error")) {

                                JSONObject objectMsg = object.getJSONObject("msg");

                                Tools.getInstance(getBaseContext()).write("UserId", objectMsg.getString("userid"));
                                Tools.getInstance(getBaseContext()).write("UserType", objectMsg.getString("usertype"));
                                Tools.getInstance(getBaseContext()).write("UserName", objectMsg.getString("username"));

                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                Loading.setVisibility(View.GONE);
                                Login_Btn.setText("ورود به سیستم");

                            } else {
                                Loading.setVisibility(View.GONE);
                                Login_Btn.setText("ورود به سیستم");
                                Toast.makeText(Login.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("ErrorLogin", e.toString() + " |");
                        Toast.makeText(Login.this, "خطایی در سیستم رخ داد لطفا دوباره امتحان کنید.", Toast.LENGTH_SHORT).show();
                        Loading.setVisibility(View.GONE);
                        Login_Btn.setText("ورود به سیستم");
                    }
                }

                @Override
                public void onErrorAction(VolleyError error) {
                    Log.e("ErrorLogin1", error.toString());
                    Toast.makeText(Login.this, "خطایی در سیستم رخ داد لطفا دوباره امتحان کنید.", Toast.LENGTH_SHORT).show();
                    Loading.setVisibility(View.GONE);
                    Login_Btn.setText("ورود به سیستم");
                }
            });

            AppController.getInstance().addToRequestQueue(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ErrorLogin2", e.toString());
            Toast.makeText(Login.this, "خطایی در سیستم رخ داد لطفا دوباره امتحان کنید.", Toast.LENGTH_SHORT).show();
            Loading.setVisibility(View.GONE);
            Login_Btn.setText("ورود به سیستم");
        }

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!Tools.getInstance(getApplicationContext()).read("UserId", "").trim().isEmpty()) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public static String encodeURIComponent(String s) {
        String result;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }
}
