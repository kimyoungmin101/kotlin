package com.example.aop_part4_chaptor02

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.example.aop_part4_chaptor02.adapter.PlayListAdapter
import com.example.aop_part4_chaptor02.databinding.FragmentPlayerBinding
import com.example.aop_part4_chaptor02.model.MusicModel
import com.example.aop_part4_chaptor02.model.PlayerModel
import com.example.aop_part4_chaptor02.model.mapper
import com.example.aop_part4_chaptor02.service.MusicDto
import com.example.aop_part4_chaptor02.service.MusicService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private var player: ExoPlayer? = null
    private var binding: FragmentPlayerBinding? = null
    private var model : PlayerModel = PlayerModel()
    private lateinit var playListAdapter: PlayListAdapter

    private val updateSeekRunnable = Runnable{
        updateSeek()
    }
    // Runnable : 어떤 객체도 리턴하지 않는다. Exception 없다.
    // Callable : 특정 타입의 객체를 리턴한다. Exception을 발생시킬 수 있다.

    // Runnable
    // 인자가 없고 리턴 값이 없다.
    // Thread에 인자로 바로 전달 할 수 있다.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        initPlayListButton(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)
        initPlayView(fragmentPlayerBinding)
        initPlayControllButtons(fragmentPlayerBinding)
        initSeekBar(fragmentPlayerBinding)

        getVideoListFromServer()

    }

    override fun onStop() {
        super.onStop()

        player?.pause()
        view?.removeCallbacks(updateSeekRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        player?.release()
        view?.removeCallbacks(updateSeekRunnable)
    }

    private fun initSeekBar(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playerSickBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                player?.seekTo((seekBar.progress) * 1000 .toLong())
            }

        })
        fragmentPlayerBinding.playListSeekBar.setOnTouchListener { view, motionEvent -> false }

    }

    private fun initPlayControllButtons(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playContollImageView.setOnClickListener {
            val player = this.player ?: return@setOnClickListener

            if (player.isPlaying){
                player.pause()
            }
            else{
                player.play()
            }
        }

        fragmentPlayerBinding.skipNextImageView.setOnClickListener {
            val nextMusic = model.nextMusic() ?: return@setOnClickListener
            playMusic(nextMusic)
        }

        fragmentPlayerBinding.skipPrevImageView.setOnClickListener {
            val prevMusic = model.prevMusic() ?: return@setOnClickListener
            playMusic(prevMusic)
        }
    }


    private fun initPlayView(fragmentPlayerBinding: FragmentPlayerBinding) {
        context?.let {
            player = ExoPlayer.Builder(it).build()
        }

        fragmentPlayerBinding.playerView.player = player // PlayerView 즉 Exoplayer2의 view에 player 설정,

        binding?.let { binding ->
            player?.addListener(object: Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    if (isPlaying) {
                        binding.playContollImageView.setImageResource(R.drawable.pause)
                    } else {
                        binding.playContollImageView.setImageResource(R.drawable.play)
                    }
                }

                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)

                    updateSeek()

                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)

                    val newIndex = mediaItem?.mediaId ?: return
                    model.currentPosition = newIndex.toInt()
                    updatePlayerView(model.currentMusicModel())
                    playListAdapter.submitList(model.getAdapterModels())
                }

            })


        }
    }

    private fun updateSeek() {
        val player = this.player?:return
        val duration = if(player.duration >= 0) player.duration else 0
        val position = player.currentPosition

        //todo ui update
        updateSeekUi(duration, position)

        val state = player.playbackState

        view?.removeCallbacks(updateSeekRunnable) // 중복 방지 1초 대기하는 코드를 지우고 다시 !!

        if(state != Player.STATE_IDLE && state != Player.STATE_ENDED){
            view?.postDelayed(updateSeekRunnable, 1000)
        } // 1초에 한번씩 실행되는 무한루프 !!
    }

    private fun updateSeekUi(duration: Long, position: Long) {
        binding?.let { binding ->
            binding.playListSeekBar.max = (duration / 1000).toInt()
            binding.playListSeekBar.progress = (position / 1000).toInt()

            binding.playerSickBar.max = (duration / 1000).toInt()
            binding.playerSickBar.progress = (position / 1000).toInt()

            binding.playTimeTextView.text = String.format("%02d:%02d", // 02는 두자리 수 인데 두자리수가 못미추면 0으로 채운다 !
                TimeUnit.MINUTES.convert(position, TimeUnit.MILLISECONDS),
                (position / 1000) % 60)
            binding.totalTimeTextView.text = String.format("%02d:%02d", // 02는 두자리 수 인데 두자리수가 못미추면 0으로 채운다 !
                TimeUnit.MINUTES.convert(position, TimeUnit.MILLISECONDS),
                (duration / 1000) % 60)
        }
    }

    private fun updatePlayerView(currentMusicModel: MusicModel?) {
        currentMusicModel ?: return

        binding?.let{ binding ->
            binding.trackTextView.text = currentMusicModel.track
            binding.artistTextView.text = currentMusicModel.artist

            Glide.with(binding.coverImageCardView.context)
                .load(currentMusicModel.coverUrl)
                .into(binding.coverImageView)
        }
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        playListAdapter = PlayListAdapter {
            // 음악을 재생
            playMusic(it)
        }

        fragmentPlayerBinding.playListRecyclerView.apply {
            adapter = playListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playlistImageView.setOnClickListener {
            fragmentPlayerBinding.playerViewGroup.isVisible = model.isWatchingPlayListView
            fragmentPlayerBinding.playListViewGroup.isVisible = model.isWatchingPlayListView.not()

            model.isWatchingPlayListView = !model.isWatchingPlayListView
        }
    }

    private fun playMusic(musicModel: MusicModel){
        model.updateCurrentPosition(musicModel)
        player?.seekTo(model.currentPosition, 0)
        player?.play()
    }

    private fun getVideoListFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusics()
                    .enqueue(object : Callback<MusicDto> {
                        override fun onResponse(
                            call: Call<MusicDto>,
                            response: Response<MusicDto>,
                        ) {
                            if (response.isSuccessful.not()) {
                                Log.d("this", "실패")
                                return
                            }
                            response.body()?.let {
                                musicDto ->
                                model = musicDto.mapper()
                                Log.d("this", "${model}")
                                setMusicList(model.getAdapterModels())
                                playListAdapter.submitList(model.getAdapterModels())
                            }
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {
                            Log.d("this", "에러내용 : ${t.toString()}")
                        }
                    })
            }
    }

    private fun setMusicList(modelList: List<MusicModel>) {
        context?.let{
            player?.addMediaItems(modelList.map{MusicModel ->
                MediaItem.Builder()
                    .setMediaId(MusicModel.id.toString())
                    .setUri(MusicModel.streamUrl)
                    .build()
            })

            player?.prepare()
            player?.play()
        }

    }


    companion object {
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}