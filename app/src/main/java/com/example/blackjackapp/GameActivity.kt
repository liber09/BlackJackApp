package com.example.blackjackapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {
    val cards = Deck() // The card deck
    val dealerCards = Deck() // The cards that the dealer has on hand
    val playerCards = Deck() // The cards that the player has on hand
    var playerMoney = 0
    var dealerMoney = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        cards.createFullDeck() // Creates the initial card deck
        cards.shuffleDeck()  // Shuffles the deck

        val drawButtonClick = findViewById<Button>(R.id.drawButton)
        drawButtonClick.setOnClickListener{
            setUpGame()
        }
    }

    /*
    Add fragment to view by creating fragment and then initiate a transaction,
    add the fragment(s) that are supposed to be visible on screen to the transaction
    and finally commit the transaction to show the fragments on screen.
     */
    fun addInitialCards(view: View){
        val initialDealerCardFragment = DealerCardFragment() // Creates the fragment with dealers initial cards
        val initialPlayerCardFragment = PlayerCardFragment() //Creates the fragment with players initial cards
        val transaction = supportFragmentManager.beginTransaction() // initiate transaction
        transaction.add(R.id.dealerCardContainer,initialDealerCardFragment,"initialDealerCardFragment") // Adds to the transaction where you want your fragment, which fragment and a tag with fragment class name
        transaction.add(R.id.playerCardContainer,initialPlayerCardFragment,"initialPlayerCardFragment") // Adds to the transaction where you want your fragment, which fragment and a tag with fragment class name
        transaction.commit() // Commits the transaction to show the fragment on screen
    }

    // Gives both players their first two cards and their initial money
    fun setUpGame(){
        playerCards.drawCard(cards)
        dealerCards.drawCard(cards)
        playerCards.drawCard(cards)
        dealerCards.drawCard(cards)
        playerMoney = 1500
        dealerMoney = 1500

        // Use the suit and value of card to generate filename to be able to dynamically set image to card
        var dealerCard = dealerCards.getCard(1)
        var fileName = dealerCard.toString()
        dealerCard.imageName = fileName
        val dealerSecondCard = findViewById<ImageView>(R.id.dealerSecondCard)
        val uriDealer = "@drawable/".plus(dealerCard.imageName)
        val imageResourceDealer = resources.getIdentifier(uriDealer, null, packageName)
        dealerSecondCard.setImageBitmap(BitmapFactory.decodeResource(getResources(),imageResourceDealer))

        var playerCardOne = playerCards.getCard(0)
        fileName = playerCardOne.toString()
        playerCardOne.imageName = fileName
        val playerFirstCardPlaceholder = findViewById<ImageView>(R.id.playerFirstCardImageView)
        val uri1Player = "@drawable/".plus(playerCardOne.imageName)
        val imageResource1Player = resources.getIdentifier(uri1Player, null, packageName)
        playerFirstCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(),imageResource1Player))

        var playerCardTwo = playerCards.getCard(1)
        fileName = playerCardTwo.toString()
        playerCardTwo.imageName = fileName
        val playerSecondCardPlaceholder = findViewById<ImageView>(R.id.playerSecondCardImageView)
        val uri2Player = "@drawable/".plus(playerCardTwo.imageName)
        val imageResource2Player = resources.getIdentifier(uri2Player, null, packageName)
        playerSecondCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(),imageResource2Player))




    }
}