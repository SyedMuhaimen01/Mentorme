package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class Main3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var button=findViewById<TextView>(R.id.loginButton)
        button.setOnClickListener {
            val intent1 = Intent(this, MainActivity8::class.java)
            startActivity(intent1)
        }


        var button2=findViewById<TextView>(R.id.forgotpwd)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity6::class.java)
            startActivity(intent2)
        }


        var button3=findViewById<TextView>(R.id.signupBtn)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity4::class.java)
            startActivity(intent3)
        }
    }
}