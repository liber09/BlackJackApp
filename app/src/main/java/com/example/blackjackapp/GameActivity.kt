package com.example.blackjackapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GameActivity : AppCompatActivity() {
    val cards = Deck() // The card deck
    val dealerCards = mutableListOf<Card>() // The cards that the dealer has
    val playerCards = mutableListOf<Card>() // The cards that the player has

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        cards.createFullDeck() // Creates the initial card deck
        cards.shuffleDeck()  // Shuffles the deck
    }
    fun addDealerInitialCards(view: View){
        val initialDealerCardFragment = CardFragment() // Creates the fragment with dealers initial cards
        val transaction = supportFragmentManager.beginTransaction() // initiate transaction
        transaction.add(R.id.container,initialDealerCardFragment,"initialDealerCardFragment") // Adds to the transaction where you want your fragment, which fragment and a tag with fragment class name
        transaction.commit() // Commits the transaction to show the fragment on screen
    }
    fun getDealerFirstCards(){
        //dealerCards.add(card)
    }
}