package com.Muhaimen.i210888
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MainActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        val searchButton = findViewById<ImageButton>(R.id.searchButton)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)

        searchButton.setOnClickListener {
            val mentorName = searchEditText.text.toString().trim()
            if (mentorName.isNotEmpty()) {
                val intent = Intent(this, MainActivity10::class.java)
                intent.putExtra("mentorName", mentorName)
                startActivity(intent)
            } else {
                // Handle empty search query
                Toast.makeText(this, "Please enter a mentor name", Toast.LENGTH_SHORT).show()
            }
        }

        val button2 = findViewById<ImageButton>(R.id.back4)
        button2.setOnClickListener {
            onBackPressed()
        }

        val button3 = findViewById<ImageButton>(R.id.home)
        button3.setOnClickListener {
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<ImageButton>(R.id.add2)
        button4.setOnClickListener {
            val intent = Intent(this, MainActivity13::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<ImageButton>(R.id.myprofile)
        button5.setOnClickListener {
            val intent = Intent(this, MainActivity21::class.java)
            startActivity(intent)
        }

        val button6 = findViewById<ImageButton>(R.id.chat)
        button6.setOnClickListener {
            val intent = Intent(this, MainActivity15::class.java)
            startActivity(intent)
        }
    }
}
