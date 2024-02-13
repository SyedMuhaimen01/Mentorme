package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView

class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)


        var button2=findViewById<ImageButton>(R.id.back2)
        button2.setOnClickListener {
            onBackPressed()
        }

        var button=findViewById<TextView>(R.id.pwdBtn)
        button.setOnClickListener {
            val intent1 = Intent(this, MainActivity7::class.java)
            startActivity(intent1)
        }


        var button3=findViewById<TextView>(R.id.login2)
        button3.setOnClickListener {
            val intent3 = Intent(this, Main3Activity::class.java)
            startActivity(intent3)
        }
    }
}