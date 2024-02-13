package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity11 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main11)

        var button=findViewById<ImageButton>(R.id.back6)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2=findViewById<TextView>(R.id.bookSession)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity14::class.java)
            startActivity(intent2)
        }

        var button3=findViewById<TextView>(R.id.review)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity12::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<TextView>(R.id.community)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity17::class.java)
            startActivity(intent4)
        }
    }
}