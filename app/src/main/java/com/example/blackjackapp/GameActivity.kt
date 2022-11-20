package com.example.blackjackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val cards = Deck()
        cards.createFullDeck()
        cards.shuffleDeck()
    }
    fun addCardFragment(view: View){
        val cardFragment = CardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container,cardFragment,"cardFragment")
        transaction.commit()
    }
}