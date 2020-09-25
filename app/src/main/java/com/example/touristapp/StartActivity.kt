package com.example.touristapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.FirebaseApp

class StartActivity : AppCompatActivity() {

    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        FirebaseApp.initializeApp(this);
        btnSignIn = findViewById(R.id.btnLogin) as Button
        btnSignIn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        btnSignUp = findViewById(R.id.btnSignup) as Button
        btnSignUp.setOnClickListener{
            val intent2 = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent2)
        }
    }
}
