package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)


        var button=findViewById<ImageButton>(R.id.search)
        button.setOnClickListener {
            val intent1 = Intent(this, MainActivity9::class.java)
            startActivity(intent1)
        }

        var button2=findViewById<TextView>(R.id.profile)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity11::class.java)
            startActivity(intent2)
        }
        var button3=findViewById<ImageButton>(R.id.add1)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity13::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<ImageButton>(R.id.myprofile)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity21::class.java)
            startActivity(intent4)
        }



        var button5=findViewById<ImageButton>(R.id.notifications)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity25::class.java)
            startActivity(intent5)
        }

        var button6=findViewById<ImageButton>(R.id.chat)
        button6.setOnClickListener {
            val intent6 = Intent(this, MainActivity15::class.java)
            startActivity(intent6)
        }
    }
}