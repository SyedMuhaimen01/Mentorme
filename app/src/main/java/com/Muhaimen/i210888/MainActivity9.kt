package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        var button=findViewById<TextView>(R.id.cat1)
        button.setOnClickListener {
            val intent1 = Intent(this, MainActivity10::class.java)
            startActivity(intent1)
        }

        var button2=findViewById<TextView>(R.id.cat2)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity10::class.java)
            startActivity(intent2)
        }

        var button3=findViewById<TextView>(R.id.cat3)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity10::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<ImageButton>(R.id.back4)
        button4.setOnClickListener {
            onBackPressed()
        }

        var button5=findViewById<ImageButton>(R.id.home)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity8::class.java)
            startActivity(intent5)
        }

        var button6=findViewById<ImageButton>(R.id.add2)
        button6.setOnClickListener {
            val intent6 = Intent(this, MainActivity13::class.java)
            startActivity(intent6)
        }

        var button7=findViewById<ImageButton>(R.id.myprofile)
        button7.setOnClickListener {
            val intent4 = Intent(this, MainActivity21::class.java)
            startActivity(intent4)
        }

        var button8=findViewById<ImageButton>(R.id.search)
        button8.setOnClickListener {
            val intent8 = Intent(this, MainActivity9::class.java)
            startActivity(intent8)
        }

        var button9=findViewById<ImageButton>(R.id.chat)
        button9.setOnClickListener {
            val intent9 = Intent(this, MainActivity15::class.java)
            startActivity(intent9)
        }
    }
}