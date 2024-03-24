package com.Muhaimen.i210888

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity7 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        auth = FirebaseAuth.getInstance()

        var button = findViewById<ImageButton>(R.id.back3)
        button.setOnClickListener {
            onBackPressed()
        }

        var button2 = findViewById<TextView>(R.id.resetPwd)
        button2.setOnClickListener {
            resetPassword()
        }

        var button3 = findViewById<TextView>(R.id.login3)
        button3.setOnClickListener {
            val intent3 = Intent(this, Main3Activity::class.java)
            startActivity(intent3)
        }
    }

    private fun resetPassword() {
        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)

        val newPassword = newPasswordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (newPassword != confirmPassword) {
            // Show error message or toast indicating passwords do not match
            return
        }

        val user: FirebaseUser? = auth.currentUser

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password updated successfully
                    // You may redirect the user to a success page or perform other actions
                } else {
                    // Password update failed
                    // You may display an error message to the user
                }
            }
    }
}
