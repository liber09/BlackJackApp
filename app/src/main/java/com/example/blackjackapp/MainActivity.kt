package com.example.blackjackapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var gameView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.playButton)
        buttonClick.setOnClickListener{
            val gameScreen = Intent(this,GameActivity::class.java) //Get a reference to the game activity screen
            startActivity(gameScreen)
        }
    }
}