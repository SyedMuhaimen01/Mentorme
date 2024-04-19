package com.Muhaimen.i210888

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserData
    (

    var name: String,
    var email: String,
    var contactNumber: String,
    var country: String,
    var city: String,
    var password: String,
    val profilePicture: String? = "",

)
class Mentor (
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val imagePath: String,
    val sessionPrice: Double,
    val availability: String,
    val rating: Double
){

    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        0.0,
        "",
        0.0
    )
}


class MainActivity4 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        auth = FirebaseAuth.getInstance()

        val items = arrayOf("Select Country","Pakistan", "India", "Afghanistan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter

        val items2 = arrayOf("Select City","Islamabad", "Karachi", "Lahore")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner2: Spinner = findViewById(R.id.spinner2)
        spinner2.adapter = adapter2

        val button = findViewById<TextView>(R.id.signup)
        button.setOnClickListener {
            val name = findViewById<TextView>(R.id.nameEditText).text.toString()
            val email = findViewById<TextView>(R.id.emailEditText).text.toString()
            val contactNumber = findViewById<TextView>(R.id.contactNumberEditText).text.toString()
            val country = spinner.selectedItem.toString()
            val city = spinner2.selectedItem.toString()
            val password = findViewById<TextView>(R.id.passwordEditText).text.toString()

            val addOnCompleteListener = auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userData = UserData(name, email, contactNumber, country, city, password)
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(user!!.uid)
                            .setValue(userData)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("isLoggedIn", true)
                                    editor.apply()
                                    startActivity(Intent(this, MainActivity5::class.java))
                                    finish()
                                } else {
                                    // Handle database storing failure
                                }
                            }
                    } else {
                        // Handle authentication failure
                    }
                }
        }

        val button2=findViewById<TextView>(R.id.loginBtn)
        button2.setOnClickListener {
            startActivity(Intent(this, Main3Activity::class.java))
        }
    }
}
