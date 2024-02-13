package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity17 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main17)

        var button=findViewById<ImageButton>(R.id.back12)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2=findViewById<ImageButton>(R.id.call)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity23::class.java)
            startActivity(intent2)
        }

        var button3=findViewById<ImageButton>(R.id.videoCall)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity20::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<ImageButton>(R.id.camera)
        button4.setOnClickListener {
            val intent4= Intent(this, MainActivity18::class.java)
            startActivity(intent4)
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

        var button8=findViewById<ImageButton>(R.id.search3)
        button8.setOnClickListener {
            val intent8 = Intent(this, MainActivity9::class.java)
            startActivity(intent8)
        }

        var button9=findViewById<ImageButton>(R.id.home)
        button9.setOnClickListener {
            val intent9 = Intent(this, MainActivity8::class.java)
            startActivity(intent9)
        }
    }
}