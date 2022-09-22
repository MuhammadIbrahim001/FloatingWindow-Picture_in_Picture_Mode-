package com.example.picture_in_picture_mode

import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            TODO("Not yet implemented")
        }

    }

    private val isPipSupported by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            packageManager.hasSystemFeature(
                PackageManager.FEATURE_PICTURE_IN_PICTURE
            )
        } else {
            false
        }
    }
    private var videoViewBounds = Rect()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uri =
            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample_mp4_file)
        val simpleVideoView =
            findViewById<View>(R.id.simpleVideoView) as VideoView // initiate a video view

        simpleVideoView.setVideoURI(uri)
        simpleVideoView.start()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatedPipParams(): PictureInPictureParams? {
        return PictureInPictureParams.Builder()
            .setAspectRatio(Rational(16, 9)).build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!isPipSupported) {
            return
        }
        updatedPipParams()?.let { params ->
            enterPictureInPictureMode(
                params
            )
        }

    }
}