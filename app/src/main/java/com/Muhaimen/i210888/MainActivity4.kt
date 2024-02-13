package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val items = arrayOf("Select Country","Pakistan", "India", "Afghanistan")

// Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Apply the adapter to the spinner
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter

        val items2 = arrayOf("Select City","Islamabad", "Karachi", "Lahore")

// Create an ArrayAdapter using the string array and a default spinner layout
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)

// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Apply the adapter to the spinner
        val spinner2: Spinner = findViewById(R.id.spinner2)
        spinner2.adapter = adapter2


        var button=findViewById<TextView>(R.id.signup)
        button.setOnClickListener {
            val intent1 = Intent(this, MainActivity5::class.java)
            startActivity(intent1)
        }


        var button2=findViewById<TextView>(R.id.loginBtn)
        button2.setOnClickListener {
            val intent2 = Intent(this, Main3Activity::class.java)
            startActivity(intent2)
        }
    }
}