package com.Muhaimen.i210888

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.widget.ImageButton
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas

class MainActivity20 : AppCompatActivity() {

    private lateinit var rtcEngine: RtcEngine
    private lateinit var localSurfaceView: SurfaceView
    private lateinit var remoteSurfaceView: SurfaceView
    private var localUid: Int = 0
    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    private fun checkSelfPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    REQUESTED_PERMISSIONS[1]
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = getString(R.string.appId)
            config.mEventHandler = mRtcHandler
            rtcEngine = RtcEngine.create(config)
            rtcEngine.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private val mRtcHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            showMessage("Remote user joined $uid")
            setupRemoteVideo(uid)
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            localUid = uid
            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            showMessage("Remote user offline $uid $reason")
            remoteSurfaceView.visibility = View.GONE
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<FrameLayout>(R.id.callervideo)
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView.setZOrderMediaOverlay(true)
        container.addView(remoteSurfaceView)
        rtcEngine.setupRemoteVideo(VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
        remoteSurfaceView.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
        val container = findViewById<FrameLayout>(R.id.myvideo)
        localSurfaceView = SurfaceView(baseContext)
        container.addView(localSurfaceView)
        rtcEngine.setupLocalVideo(VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))
        rtcEngine.startPreview()
        localSurfaceView.visibility = View.VISIBLE
    }

    private fun joinChannel() {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            setupLocalVideo()
            rtcEngine.joinChannel(getString(R.string.token), getString(R.string.channel), localUid, options)
        } else {
            showMessage("Permission was not granted")
        }
    }

    private fun leaveChannel() {
        rtcEngine.leaveChannel()
        showMessage("You left channel")
        finish()
    }

    private fun toggleCamera() {
        rtcEngine.switchCamera()
    }

    private fun stopCamera() {
        rtcEngine.stopPreview()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main20)

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }

        setupVideoSDKEngine()
        joinChannel()

        val endCallBtn = findViewById<ImageButton>(R.id.close)
        val switchCameraBtn = findViewById<ImageView>(R.id.toggle)
        val stopCameraBtn = findViewById<ImageView>(R.id.videooff)

        endCallBtn.setOnClickListener {
            leaveChannel()
        }

        switchCameraBtn.setOnClickListener {
            toggleCamera()
        }

        stopCameraBtn.setOnClickListener {
            stopCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rtcEngine.stopPreview()
        rtcEngine.leaveChannel()
        RtcEngine.destroy()
    }
}
