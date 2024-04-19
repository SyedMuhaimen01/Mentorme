package com.Muhaimen.i210888

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

data class Chat(
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    var message: String,
    val time: String,
    val type: String,
    val Editable: String
)
class MainActivity16 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: chatAdapter
    private val chatList = ArrayList<Chat>() // Assuming Chat is your model class
    private var firebaseUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.userRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = chatAdapter(this, chatList) { /* Handle double tap logic here */ }
        recyclerView.adapter = adapter

        // Initialize Firebase references
        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("chats")

        // Get userId from intent
        userId = intent.getStringExtra("userId")

        // Load messages
        readMessage(firebaseUser!!.uid, userId!!)
    }

    private fun readMessage(senderId: String, receiverId: String) {
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)
                    if ((chat!!.senderId == senderId && chat.receiverId == receiverId) ||
                        (chat.senderId == receiverId && chat.receiverId == senderId)) {
                        chatList.add(chat)
                    }
                }
                // Notify adapter of changes
                adapter.notifyDataSetChanged()

                // Scroll to the bottom of the chat list after the data is loaded
                if (chatList.isNotEmpty()) {
                    recyclerView.scrollToPosition(chatList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}
