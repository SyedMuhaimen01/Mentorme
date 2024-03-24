package com.Muhaimen.i210888
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity21 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var usernameTextView: TextView
    private lateinit var locationTextView: TextView

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedImage ->
            uploadImageToFirebaseStorage(selectedImage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main21)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        storage = Firebase.storage

        usernameTextView = findViewById(R.id.username)
        locationTextView = findViewById(R.id.location)

        val buttonEditProfile = findViewById<ImageButton>(R.id.edit2)
        buttonEditProfile.setOnClickListener {
            openGalleryForImage()
        }

        val logoutButton = findViewById<TextView>(R.id.logout)
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Main3Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        var button5 = findViewById<ImageButton>(R.id.edit)
        button5.setOnClickListener {
            val intent5 = Intent(this, MainActivity22::class.java)
            startActivity(intent5)
        }

        var button4 = findViewById<ImageButton>(R.id.home3)
        button4.setOnClickListener {
            onBackPressed()
        }

        // Fetch and display user profile information
        displayUserProfile()
    }

    private fun openGalleryForImage() {
        getContent.launch("image/*")
    }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        // Upload image to Firebase Storage
    }

    private fun displayUserProfile() {
        Log.d("MainActivity21", "displayUserProfile() called")
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            val userRef = database.reference.child("users").child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("MainActivity21", "onDataChange()")
                    val username = snapshot.child("name").getValue(String::class.java)
                    val location = snapshot.child("city").getValue(String::class.java)
                    val imageUrl = snapshot.child("profilePictureUrl").getValue(String::class.java)
                    Log.d("MainActivity21", "Username: $username, Location: $location, Image URL: $imageUrl")

                    // Set fetched username and location to TextViews
                    username?.let { usernameTextView.text = it }
                    location?.let { locationTextView.text = it }

                    // Load user image if available
                    imageUrl?.let { url ->
                        Glide.with(this@MainActivity21)
                            .load(url)
                            .circleCrop() // Apply circular transformation
                            .into(findViewById(R.id.profileImageView)) // Assuming the ImageView's ID is profileImageView
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseDatabase", "Error fetching user profile: ${error.message}")
                }
            })
        }
    }


}
