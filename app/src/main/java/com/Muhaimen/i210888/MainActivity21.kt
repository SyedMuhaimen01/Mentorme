package com.Muhaimen.i210888

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity21 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main21)

        var button=findViewById<TextView>(R.id.bookedSessions)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity22::class.java)
            startActivity(intent)
        }

        var button2=findViewById<ImageButton>(R.id.edit)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity24::class.java)
            startActivity(intent2)
        }

        var button3=findViewById<ImageButton>(R.id.edit2)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity24::class.java)
            startActivity(intent3)
        }

        var button5=findViewById<ImageButton>(R.id.myprofile)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity21::class.java)
            startActivity(intent5)
        }

        var button9=findViewById<ImageButton>(R.id.home3)
        button9.setOnClickListener {
            val intent9 = Intent(this, MainActivity8::class.java)
            startActivity(intent9)
        }

        var button4=findViewById<ImageButton>(R.id.search)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity9::class.java)
            startActivity(intent4)
        }

        var button6=findViewById<ImageButton>(R.id.chat)
        button6.setOnClickListener {
            val intent6 = Intent(this, MainActivity15::class.java)
            startActivity(intent6)
        }
    }
}