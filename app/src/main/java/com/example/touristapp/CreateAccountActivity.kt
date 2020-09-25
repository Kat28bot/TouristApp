package com.example.touristapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.touristapp.ui.login.LoginFormState
import com.example.touristapp.ui.login.afterTextChanged
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {
    private var etUsername: EditText?=null
    private var etEmail: EditText?=null
    private var etPassword: EditText?=null
    private var btnCreateAcc: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        etUsername = findViewById(R.id.et_username) as EditText
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnCreateAcc = findViewById<View>(R.id.btn_register) as Button


        etEmail!!.afterTextChanged {
            if(!isUserNameValid(etEmail!!.text.toString())){
                etEmail!!.setError("hhh")
            }
            else{

            }
        }
        etPassword!!.afterTextChanged {
            if(!isPasswordValid(etPassword!!.text.toString())){
            etPassword!!.setError("hhh")
            }
            else{

            }
        }
       // btnCreateAcc!!.setOnClickListener {
         //   registerUser()
        //}
        btnCreateAcc!!.setOnClickListener(View.OnClickListener {
            if (etPassword!!.getText().toString() == "" || etUsername!!.getText().toString() == ""||
                    etEmail!!.text.toString()=="") {
                Toast.makeText(
                    applicationContext,
                    R.string.Pusta_rejestracja,
                    Toast.LENGTH_SHORT
                ).show()
            }else if(etUsername!!.text.toString().contains('.')||
                etUsername!!.text.toString().contains('[')||
                etUsername!!.text.toString().contains(']')||
                etUsername!!.text.toString().contains('#')||
                etUsername!!.text.toString().contains('$')){
                Toast.makeText(
                    applicationContext,
                    "Login can't contain symbols: [, ], #, $",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                sendHtmlEmail()

                val fbdb = DatabaseHandler()
                fbdb.checkIfUserExistsAndRegister(
                    etUsername!!.getText().toString(),
                    object : DatabaseHandler.DataStatus {
                        override fun dataInserted() {}
                        override fun dataUpdated() {}
                        override fun dataLoaded() {}
                        override fun dataExists() {
                            Toast.makeText(
                                applicationContext,
                                R.string.uz_istnieje,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun databaseFailure() {
                            Toast.makeText(
                                applicationContext,
                                R.string.DataBase_failure,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun dataExistsNot() {
                            val us = User(
                                etUsername!!.getText().toString(),
                                etPassword!!.getText().toString(),
                                etEmail!!.text.toString()
                            )
                            fbdb.addUser(us, object : DatabaseHandler.DataStatus {
                                override fun dataInserted() {
                                    //registerDialog.dismiss()
                                    Toast.makeText(
                                        applicationContext,
                                        R.string.rejestracja,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                override fun dataUpdated() {}
                                override fun dataLoaded() {}
                                override fun dataExists() {}
                                override fun databaseFailure() {
                                    Toast.makeText(
                                        applicationContext,
                                        R.string.DataBase_failure,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                override fun dataExistsNot() {}
                            })
                        }
                    })
            }
        })

    }

    fun sendHtmlEmail() {
        val mailId = "yourmail@gmail.com"
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mailId, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here")
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<p><b>Some Content</b></p>" +
                "http://www.google.com" + "<small><p>More content</p></small>"))
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    private fun registerUser(){
        val dbhd=DatabaseHandler()
        val us= etUsername?.text.toString()
        val em = etEmail?.text.toString()
        val ps= etPassword?.text.toString()
        dbhd.SignUp(us,em,ps)
      //  dbhd.addUser(dbhd.user)

    }
    fun loginDataChanged(username: String, password: String) {
        val _loginForm = MutableLiveData<LoginFormState>()
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
        // return  username.isNotBlank()
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
