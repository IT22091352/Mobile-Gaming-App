package com.example.missilemafia

interface GameTask {
    fun closeGame(mScore: Int, highScore: Int)
    fun updateHighScore(newHighScore: Int)
}
