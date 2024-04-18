package com.Muhaimen.i210888

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity10 : AppCompatActivity() {
    private lateinit var resultMentorList: ArrayList<Mentor>
    private lateinit var resultMentorAdapter: searchAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main10)

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().reference.child("mentors")

        setupSpinner()
        setupRecyclerView()
        setupButtonListeners()

        // Retrieve and set mentor data
        retrieveAndSetMentorData()
    }

    private fun setupSpinner() {
        val items = arrayOf("Filter", "aaa", "bbbb")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner: Spinner = findViewById(R.id.spinner3)
        spinner.adapter = adapter
    }

    private fun setupRecyclerView() {
        resultMentorList = ArrayList()
        resultMentorAdapter = searchAdapter(resultMentorList, this)
        val recyclerView: RecyclerView = findViewById(R.id.searchResult)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = resultMentorAdapter
    }

    private fun setupButtonListeners() {
        findViewById<ImageButton>(R.id.back5).setOnClickListener {
            onBackPressed()
        }

        findViewById<ImageButton>(R.id.home2).setOnClickListener {
            startActivity(Intent(this, MainActivity8::class.java))
        }

        findViewById<ImageButton>(R.id.add3).setOnClickListener {
            startActivity(Intent(this, MainActivity13::class.java))
        }

        findViewById<ImageButton>(R.id.myprofile).setOnClickListener {
            startActivity(Intent(this, MainActivity21::class.java))
        }

        findViewById<ImageButton>(R.id.chat).setOnClickListener {
            startActivity(Intent(this, MainActivity15::class.java))
        }

        findViewById<ImageButton>(R.id.search).setOnClickListener {
            startActivity(Intent(this, MainActivity9::class.java))
        }
    }

    private fun retrieveAndSetMentorData() {
        val mentorName = intent.getStringExtra("mentorName")

        if (!mentorName.isNullOrEmpty()) {
            // Query Firebase for mentor data by name
            databaseReference.orderByChild("name").equalTo(mentorName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val mentor = snapshot.getValue(Mentor::class.java)
                            if (mentor != null) {
                                // Clear the list and add the mentor
                                resultMentorList.clear()
                                resultMentorList.add(mentor)
                                // Notify the adapter of the data change
                                resultMentorAdapter.notifyDataSetChanged()
                                break
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                    }
                })
        }
    }
}
