package com.Muhaimen.i210888

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class searchAdapter(private val mentorList: ArrayList<Mentor>, private val context: MainActivity10): RecyclerView.Adapter<searchAdapter.SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.search_mentor_card, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int = mentorList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val mentor = mentorList[position]

        holder.name.text = mentor.name
        holder.title.text = mentor.title
        holder.sessionPrice.text = "${mentor.sessionPrice}/S"
        holder.availability.text = mentor.availability
        Picasso.get().load(mentor.imagePath).into(holder.profileImg)

        // Single click listener to open Mentor Profile
        holder.profileImg.setOnClickListener {
            val intent = Intent(context, MainActivity11::class.java).apply {
                putExtra("mentorId", mentor.id)
                putExtra("mentorName", mentor.name)
                putExtra("mentorTitle", mentor.title)
                putExtra("mentorDescription", mentor.description)
                putExtra("mentorProfileImage", mentor.imagePath)
                putExtra("mentorsessionPrice", mentor.sessionPrice)
                putExtra("mentorAvailibilty", mentor.availability)
            }
            context.startActivity(intent)
        }

        // Handle click event for the favorite ImageView
        holder.fav.setOnClickListener {
            // Toggle favorite state
            if (holder.fav.tag == "empty") {
                holder.fav.setImageResource(R.mipmap.heart3)
                holder.fav.tag = "filled"
            } else {
                holder.fav.setImageResource(R.mipmap.heart2)
                holder.fav.tag = "empty"
            }
        }
    }

    // View Holder Class
    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var profileImg: ImageView = itemView.findViewById(R.id.profileImg)
        var title: TextView = itemView.findViewById(R.id.title)
        var availability: TextView = itemView.findViewById(R.id.availability)
        var sessionPrice: TextView = itemView.findViewById(R.id.sessionPrice)
        var fav: ImageView = itemView.findViewById(R.id.fav)
    }
}
