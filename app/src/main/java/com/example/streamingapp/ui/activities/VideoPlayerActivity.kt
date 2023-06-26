package com.example.streamingapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    // Declare a global variable for the player
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        // Create a SimpleExoPlayer instance
        player = SimpleExoPlayer.Builder(this).build()


        binding.playerView.player = player
        // Set the media source for the player
        val mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/admin-yogeshwar.appspot.com/o/Files%2Fknee%20pain.mp4?alt=media&token=d2df1a1c-da58-4d42-a1fb-3f18dfcbad9b")
        player?.setMediaItem(mediaItem)

        // Prepare and start the player
        player?.prepare()
        player?.playWhenReady = true
    }

    // Release the player in onDestroy
    override fun onDestroy() {
        super.onDestroy()
        // Release the player resources
        player?.release()
        player = null
    }

}