package com.Muhaimen.i210888

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import com.google.firebase.database.FirebaseDatabase

class chatAdapter(private val context: Context, private val chatList: MutableList<Chat>, private val onMessageDoubleTap: (String) -> Unit): RecyclerView.Adapter<chatAdapter.ViewHolder>() {

    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1

    private val IMG_MSG_TYP_LEFT = 2
    private val IMG_MSG_TYP_RIGHT = 3

    private val VID_MSG_TYP_LEFT = 4
    private val VID_MSG_TYP_RIGHT = 5

    private var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            MSG_TYPE_LEFT -> LayoutInflater.from(parent.context).inflate(R.layout.msg_left, parent, false)
            MSG_TYPE_RIGHT -> LayoutInflater.from(parent.context).inflate(R.layout.msg_right, parent, false)

            IMG_MSG_TYP_LEFT -> LayoutInflater.from(parent.context).inflate(R.layout.image_left, parent, false)
            IMG_MSG_TYP_RIGHT -> LayoutInflater.from(parent.context).inflate(R.layout.image_right, parent, false)

            VID_MSG_TYP_LEFT -> LayoutInflater.from(parent.context).inflate(R.layout.video_left, parent, false)
            VID_MSG_TYP_RIGHT -> LayoutInflater.from(parent.context).inflate(R.layout.video_right, parent, false)
            else -> throw IllegalArgumentException("Invalid View Type")
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]

        when(chat.type) {
            "message" -> {
                holder.msg?.text = chat.message

                holder.itemView.setOnLongClickListener {
                    val position = holder.adapterPosition
                    val itemToDelete = chatList[position]

                    chatList[position].message = "This message has been deleted"

                    val databaseReference = FirebaseDatabase.getInstance().getReference("chats")
                    databaseReference.child(itemToDelete.chatId).setValue(chatList[position])
                        .addOnSuccessListener {
                            Toast.makeText(context, "Message deleted", Toast.LENGTH_SHORT).show()
                        }
                    true
                }

                holder.setupDoubleTapGesture(context, chat.chatId) {
                    if (position < chatList.size) {
                        val itemToDelete = chatList[position]
                        val message = itemToDelete.message ?: ""
                        onMessageDoubleTap.invoke(message)
                    }
                }
            }
            "image" -> {
                Picasso.get().load(chat.message).into(holder.image)
            }
            "video" -> {
                holder.videoPlayer?.apply {
                    setVideoURI(Uri.parse(chat.message))
                    setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.setVolume(0f, 0f)
                        mediaPlayer.isLooping = true
                        holder.videoPlayer.start()
                    }
                    setOnErrorListener { mediaPlayer, i, i2 ->
                        Toast.makeText(context, "Error in playing video", Toast.LENGTH_SHORT).show()
                        true
                    }
                }
            }
        }

        holder.time?.text = chat.time
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val msg: TextView? = view.findViewById(R.id.textView)
        val chatView: TextView? = view.findViewById(R.id.textView)
        val time: TextView? = view.findViewById(R.id.time)
        val image: ImageView? = view.findViewById(R.id.imageView)
        val videoPlayer: VideoView? = view.findViewById(R.id.VideoView)

        init {
            videoPlayer?.setOnClickListener {
                if (videoPlayer.isPlaying) {
                    videoPlayer.pause()
                } else {
                    videoPlayer.start()
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        fun setupDoubleTapGesture(context: Context, chatId: String, onDoubleTap: () -> Unit) {
            val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    onDoubleTap()

                    val messageRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId)
                    messageRef.child("Editable").setValue("yes")
                    return true
                }
            })

            chatView?.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val isImage = chatList[position].type

        return if (chatList[position].senderId == firebaseUser!!.uid) {
            if (isImage == "image") IMG_MSG_TYP_RIGHT else if(isImage == "video") VID_MSG_TYP_RIGHT else MSG_TYPE_RIGHT
        } else {
            if (isImage == "image") IMG_MSG_TYP_LEFT else if(isImage == "video") VID_MSG_TYP_LEFT else MSG_TYPE_LEFT
        }
    }
}
