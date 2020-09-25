package com.example.touristapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp


//import com.google.firebase.database.core.view.View

class SignInActivity : AppCompatActivity() {

    private var etEmail: EditText?=null
    private var etPassword: EditText?=null
    private var btnLogin: Button?=null
    private var tvForgot: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tvForgot = findViewById<View>(R.id.tv_forgot_password) as TextView
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.btn_login) as Button

        btnLogin!!.setOnClickListener(View.OnClickListener {
            if (etPassword!!.getText().toString() == "" || etEmail!!.getText()
                    .toString() == "" || etPassword!!.getText().toString()
                    .isEmpty() || etEmail!!.getText().toString().isEmpty()
            ) {
                Toast.makeText(
                    applicationContext,
                    R.string.Pusta_rejestracja,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val fbdb = DatabaseHandler()
                val login=ArrayList<Char>()
                val em= etEmail!!.text.toString()


                fbdb.checkIfUserExistsAndLogin(
                    etEmail!!.getText().toString(),
                    etPassword!!.getText().toString(),
                    object : DatabaseHandler.DataStatus {
                        override fun dataInserted() {}
                        override fun dataUpdated() {}
                        override fun dataLoaded() {}
                        override fun dataExists() {
                            //fbdb.user.eMail
                            // Toast.makeText(getApplicationContext(),R.string.LogIn,Toast.LENGTH_SHORT).show();
                            val MainPageIntent = Intent(this@SignInActivity, MainActivity::class.java)
                            sendHtmlEmail(fbdb.user.eMail.toString())
                            MainPageIntent.putExtra("User", fbdb.user)
                            Log.i("logIn","dataExsists : intent")
                            //haveAccountDialog.dismiss()
                            startActivity(MainPageIntent)
                        }

                        override fun databaseFailure() {}
                        override fun dataExistsNot() {
                            Log.i("logIn","dataExsistsNot : Toast")
                            Toast.makeText(
                                applicationContext,
                                R.string.LoginFailed,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    })
            }
        })

    tvForgot!!.setOnClickListener{
        val textString = "Forgot password?"
        val spanText = Spannable.Factory.getInstance().newSpannable(textString)
        spanText.setSpan(BackgroundColorSpan(-0x100), 14, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvForgot!!.setText(spanText)
    }
    }

    fun sendHtmlEmail(email:String) {
        val mailId = "yourmail@gmail.com"
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here")
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<p><b>Some Content</b></p>" +
                "http://www.google.com" + "<small><p>More content</p></small>"))
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

fun signIn(){
    fun onClick(view: View?) {
        if (etPassword?.text.toString() == "" || etEmail?.getText()
                .toString() == "" || etPassword?.getText().toString().isEmpty() || etEmail?.getText()
                .toString().isEmpty()
        ) {
            Toast.makeText(applicationContext, R.string.Pusta_rejestracja, Toast.LENGTH_SHORT)
                .show()
        } else {
            val fbdb = DatabaseHandler()
            fbdb.checkIfUserExistsAndLogin(
                etEmail?.text.toString(),
                etPassword?.getText().toString(),
                object : DatabaseHandler.DataStatus {
                    override fun dataInserted() {}
                    override fun dataUpdated() {}
                    override fun dataLoaded() {}
                    override fun dataExists() {
                        // Toast.makeText(getApplicationContext(),R.string.LogIn,Toast.LENGTH_SHORT).show();
                        val MainPageIntent = Intent(this@SignInActivity, MainActivity::class.java)
                        MainPageIntent.putExtra("User", fbdb.user)
                       // haveAccountDialog.dismiss()
                        startActivity(MainPageIntent)
                    }

                    override fun databaseFailure() {}
                    override fun dataExistsNot() {
                        Toast.makeText(
                            applicationContext,
                            R.string.LoginFailed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}

   private fun loginUser(){
       FirebaseApp.initializeApp(this)
    val dbhd=DatabaseHandler()
    dbhd.SignIn(etEmail?.text.toString(), etPassword?.text.toString())
       if(dbhd.userExsist) {
           val intent = Intent(this, MainActivity::class.java)
           intent.putExtra("User", dbhd.user)
           startActivity(intent)
       }
       else{
           Toast.makeText(this, "Login failed.",
                 Toast.LENGTH_SHORT).show()
       }
    }
}


