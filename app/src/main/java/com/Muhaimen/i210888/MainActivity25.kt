package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity25 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main25)

        var button5=findViewById<ImageButton>(R.id.notifications)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity25::class.java)
            startActivity(intent5)
        }

        var button9=findViewById<ImageButton>(R.id.home)
        button9.setOnClickListener {
            val intent9 = Intent(this, MainActivity8::class.java)
            startActivity(intent9)
        }
    }
}