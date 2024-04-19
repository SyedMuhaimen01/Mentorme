package com.Muhaimen.i210888

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class userAdapter(private val context: Context, private val userList: ArrayList<UserData>): RecyclerView.Adapter<userAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.textUsername.text = user.name

        // Load user image if available
        user.profilePicture?.let { imagePath ->
            if (imagePath.isNotBlank()) {
                Picasso.get().load(imagePath).into(holder.userImage)
            } else {

            }
        } ?: run {

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MainActivity16::class.java)
            intent.putExtra("name", user.name)
            intent.putExtra("imagePath", user.profilePicture)
            context.startActivity(intent)
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textUsername: TextView = view.findViewById(R.id.nameTextView)
        val userImage: CircleImageView = view.findViewById(R.id.circleImageView)
    }
}
