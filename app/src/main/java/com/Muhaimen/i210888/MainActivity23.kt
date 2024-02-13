package com.Muhaimen.i210888

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity23 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main23)

        var button=findViewById<ImageButton>(R.id.close)
        button.setOnClickListener {
            onBackPressed()
        }
    }
}