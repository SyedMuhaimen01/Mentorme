package com.Muhaimen.i210888
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity11 : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var mentorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main11)

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().reference

        // Retrieve mentor details from intent
        val mentorName = intent.getStringExtra("mentorName")
        val mentorDescription = intent.getStringExtra("mentorDescription")
        val mentorProfileImageUriString = intent.getStringExtra("mentorProfileImage")
        mentorId = intent.getStringExtra("mentorId") ?: ""
        val mentorSessionPrice = intent.getDoubleExtra("mentorsessionPrice", 0.0)
        val mentorTitle = intent.getStringExtra("mentorTitle")

        // Set mentor details in the appropriate fields
        findViewById<TextView>(R.id.nameEditText).text = mentorName
        findViewById<TextView>(R.id.TitleEditText).text = mentorTitle
        findViewById<TextView>(R.id.descriptionEditText).text = mentorDescription

        // Set rounded corners for profile image
        val imageView = findViewById<ImageView>(R.id.profileImage)
        val requestOptions = RequestOptions().transform(CircleCrop())

        // Load mentor profile image using Glide
        val mentorProfileImageUri = mentorProfileImageUriString?.let { Uri.parse(it) }
        if (mentorProfileImageUri != null) {
            Glide.with(this)
                .load(mentorProfileImageUri)
                .apply(requestOptions)
                .into(imageView)
        }

        // Set up back button click listener
        val backButton = findViewById<ImageButton>(R.id.back6)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set up button click listeners for other actions
        val bookSessionButton = findViewById<TextView>(R.id.bookSession)
        bookSessionButton.setOnClickListener {
            navigateToMainActivity14(mentorName, mentorDescription, mentorProfileImageUriString, mentorSessionPrice)
        }

        val reviewButton = findViewById<TextView>(R.id.review)
        reviewButton.setOnClickListener {
            navigateToMainActivity12(mentorName, mentorDescription, mentorProfileImageUriString)
        }

        val communityButton = findViewById<TextView>(R.id.community)
        communityButton.setOnClickListener {
            navigateToMainActivity17(mentorName)
        }

        // Load mentor's rating from the database
        loadMentorRating()
    }

    private fun navigateToMainActivity14(mentorName: String?, mentorDescription: String?, mentorProfileImageUriString: String?, mentorSessionPrice: Double) {
        val intent = Intent(this, MainActivity14::class.java).apply {
            putExtra("mentorName", mentorName)
            putExtra("mentorDescription", mentorDescription)
            putExtra("mentorProfileImage", mentorProfileImageUriString)
            putExtra("mentorsessionPrice", mentorSessionPrice)
            putExtra("mentorId", mentorId)
        }
        startActivity(intent)
    }

    private fun navigateToMainActivity12(mentorName: String?, mentorDescription: String?, mentorProfileImageUriString: String?) {
        val intent = Intent(this, MainActivity12::class.java).apply {
            putExtra("mentorName", mentorName)
            putExtra("mentorDescription", mentorDescription)
            putExtra("mentorProfileImage", mentorProfileImageUriString)
            putExtra("mentorId", mentorId)
        }
        startActivity(intent)
    }

    private fun navigateToMainActivity17(mentorName: String?) {
        val intent = Intent(this, MainActivity17::class.java).apply {
            putExtra("mentorName", mentorName)
            putExtra("mentorId", mentorId)
        }
        startActivity(intent)
    }

    private fun loadMentorRating() {
        databaseReference.child("mentors").child(mentorId).child("rating").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mentorRating = dataSnapshot.getValue(Float::class.java)
                findViewById<TextView>(R.id.rating).text = mentorRating?.toString() ?: "0.0"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }
}
