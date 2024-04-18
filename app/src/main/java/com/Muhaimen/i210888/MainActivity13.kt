package com.Muhaimen.i210888

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class MainActivity13 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main13)

        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance()

        val items = arrayOf("Available", "Not Available")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner: Spinner = findViewById(R.id.spinner4)
        spinner.adapter = adapter

        val uploadButton = findViewById<ImageView>(R.id.uploadimage) // Corrected: Changed to uploadimage
        uploadButton.setOnClickListener {
            val name = findViewById<TextView>(R.id.nameEditText).text.toString()
            val description = findViewById<TextView>(R.id.descriptionEditText).text.toString()
            val status = spinner.selectedItem.toString()

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please enter name and description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Launch image selection when image view is clicked
            selectImage()
        }


        val backButton = findViewById<ImageButton>(R.id.back8)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val homeButton = findViewById<ImageButton>(R.id.home3)
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity8::class.java))
        }

        val searchButton = findViewById<ImageButton>(R.id.search3)
        searchButton.setOnClickListener {
            startActivity(Intent(this, MainActivity9::class.java))
        }

        val myProfileButton = findViewById<ImageButton>(R.id.myprofile)
        myProfileButton.setOnClickListener {
            startActivity(Intent(this, MainActivity21::class.java))
        }

        val chatButton = findViewById<ImageButton>(R.id.chat)
        chatButton.setOnClickListener {
            startActivity(Intent(this, MainActivity15::class.java))
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uri ->
            uploadImageAndSaveMentor(uri)
        }
    }

    private fun selectImage() {
        getContent.launch("image/*")
    }

    private fun uploadImageAndSaveMentor(imageUri: Uri) {
        // Upload image to Firebase Storage
        val filename = UUID.randomUUID().toString()
        val imageRef = storageReference.reference.child("images/$filename")

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully, get the download URL
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Pass image URL to uploadMentorData function
                uploadMentorData(uri.toString())
            }.addOnFailureListener { exception ->
                // Handle failure to get image download URL
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            // Handle image upload failure
            Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadMentorData(imageUrl: String) {
        // Retrieve mentor data
        val name = findViewById<TextView>(R.id.nameEditText).text.toString()
        val description = findViewById<TextView>(R.id.descriptionEditText).text.toString()
        val status = findViewById<Spinner>(R.id.spinner4).selectedItem.toString()

        // Create Mentor object
        val mentor = Mentor(
            id = UUID.randomUUID().toString(),
            name = name,
            title = "",
            description = description,
            imagePath = imageUrl, // Image URL
            sessionPrice = 0.0,
            availability = status,
            rating = 0.0
        )

        // Get reference to the Firebase database
        val database = FirebaseDatabase.getInstance()
        val mentorsRef = database.getReference("mentors")

        // Push mentor data to Firebase Realtime Database
        mentorsRef.child(mentor.id).setValue(mentor)
            .addOnSuccessListener {
                // Data uploaded successfully, start MainActivity11 with mentor details
                val intent = Intent(this, MainActivity11::class.java).apply {
                    putExtra("mentorName", mentor.name)
                    putExtra("mentorId", mentor.id)
                    putExtra("mentorTitle", mentor.title)
                    putExtra("mentorRating", mentor.rating)
                    putExtra("mentorsessionPrice", mentor.sessionPrice)
                    putExtra("mentorDescription", mentor.description)
                    putExtra("mentorProfileImage", mentor.imagePath)
                }

                // Start MainActivity11 with mentor details
                startActivity(intent)
            }
            .addOnFailureListener { exception ->
                // Handle failure to upload mentor data
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}
