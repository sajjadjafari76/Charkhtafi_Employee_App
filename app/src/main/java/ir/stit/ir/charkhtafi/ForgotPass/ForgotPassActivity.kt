package ir.stit.ir.charkhtafi.ForgotPass

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.stit.ir.charkhtafi.R

class ForgotPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        val transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.ForgotPassActivityRoot, FP_GetPhone())
        transaction.commit();


//        val sharedViewModel = SharedViewModel().inputNumber.observe(this, Observer {
//            it.let {
////                tv_show
//            }
//        })

    }

}
