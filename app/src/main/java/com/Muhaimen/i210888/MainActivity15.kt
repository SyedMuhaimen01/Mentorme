package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity15 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main15)

        var button=findViewById<ImageButton>(R.id.back10)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2=findViewById<TextView>(R.id.msg)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity16::class.java)
            startActivity(intent2)
        }

        var button3=findViewById<ImageButton>(R.id.home4)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity8::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<ImageButton>(R.id.search4)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity8::class.java)
            startActivity(intent4)
        }

        var button5=findViewById<ImageButton>(R.id.add4)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity13::class.java)
            startActivity(intent5)
        }

        var button6=findViewById<ImageButton>(R.id.chat)
        button6.setOnClickListener {
            val intent6 = Intent(this, MainActivity15::class.java)
            startActivity(intent6)
        }

        var button7=findViewById<ImageButton>(R.id.myprofile)
        button7.setOnClickListener {
            val intent7 = Intent(this, MainActivity21::class.java)
            startActivity(intent7)
        }
    }
}