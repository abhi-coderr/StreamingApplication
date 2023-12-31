package com.example.streamingapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityVideoPlayerBinding
import com.example.streamingapp.utils.setSafeOnClickListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.lang.Exception

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding

    // Declare a global variable for the player
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)

        val url = intent.getStringExtra("videoUrl")
        val category = intent.getStringExtra("videoCategory")
        val detail = intent.getStringExtra("videoDetail")

        Log.i("VideoPlayerCategoryAndDetail", "$category and $detail")

        if (detail != null) {
            setUpBinding(category.toString(), url.toString(), detail)
        }
        setVideoPlayer(url.toString())
    }

    // Release the player in onDestroy
    override fun onDestroy() {
        super.onDestroy()
        // Release the player resources
        player?.release()
        player = null
    }

    private fun setUpBinding(
        category: String,
        url: String,
        detail: String
    ) = binding.apply {
        shareBtnVideoGallery.setSafeOnClickListener {
            shareVideo(this@VideoPlayerActivity, url.toString(), detail.toString())
        }
        categoryLabel.text = category
        videoDetail.text = detail
    }

    private fun setVideoPlayer(url: String) {

        try {
            // Create a SimpleExoPlayer instance
            player = SimpleExoPlayer.Builder(this).build()


            binding.playerView.player = player
            // Set the media source for the player
            val mediaItem =
                MediaItem.fromUri(url.toString())
            player?.setMediaItem(mediaItem)

            // Prepare and start the player
            player?.prepare()
            player?.playWhenReady = true

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun shareVideo(context: Context, videoUrl: String, videoTitle: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this amazing experience testimony: $videoTitle\n\n$videoUrl"
        )
        context.startActivity(Intent.createChooser(shareIntent, "Share Video"))
    }

    override fun onPause() {
        super.onPause()
        binding.playerView.player = player
        player?.pause()
    }

}