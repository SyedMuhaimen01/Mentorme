package com.Muhaimen.i210888
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        // Get the flag from shared preferences
        val sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn)
        {
            // User is already logged in, navigate to home screen
            navigateToHome()
        } else {

            // Navigate to login screen
            navigateToLogin()
        }
    }

    private fun navigateToSignup() {
        Handler().postDelayed({ // Implement navigation to signup screen after delay
            val intent = Intent(this@MainActivity, MainActivity4::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }

    private fun navigateToLogin() {
        Handler().postDelayed({ // Implement navigation to signup screen after delay
            val intent = Intent(this@MainActivity, Main3Activity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }

    private fun navigateToHome() {
        // Implement navigation to home screen
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, MainActivity8::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}