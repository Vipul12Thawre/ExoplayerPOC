package com.example.exoplayerpoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.commit451.youtubeextractor.YouTubeExtraction
import com.commit451.youtubeextractor.YouTubeExtractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.alexandroid.utils.exoplayerhelper.ExoPlayerHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        extract("z50NGKTvVV4")
    }

    private var extractor: YouTubeExtractor? = null
    private fun extract(videoID: String) {

        extractor = YouTubeExtractor.create()

        extractor?.extract(videoID)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ extraction ->
                bindVideoResult(extraction)
            },
                { t ->
                    // handlerError(t)
                })

    }


    private fun bindVideoResult(youTubeExtraction: YouTubeExtraction?) {

        if (youTubeExtraction != null && youTubeExtraction.videoStreams.isNotEmpty()) {


            setPlayer(youTubeExtraction.videoStreams[0].url)
        }


    }

    private var mExoPlayerHelper: ExoPlayerHelper? = null

    private fun setPlayer(url: String) {


        mExoPlayerHelper = ExoPlayerHelper.Builder(this, player_view)
            .addMuteButton(false, false)
            .setUiControllersVisibility(true)
            .setRepeatModeOn(false)
            .setAutoPlayOn(true)
            .setVideoUrls(url)
            .addProgressBarWithColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorAccent
                )
            )
            .setFullScreenBtnVisible()

            .createAndPrepare()

        mExoPlayerHelper?.playerPlay()

    }
}
