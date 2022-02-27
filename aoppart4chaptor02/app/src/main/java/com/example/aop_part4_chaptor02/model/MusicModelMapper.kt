package com.example.aop_part4_chaptor02.model

import com.example.aop_part4_chaptor02.service.MusicDto

fun MusicDto.mapper(): PlayerModel =
    PlayerModel(
        playMusicList = musics.mapIndexed { index, musicEntity ->
            MusicModel(
                id = index.toLong(),
                streamUrl = musicEntity.streamUrl,
                coverUrl = musicEntity.coverUrl,
                track = musicEntity.track,
                artist = musicEntity.artist,
            )
        }
    )