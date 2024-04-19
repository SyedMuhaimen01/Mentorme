package com.Muhaimen.i210888

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
class MainActivity15 : AppCompatActivity() {
    private lateinit var userAdapter: userAdapter
    private val userList: ArrayList<UserData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        setContentView(R.layout.activity_main15)

        // for immersive mode
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, findViewById(android.R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Set up click listeners for navigation buttons
        findViewById<ImageButton>(R.id.back10).setOnClickListener {
            onBackPressed()
        }

        findViewById<ImageButton>(R.id.home4).setOnClickListener {
            // Handle click to go to home activity
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.search4).setOnClickListener {
            // Handle click to go to search activity
            val intent = Intent(this, MainActivity9::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.add4).setOnClickListener {
            // Handle click to go to add activity
            val intent = Intent(this, MainActivity13::class.java)
            startActivity(intent)
        }

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.userRecyclerView)
        userAdapter = userAdapter(this, userList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        // Populate userList with sample data (replace with your data fetching logic)
        populateUserListFromDatabase()
    }


    private fun populateUserListFromDatabase() {
        val auth = FirebaseAuth.getInstance()
        val currentUserUid = auth.currentUser?.uid

        // Assuming you have a reference to your Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        // Add a listener to fetch user data from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the existing user list
                userList.clear()

                // Iterate through the database snapshot to fetch user data
                for (dataSnapshot in snapshot.children) {
                    // Parse user data and add it to the userList
                    val user = dataSnapshot.getValue(UserData::class.java)
                    user?.let {
                        // Check if the userId is not null and not equal to the current user's UID
                        if (dataSnapshot.key != currentUserUid) {
                            userList.add(it)
                        }
                    }
                }

                // Notify the adapter of the data change
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

}
