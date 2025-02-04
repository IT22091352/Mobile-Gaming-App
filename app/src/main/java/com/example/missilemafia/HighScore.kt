package com.example.missilemafia

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HighScore : AppCompatActivity() {

    lateinit var highScoreTextView: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var playAgainBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        playAgainBtn = findViewById(R.id.imageButton)

        highScoreTextView = findViewById(R.id.highScoreTextView)
        sharedPreferences = getSharedPreferences("HighScore", MODE_PRIVATE)

        loadHighScore()

        playAgainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




    private fun loadHighScore() {
        val highScore = sharedPreferences.getInt("HighScore", 0)
        highScoreTextView.text = "High Score: $highScore"
    }
}