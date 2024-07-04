package com.example.missilemafia

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var mypaint: Paint? = null
    var speed = 1
    private var time = 0
    var score = 0
    private var highScore: Int
    private var myBoyPosition = 0
    val otherMissile = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0

    private val sharedPreferences: SharedPreferences =
        c.getSharedPreferences("HighScore", Context.MODE_PRIVATE)

    init {
        mypaint = Paint()
        highScore = sharedPreferences.getInt("HighScore", 0)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherMissile.add(map)
        }
        time = time + 10 + speed
        val objWidth = viewWidth / 5
        val objHeight = objWidth + 10
        mypaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.boy, null)

        d.setBounds(
            myBoyPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - objHeight,
            myBoyPosition * viewWidth / 3 + viewWidth / 15 + objWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)

        mypaint!!.color = Color.GREEN


        for (i in otherMissile.indices) {
            try {
                val boyX = otherMissile[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var missileY = time - otherMissile[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.missile, null)

                d2.setBounds(
                    boyX + 25, missileY - objHeight, boyX + objWidth - 25, missileY
                )
                d2.draw(canvas)

                if (otherMissile[i]["lane"] as Int == myBoyPosition) {
                    if (missileY > viewHeight - 2 - objHeight && missileY < viewHeight - 2) {
                        gameTask.closeGame(score, highScore)
                    }
                }

                if (missileY > viewHeight + objHeight) {
                    otherMissile.removeAt(i)
                    score++
                    updateHighScore()
                    speed = 1 + Math.abs(score / 8)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mypaint!!.color = Color.WHITE
        mypaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, mypaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, mypaint!!)
        canvas.drawText("High Score : $highScore", 80f, 140f, mypaint!!)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myBoyPosition > 0) {
                        myBoyPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myBoyPosition < 2) {
                        myBoyPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    fun updateHighScore() {
        if (score > highScore) {
            highScore = score
        }
    }
}
