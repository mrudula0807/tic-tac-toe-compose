package com.msk.tictactoe

import android.content.Context
import android.media.SoundPool

class SoundManager(context:Context) {

    private val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(5).build()
    private val moveSoundId: Int = soundPool.load(context, R.raw.move, 1)
    private val winSound: Int = soundPool.load(context, R.raw.win, 1)
    private val lostSound: Int = soundPool.load(context, R.raw.lost, 1)
    private val drawSound: Int = soundPool.load(context, R.raw.draw, 1)
    private val resetSound: Int = soundPool.load(context, R.raw.reset, 1)

    fun playMoveSound() {
        soundPool.play(moveSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun playWinSound() {
        soundPool.play(winSound, 1f, 1f, 0, 0, 1f)
    }

    fun playLostSound() {
        soundPool.play(lostSound, 1f, 1f, 0, 0, 1f)
    }

    fun playDrawSound() {
        soundPool.play(drawSound, 1f, 1f, 0, 0, 1f)
    }

    fun playResetSound() {
        soundPool.play(resetSound, 1f, 1f, 0, 0, 1f)
    }

    fun release(){
        soundPool.release()
    }
}