package com.Muhaimen.i210888
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Main3Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        auth = FirebaseAuth.getInstance()

        val loginButton = findViewById<TextView>(R.id.loginButton)
        val emailEditText = findViewById<TextView>(R.id.emailEditText)
        emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                emailEditText.text = null // Clear the text when EditText gains focus
            }
        }
        val passwordEditText = findViewById<TextView>(R.id.passwordEditText)
        passwordEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                passwordEditText.text = null // Clear the text when EditText gains focus
            }
        }
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val sharedPreferences = getSharedPreferences("Users", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                        // Navigate to MainActivity8 or any other activity you want to go after successful login
                        startActivity(Intent(this, MainActivity8::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        val forgotPasswordButton = findViewById<TextView>(R.id.forgotpwd)
        forgotPasswordButton.setOnClickListener {
            startActivity(Intent(this, MainActivity6::class.java))
            finish()
        }

        val signupButton = findViewById<TextView>(R.id.signupBtn)
        signupButton.setOnClickListener {
            startActivity(Intent(this, MainActivity4::class.java))
        }
    }
}
