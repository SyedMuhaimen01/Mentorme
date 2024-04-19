package com.Muhaimen.i210888

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class bookAdapter(private val bookingList: ArrayList<Map<String, Any>>, private val context: MainActivity24) : RecyclerView.Adapter<bookAdapter.BookingViewHolder>() {

    private lateinit var databaseReference: DatabaseReference
    private val currentUserUID: String? = FirebaseAuth.getInstance().currentUser?.uid

    init {
        // Initialize Firebase database reference
        currentUserUID?.let { uid ->
            databaseReference = FirebaseDatabase.getInstance().reference.child("bookings").child(uid)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.bookedsessioncard, parent, false)
        return BookingViewHolder(view)
    }

    override fun getItemCount(): Int = bookingList.size

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        // Populate UI with booking details
        holder.date.text = booking["date"] as? String
        holder.time.text = booking["time"] as? String
        val mentorId = booking["mentorId"] as? String

        // Fetch mentor details from the database using mentorId
        mentorId?.let { id ->
            val mentorReference = FirebaseDatabase.getInstance().getReference("mentors").child(id)
            mentorReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val mentor = dataSnapshot.getValue(Mentor::class.java)

                    // Populate UI with mentor details if mentor is not null
                    mentor?.let {
                        holder.name.text = it.name
                        holder.title.text = it.title
                        Picasso.get().load(it.imagePath).into(holder.profileImage)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                    Toast.makeText(context, "Failed to retrieve mentor details: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        holder.itemView.setOnLongClickListener {
            val bookingId = booking["id"] as? String
            bookingId?.let { id ->
                deleteBooking(id, position)
            }
            true // Indicate that the click was handled
        }
    }


    // View Holder Class
    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.nameText)
        var title: TextView = itemView.findViewById(R.id.titleText)
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        var date: TextView = itemView.findViewById(R.id.dateText)
        var time: TextView = itemView.findViewById(R.id.timeText)
    }

    // Method to delete the booking from Firebase
    private fun deleteBooking(bookingId: String, position: Int) {
        currentUserUID?.let { uid ->
            val databaseReference = FirebaseDatabase.getInstance().getReference("bookings/$uid")
            databaseReference.child(bookingId).removeValue()
                .addOnSuccessListener {
                    notifyDataSetChanged()
                    Toast.makeText(context, "Booking deleted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to delete booking: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
