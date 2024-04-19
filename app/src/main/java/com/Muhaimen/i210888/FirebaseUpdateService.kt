package com.Muhaimen.i210888

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.database.*

class FirebaseUpdateService : Service() {

    private lateinit var databaseReference: DatabaseReference

    companion object {
        const val CHANNEL_ID = "FirebaseUpdateServiceChannel"
        const val NOTIFICATION_ID = 12345
    }

    override fun onCreate() {
        super.onCreate()
        databaseReference = FirebaseDatabase.getInstance().reference
        createNotificationChannel()
        listenForUpdates("Users")
        listenForUpdates("mentors")
        listenForUpdates("bookings")
        listenForUpdates("chats")
        listenForUpdates("reviews")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Firebase Update Service"
            val descriptionText = "Foreground service to listen for updates in Firebase Realtime Database"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun listenForUpdates(nodeName: String) {
        val nodeReference = databaseReference.child(nodeName)
        nodeReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.childrenCount > 0) {
                    Log.d("FirebaseUpdateService", "Data changed for node: $nodeName")
                    val message = "$nodeName Updated Successfully"
                    sendNotification(message)
                } else {
                    Log.d("FirebaseUpdateService", "No data changes for node: $nodeName")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseUpdateService", "Database error for node: $nodeName", error.toException())
            }
        })
    }

    @SuppressLint("NotificationPermission")
    private fun sendNotification(message: String) {
        val intent = Intent(this, MainActivity25::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Firebase Update")
            .setContentText(message)
            .setSmallIcon(R.mipmap.notification2)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
