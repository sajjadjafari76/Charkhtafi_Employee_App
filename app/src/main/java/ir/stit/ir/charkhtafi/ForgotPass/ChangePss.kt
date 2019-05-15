package ir.stit.ir.charkhtafi.ForgotPass

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.android.volley.VolleyError
import ir.stit.ir.charkhtafi.AppController.AppController
import ir.stit.ir.charkhtafi.Login
import ir.stit.ir.charkhtafi.MainActivity
import ir.stit.ir.charkhtafi.Network.StringRequest
import ir.stit.ir.charkhtafi.R
import ir.stit.ir.charkhtafi.Utils.Tools
import kotlinx.android.synthetic.main.activity_change_pss.*
import org.json.JSONObject

class ChangePss : Fragment() {


    companion object {
        @JvmStatic
        fun newInstance(std: String) = ChangePss().apply {
            arguments = Bundle().apply {
                putString("REPLACE WITH A STRING CONSTANT", std)
            }
        }
    }

    private var myStd = ""
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString("REPLACE WITH A STRING CONSTANT")?.let {
            myStd = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_change_pss, container, false)

        val ChangePass_Btn = view.findViewById<Button>(R.id.ChangePass_Btn1);

        ChangePass_Btn.setOnClickListener {
            if (Tools.getInstance(context).isOnline) {
                if (ChangePass_Input.text.toString().length == 0) {
                    Toast.makeText(context, "رمز نمیتواند خالی باشد!", Toast.LENGTH_SHORT).show()
                } else if (ChangePass_InputConfirm.text.toString().length == 0) {
                    Toast.makeText(context, "تایید رمز نمیتواند خالی باشد!", Toast.LENGTH_SHORT).show()
                } else if (!ChangePass_Input.text.toString().equals(ChangePass_InputConfirm.text.toString())) {
                    Toast.makeText(context, "رمز عبور یکسان نیستند!", Toast.LENGTH_SHORT).show()
                } else {
                    ChangePass_Prgress.visibility = View.VISIBLE
                    ChangePass_Btn.text = ""
                    ChangePass(ChangePass_Input.text.toString(), arguments?.getString("phone").toString(), ChangePass_Btn)
                }
            } else {
                Toast.makeText(context, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show()
            }
        }
        return view;
    }

    private fun ChangePass(pass: String, phone: String, ChangePass_Btn : Button) {

        val params = HashMap<String, String>();
        params.put("OPR", "FPWNEWPASSWORD")
        params.put("phone", myStd)
        params.put("pwd", pass)

        val verifyRequest = StringRequest(params, 0, object : StringRequest.ResponseAction {
            override fun onResponseAction(jsonObject: String?) {


                val myObject = JSONObject(jsonObject);
                ChangePass_Prgress.visibility = View.GONE
                ChangePass_Btn.text = "ارسال"

                if (myObject.has("error")) {
                    if (myObject.getString("error").equals("false")) {
                        ChangePass_Prgress.visibility = View.GONE
                        ChangePass_Btn.text = "ارسال"
                       startActivity(Intent(context, Login().javaClass))

                    }else {
                        Toast.makeText(context, myObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, myObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }

            }

            override fun onErrorAction(error: VolleyError?) {

            }
        })

        AppController.getInstance().addToRequestQueue(verifyRequest)

    }

}
