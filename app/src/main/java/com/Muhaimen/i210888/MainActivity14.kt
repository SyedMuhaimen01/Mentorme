package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity14 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main14)

        var button=findViewById<ImageButton>(R.id.back9)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2=findViewById<TextView>(R.id.book)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity8::class.java)
            startActivity(intent2)
        }
    }
}