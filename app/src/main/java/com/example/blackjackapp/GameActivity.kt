package com.example.blackjackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GameActivity : AppCompatActivity() {
    val dealerCards = mutableListOf<Card>()
    val cards = Deck()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        cards.createFullDeck()
        cards.shuffleDeck()
    }
    fun addDealerInitialCards(view: View){
        val cardFragment = CardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container,cardFragment,"cardFragment")
        transaction.commit()
    }
    fun getDealerFirstCards(){
        //dealerCards.add(card)
    }
}