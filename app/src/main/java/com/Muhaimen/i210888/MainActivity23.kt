package com.Muhaimen.i210888

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.squareup.picasso.Picasso
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import java.util.concurrent.TimeUnit



class MainActivity23 : AppCompatActivity() {

    private val APP_ID = "YOUR_AGORA_APP_ID"
    private val CHANNEL = "AudioCallChannel"
    private val TOKEN = "YOUR_TEMP_TOKEN"

    private var mRtcEngine: RtcEngine? = null
    private var localUid: Int = 0
    private var remoteUids = HashSet<Int>()

    private var secondsElapsed = 0
    private val handler = Handler(Looper.getMainLooper())
    private val updateTask = object : Runnable {
        override fun run() {
            val minutes = TimeUnit.SECONDS.toMinutes(secondsElapsed.toLong())
            val seconds = secondsElapsed % 60
            val timeString = String.format("%02d:%02d", minutes, seconds)

            // Update the time display
            // You need to replace `timeTextView` with your actual TextView
            timeTextView.text = timeString

            secondsElapsed++
            handler.postDelayed(this, 1000)
        }
    }

    private lateinit var timeTextView: TextView
    private lateinit var endCallButton: ImageButton

    // Define mRtcEventHandler
    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.d("AudioCallActivity", "Remote user joined: $uid")
            // Handle the event when a remote user joins the channel
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            Log.d("AudioCallActivity", "Joined channel: $channel, uid: $uid")
            // Handle the event when the local user successfully joins the channel
            localUid = uid
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d("AudioCallActivity", "Remote user offline: $uid, reason: $reason")
            remoteUids.remove(uid)
        }

        override fun onError(err: Int) {
            Log.e("MainActivity23", "Error code: $err")
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Set layout to immersive mode
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val rootView = findViewById<View>(android.R.id.content)
        WindowInsetsControllerCompat(window, rootView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContentView(R.layout.activity_main23)

        // Initialize views
        timeTextView = findViewById<TextView>(R.id.timeTextView)
        endCallButton = findViewById<ImageButton>(R.id.close)

        // Set click listener for end call button
        endCallButton.setOnClickListener {
            finish()
        }

        // Start the timer
        handler.postDelayed(updateTask, 1000)

        // Request microphone permission
        if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initializeAndJoinChannel()
        } else {
            requestMicrophonePermission()
        }
    }

    // Function to initialize and join channel
    private fun initializeAndJoinChannel() {
        try {
            mRtcEngine = RtcEngine.create(baseContext, APP_ID, mRtcEventHandler)
        } catch (e: Exception) {
            Log.e("AudioCallActivity", "Failed to create RtcEngine: ${e.message}")
        }

        // Configure channel options
        val options = ChannelMediaOptions()
        options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER

        // Join the channel
        mRtcEngine?.joinChannel(TOKEN, CHANNEL, localUid, options)
        Log.d("AudioCallActivity", "Call started")
    }

    // Function to handle microphone permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQ_ID_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeAndJoinChannel()
                } else {
                    Toast.makeText(this, "Microphone permission is needed for audio calls", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    // Function to request microphone permission
    private fun requestMicrophonePermission() {
        checkSelfPermission(android.Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)
    }

    // Function to check if microphone permission is granted
    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }

    override fun onStop() {
        super.onStop()
        // Release resources when activity is stopped
        mRtcEngine?.leaveChannel()
        RtcEngine.destroy()
        handler.removeCallbacks(updateTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTask)
    }

    companion object {
        const val PERMISSION_REQ_ID_RECORD_AUDIO = 1
    }
}
