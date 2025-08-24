package com.sjm.bankapp.logic

import android.content.Context
import android.media.SoundPool
import com.sjm.bankapp.R

object SoundManager {
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    private val soundIds = mutableMapOf<Int, Int>()

    fun init(context: Context) {
        for (sound in Sounds.entries) {
            soundIds[sound.ordinal] = soundPool.load(context, sound.resource, 1)
        }
    }

    fun play(sound: Sounds) {
        val id = soundIds[sound.ordinal]
        if (id != null) {
            soundPool.play(id, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }

    enum class Sounds(val resource: Int) {
        SUCCESS(R.raw.success),
        ATTENTION(R.raw.attention)
    }
}