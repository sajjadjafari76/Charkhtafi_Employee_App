package ir.stit.ir.charkhtafi.ForgotPass

import android.content.Context
import android.net.Uri
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
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import ir.stit.ir.charkhtafi.AppController.AppController
import ir.stit.ir.charkhtafi.Network.CustomRequest
import ir.stit.ir.charkhtafi.Network.StringRequest

import ir.stit.ir.charkhtafi.R
import ir.stit.ir.charkhtafi.Utils.Tools
import kotlinx.android.synthetic.main.fragment_fp__get_phone.*
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FP_GetPhone.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class FP_GetPhone: Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_fp__get_phone, container, false)


        val text = view.findViewById<EditText>(R.id.FGGetPhone_Text)
        val btn = view.findViewById<Button>(R.id.FGGetPhone_Btn)

        btn.setOnClickListener {
            if (Tools.getInstance(context).isOnline) {
                if (text.text.toString().equals("") || text.text.toString().length < 10) {
                    Toast.makeText(context, "شماره وارد شده اشتباه است!" , Toast.LENGTH_SHORT).show()
                }else{
                    FGGetPhone_Progress.visibility = View.VISIBLE
                    btn.text = ""
                    sendPhoneNumber(text.text.toString(), btn)
                }
            }else{
                Toast.makeText(context, "دسترسی به اینترنت موجود نیست!" , Toast.LENGTH_SHORT).show()
            }
        }

        return view;
    }

    private fun sendPhoneNumber(phone : String, btn : Button) {

        val params = HashMap<String, String>();
        params.put("OPR", "FPWVERIFYPHONE")
        params.put("phone", phone)

        val request = StringRequest(params, 0, object : StringRequest.ResponseAction {
            override fun onResponseAction(jsonObject: String) {
                Log.e("GetPhoneResponse", jsonObject + " |")

                val myObject = JSONObject(jsonObject);

                if (myObject.has("error")) {
                    if (myObject.getString("error").equals("false")) {

                        FGGetPhone_Progress.visibility = View.GONE
                        btn.text = "ارسال"
                        val transaction = activity?.supportFragmentManager?.beginTransaction();

                        Log.e("data11", phone + " |");
                        val fragment = FP_VerfySms.newInstance(phone)
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

            override fun onErrorAction(volley: VolleyError) {
                Log.e("GetPhoneError", volley.toString() + " |")
                Toast.makeText(context, "خطایی رخ داد", Toast.LENGTH_SHORT).show();
            }
        })

        AppController.getInstance().addToRequestQueue(request)

    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
