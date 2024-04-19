package com.Muhaimen.i210888

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*

class MainActivity24 : AppCompatActivity() {
    private lateinit var bookedSessionList: ArrayList<Map<String, Any>>
    private lateinit var bookSessionAdapter: bookAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val TAG = "MainActivity24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main24)

        auth = FirebaseAuth.getInstance()
        setupRecyclerView()
        setupButtonListeners()

        // Initialize Firebase and retrieve bookings
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            // Create a query to retrieve bookings for the current user
            val query = FirebaseDatabase.getInstance().getReference("bookings")
                .orderByChild("userId").equalTo(uid)

            // Get the DatabaseReference object from the query
            databaseReference = query.ref

            // Retrieve and set booked sessions
            retrieveAndSetBookedSessions()
        }


    }

    private fun setupRecyclerView() {
        bookedSessionList = ArrayList()
        bookSessionAdapter = bookAdapter(bookedSessionList, this)
        val recyclerView: RecyclerView = findViewById(R.id.bookedSessionRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookSessionAdapter
    }

    private fun setupButtonListeners() {
        findViewById<ImageButton>(R.id.back14).setOnClickListener {
            onBackPressed()
        }
    }

    private fun retrieveAndSetBookedSessions() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear existing list
                bookedSessionList.clear()

                // Populate bookedSessionList with bookings for the current user
                for (bookingSnapshot in dataSnapshot.children) {
                    val bookingMap = bookingSnapshot.value as? Map<String, Any>
                    bookingMap?.let {
                        bookedSessionList.add(it)
                    }
                }

                // Notify adapter that data set has changed
                bookSessionAdapter.notifyDataSetChanged()

                // Log the number of booked sessions retrieved
                Log.d(TAG, "Number of booked sessions retrieved: ${bookedSessionList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e(TAG, "Error retrieving booked sessions: ${error.message}")
            }
        })
    }
}
