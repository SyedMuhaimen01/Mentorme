package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity16 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        var button=findViewById<ImageButton>(R.id.back11)
        button.setOnClickListener {
            onBackPressed()
        }


        var button5=findViewById<ImageButton>(R.id.chat)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity15::class.java)
            startActivity(intent5)



        }
        var button4=findViewById<ImageButton>(R.id.myprofile)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity21::class.java)
            startActivity(intent4)
        }

        var button3=findViewById<ImageButton>(R.id.home5)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity8::class.java)
            startActivity(intent3)
        }

        var button8=findViewById<ImageButton>(R.id.camera)
        button8.setOnClickListener {
            val intent8= Intent(this, MainActivity18::class.java)
            startActivity(intent8)
        }

        var button9=findViewById<ImageButton>(R.id.videoCall)
        button9.setOnClickListener {
            val intent9 = Intent(this, MainActivity20::class.java)
            startActivity(intent9)
        }


        var button2=findViewById<ImageButton>(R.id.call)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity23::class.java)
            startActivity(intent2)
        }
    }
}