package ir.stit.ir.charkhtafi.ForgotPass


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.VolleyError
import ir.stit.ir.charkhtafi.AppController.AppController
import ir.stit.ir.charkhtafi.Network.StringRequest

import ir.stit.ir.charkhtafi.R
import ir.stit.ir.charkhtafi.Utils.Tools
import kotlinx.android.synthetic.main.fragment_fp__verfy_sms.*
import org.json.JSONObject
import java.util.Observer

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FP_VerfySms : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(std: String) = FP_VerfySms().apply {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fp__verfy_sms, container, false);

        val text = view.findViewById<EditText>(R.id.FGVerifySms_Text)
        val btn = view.findViewById<Button>(R.id.FGVerifySms_Btn)
        Log.e("data", myStd+ " |");

        btn.setOnClickListener {
            if (Tools.getInstance(context).isOnline) {
                if (text.text.toString().length != 0) {
                    btn.text = ""
                    FGVerifySms_Progress.visibility = View.VISIBLE
                    Log.e("dasas", arguments?.getString("phone") + " |");
                    verifySms(text.text.toString(), arguments?.getString("phone").toString(), btn);
                } else {
                    Toast.makeText(context, "کد تایید نمیتواند خالی باشد!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "دسترسی به اینترنت موجود نیست!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun verifySms(smsCode: String, phone: String, btn : Button) {

        Log.e("sd", myStd + " | " + smsCode)
        val params = HashMap<String, String>();
        params.put("OPR", "FPWVERIFYSMSCODE")
        params.put("phone", myStd)
        params.put("code", smsCode)

        val verifyRequest = StringRequest(params, 0, object : StringRequest.ResponseAction {
            override fun onResponseAction(jsonObject: String?) {

                Log.e("VerifySMSResponse", jsonObject + " |");

                val myObject = JSONObject(jsonObject);

                if (myObject.has("error")) {
                    if (myObject.getString("error").equals("false")) {
                        FGVerifySms_Progress.visibility = View.GONE
                        btn.text = "ارسال"

                        val transaction = activity?.supportFragmentManager?.beginTransaction();
                        val fragment = ChangePss.newInstance(phone)
                        fragment.arguments?.putString("phone", phone)
                        transaction?.replace(R.id.ForgotPassActivityRoot, fragment)
                        transaction?.commit();

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


//Design Patterns: Elements of Reusable Object-Oriented Software (1994) is a software engineering book describing software design patterns
