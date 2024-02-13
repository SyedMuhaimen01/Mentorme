package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView

class MainActivity7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)


        var button=findViewById<ImageButton>(R.id.back3)
        button.setOnClickListener {
            onBackPressed()
        }


        var button2=findViewById<TextView>(R.id.resetPwd)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity8::class.java)
            startActivity(intent2)
        }


        var button3=findViewById<TextView>(R.id.login3)
        button3.setOnClickListener {
            val intent3 = Intent(this, Main3Activity::class.java)
            startActivity(intent3)
        }
    }
}