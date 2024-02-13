package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView

class MainActivity10 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main10)

        val items = arrayOf("Filter","aaa","bbbb")

// Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Apply the adapter to the spinner
        val spinner: Spinner = findViewById(R.id.spinner3)
        spinner.adapter = adapter


        var button=findViewById<ImageButton>(R.id.back5)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2=findViewById<ImageButton>(R.id.home2)
        button2.setOnClickListener {
            val intent2 = Intent(this, MainActivity8::class.java)
            startActivity(intent2)
        }
        var button3=findViewById<ImageButton>(R.id.add3)
        button3.setOnClickListener {
            val intent3 = Intent(this, MainActivity13::class.java)
            startActivity(intent3)
        }

        var button4=findViewById<ImageButton>(R.id.myprofile)
        button4.setOnClickListener {
            val intent4 = Intent(this, MainActivity21::class.java)
            startActivity(intent4)
        }

        var button6=findViewById<ImageButton>(R.id.chat)
        button6.setOnClickListener {
            val intent6 = Intent(this, MainActivity15::class.java)
            startActivity(intent6)
        }

        var button7=findViewById<ImageButton>(R.id.search)
        button7.setOnClickListener {
            val intent7 = Intent(this, MainActivity9::class.java)
            startActivity(intent7)
        }

    }
}