package com.Muhaimen.i210888

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

data class Chat(
    val chatId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    var message: String = "",
    val time: String = "",
    val type: String = "",
    val editable: String = ""
) {
    // Add a no-argument constructor
    constructor() : this("", "", "", "", "", "", "")
}

class MainActivity16 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: chatAdapter
    private val chatList = ArrayList<Chat>()
    private var firebaseUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var receiverId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main16)

        recyclerView = findViewById(R.id.userRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = chatAdapter(this, chatList) { chatId ->
            val intent = Intent(this, MainActivity17::class.java)
            intent.putExtra("chatId", chatId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("chats")

        val backButton: ImageButton = findViewById(R.id.back11)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val sendButton: ImageButton = findViewById(R.id.send)
        sendButton.setOnClickListener {
            sendMessage()
        }

        val galleryButton: ImageButton = findViewById(R.id.gallery)
        galleryButton.setOnClickListener {
            openGallery()
        }

        val name = intent.getStringExtra("name")
        val nameTextView = findViewById<TextView>(R.id.name)
        nameTextView.text = name

        if (name != null) {
            findUserIdByName(name)
        } else {
            Toast.makeText(this, "Name is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun findUserIdByName(userName: String) {
        val usersRef = FirebaseDatabase.getInstance().getReference("Users")

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val name = userSnapshot.child("name").getValue(String::class.java)
                    if (name == userName) {
                        receiverId = userSnapshot.key // Get the user ID
                        receiverId?.let {
                            readMessage(firebaseUser?.uid ?: "", it)
                            return // Exit the loop once the user is found
                        }
                    }
                }
                // Handle the case where user with the given name is not found
                Toast.makeText(applicationContext, "User with name $userName not found", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

    private fun readMessage(senderId: String, receiverId: String) {
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)
                    if ((chat?.senderId == senderId && chat.receiverId == receiverId) ||
                        (chat?.senderId == receiverId && chat.receiverId == senderId)) {
                        chat?.let { chatList.add(it) }
                    }
                }
                adapter.notifyDataSetChanged()
                if (chatList.isNotEmpty()) {
                    recyclerView.scrollToPosition(chatList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

    private fun sendMessage() {
        val messageField = findViewById<EditText>(R.id.msgFeild)
        val message = messageField.text.toString().trim()
        if (message.isNotEmpty()) {
            val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val chatId = reference?.push()?.key ?: ""
            val senderId = firebaseUser?.uid ?: ""
            val receiverId = receiverId ?: ""
            val chat = Chat(chatId, senderId, receiverId, message, currentTime, "message", "false")
            reference?.child(chatId)?.setValue(chat)
            messageField.text.clear()
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                sendImageMessage(it)
            }
        }
    }

    private fun sendImageMessage(imageUri: Uri) {
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val chatId = reference?.push()?.key ?: ""
        val senderId = firebaseUser?.uid ?: ""
        val receiverId = receiverId ?: ""
        val chat = Chat(chatId, senderId, receiverId, imageUri.toString(), currentTime, "image", "false")
        reference?.child(chatId)?.setValue(chat)
    }

    companion object {
        private const val REQUEST_GALLERY = 100
    }
}
