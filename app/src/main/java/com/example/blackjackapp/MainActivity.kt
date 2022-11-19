package com.example.blackjackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cards = Deck()
        cards.createFullDeck()
        cards.shuffleDeck()
        cards.cards.forEach(){
            Log.d("!!!",it.toString())
        }
    }
}