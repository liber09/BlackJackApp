package com.example.blackjackapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.util.*


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
        addInitialCards()

        //Setup clickhandler for drawbutton. This Button clickEvent changes during the game
        val drawButtonClick = findViewById<Button>(R.id.drawButton)
        drawButtonClick.setOnClickListener() {
            setUpGame()
        }
        //Setup clickHandler for stayButton. Reveals dealer cards and finishes up round
        val stayButtonClick = findViewById<Button>(R.id.stayButton)
        stayButtonClick.setOnClickListener {
            revealDealerCards()
        }
    }


    /*
    Add fragment to view by creating fragment and then initiate a transaction,
    add the fragment(s) that are supposed to be visible on screen to the transaction
    and finally commit the transaction to show the fragments on screen.
     */
    fun addInitialCards() {
        val initialDealerCardFragment = DealerCardFragment() // Creates the fragment with dealers initial cards
        val initialPlayerCardFragment = PlayerCardFragment() // Creates the fragment with players initial cards
        val newPlayerCardFragment = NewPlayerCardFragment() // Creates the fragment with players additional cards
        val newDealerCadFragment = NewDealerCardFragment() // Creates the fragment with dealers additional cards
        val transaction = supportFragmentManager.beginTransaction() // initiate transaction
        transaction.add(R.id.dealerCardContainer, initialDealerCardFragment, "initialDealerCardFragment") // Adds to the transaction where you want your fragment, which fragment and a tag with fragment class name
        transaction.add(R.id.playerCardContainer, initialPlayerCardFragment, "initialPlayerCardFragment") // Adds to the transaction where you want your fragment, which fragment and a tag with fragment class name
        transaction.add(R.id.playerCardContainer, newPlayerCardFragment,"newPlayerCardFragment")
        transaction.add(R.id.dealerCardContainer, newDealerCadFragment, "newDealerCardFragment")
        transaction.commitNow() // Commits the transaction to show the fragment on screen
    }

    // Gives both players their first two cards and their initial money
    fun setUpGame() {
        playerCards.drawCard(cards)
        dealerCards.drawCard(cards)
        playerCards.drawCard(cards)
        dealerCards.drawCard(cards)
        playerMoney = 1500
        dealerMoney = 1500
        var playerMoneyTextView = findViewById<TextView>(R.id.moneyLeftTextView)
        playerMoneyTextView.text = playerMoney.toString()

        //Use the suit and value of card to generate filename to be able to dynamically set image to card
        var dealerCard = dealerCards.getCard(1)
        var fileName = dealerCard.toString()
        dealerCard.imageName = fileName
        val dealerSecondCard = findViewById<ImageView>(R.id.dealerSecondCard)
        Log.d("!!!", dealerSecondCard.id.toString())
        val uriDealer = "@drawable/".plus(dealerCard.imageName)
        val imageResourceDealer = resources.getIdentifier(uriDealer, null, packageName)
        dealerSecondCard.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResourceDealer))

        var playerCardOne = playerCards.getCard(0)
        fileName = playerCardOne.toString()
        playerCardOne.imageName = fileName
        val playerFirstCardPlaceholder = findViewById<ImageView>(R.id.playerFirstCardImageView)

        val uri1Player = "@drawable/".plus(playerCardOne.imageName)
        val imageResource1Player = resources.getIdentifier(uri1Player, null, packageName)
        playerFirstCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource1Player))

        var playerCardTwo = playerCards.getCard(1)
        fileName = playerCardTwo.toString()
        playerCardTwo.imageName = fileName
        val playerSecondCardPlaceholder = findViewById<ImageView>(R.id.playerSecondCardImageView)
        val uri2Player = "@drawable/".plus(playerCardTwo.imageName)
        val imageResource2Player = resources.getIdentifier(uri2Player, null, packageName)
        playerSecondCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource2Player))

        updateCardsValue()

        val drawButton = findViewById<Button>(R.id.drawButton)
        drawButton.text = "Draw"
        drawButton.setOnClickListener {
            drawPlayerCard()
        }
    }
    // Update the value of both player dealer cards
    fun updateCardsValue(){
        val playerScore = findViewById<TextView>(R.id.playerValueTextView)  // Get a reference to the players score
        val dealerScore = findViewById<TextView>(R.id.dealerValueTextView)
        playerScore.text = playerCards.cardsValue().toString()
        dealerScore.text = dealerCards.cardsValue().toString()
        checkIfPlayerLost()
    }

    //Check if the player lost the round, if so print message to screen
    fun checkIfPlayerLost(){
        val playerScore = findViewById<TextView>(R.id.playerValueTextView)
        if (playerScore.text.toString().toInt() >21){
            val resultTextView = findViewById<TextView>(R.id.roundResultTextView)
            resultTextView.text = "Dealer won this round!"
            resultTextView.visibility = View.VISIBLE
        }
    }
    //Draws the additional player cards to the board
    fun drawPlayerCard() {
        playerCards.drawCard(cards) // Draw a ned card from the deck
        var playerCard = playerCards.getCard(playerCards.cards.size - 1) // Get the latest card from the players stack of cards
        val fileName = playerCard.toString() // Get the filename
        playerCard.imageName = fileName // Save the filename to the playerCard object
        var playerCardPlaceholder = findViewById<ImageView>(R.id.playerCardImageView3) // Get the placeholder for where on the screen it should be drawn
        if (playerCards.cards.size == 4) { //if it is the second additional card
            playerCardPlaceholder = findViewById<ImageView>(R.id.playerCardImageView4) // Get the placeholder for where on the screen it should be drawn
        } else if (playerCards.cards.size == 5) { //if it is the third additional card
            playerCardPlaceholder = findViewById<ImageView>(R.id.playerCardImageView5) // Get the placeholder for where on the screen it should be drawn
        } else { //if it is the sixth addidional card
            playerCardPlaceholder = findViewById<ImageView>(R.id.playerCardImageView6) // Get the placeholder for where on the screen it should be drawn
        }
        playerCardPlaceholder.visibility = View.VISIBLE // Make the card visible
        val uriPlayer = "@drawable/".plus(playerCard.imageName) // get the path to where in the project the file is saved
        val imageResource = resources.getIdentifier(uriPlayer, null, packageName) // Get the actual image
        playerCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource)) // Set the image to the placeholder
        updateCardsValue() // Update the value of cards on the gamingboard
    }

    // This function shows the dealers initial hidden card
    fun revealDealerCards(){
        //Use the suit and value of card to generate filename to be able to dynamically set image to card
        var dealerCard = dealerCards.getCard(1) // get the second card
        var fileName = dealerCard.toString() // Get the filename
        dealerCard.imageName = fileName // Save the filename to the card object
        val dealerFirstCard = findViewById<ImageView>(R.id.dealerFirstCard) //
        Log.d("!!!", dealerFirstCard.id.toString())
        val uriDealer = "@drawable/".plus(dealerCard.imageName)
        val imageResourceDealer = resources.getIdentifier(uriDealer, null, packageName)
        dealerFirstCard.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResourceDealer))
        updateCardsValue()
        var dealerHandValueTextView = findViewById<TextView>(R.id.dealerValueTextView)
        var dealerHandValue = Integer.parseInt(dealerHandValueTextView.text.toString())
        while (dealerHandValue < 17){ // check if the dealer has above 17, if nut run loop
            drawDealerCard() //Draw a new card to the dealer
            dealerHandValue = dealerHandValueTextView.text.toString().toInt() // Get the value of dealer cards as int
            if(dealerHandValue > 21){ // if dealers value on hand is above 21, dealer looses
                var resultTextView = findViewById<TextView>(R.id.roundResultTextView) // Get the result textview
                resultTextView.text = "Congratulations you won this round!" // Set the text for the resultvire
                resultTextView.visibility = View.VISIBLE // Show the result textview
            }
        }
        var drawButton = findViewById<Button>(R.id.drawButton) // Get a reference to the drawBtton
        drawButton.text = "New cards"
        //Set the clicklistener to reset
        drawButton.setOnClickListener {
            resetForNextRound()
        }
    }
    //Dealer draws a new card
    fun drawDealerCard(){
        dealerCards.drawCard(cards) // Dealer draws the card
        // Get the card and apply image to it
        var dealerCard = dealerCards.getCard(dealerCards.cards.size - 1)
        val fileName = dealerCard.toString()
        dealerCard.imageName = fileName
        var dealerCardPlaceholder = findViewById<ImageView>(R.id.newDealerCard3) // First extra card
        if (dealerCards.cards.size == 4) {
            dealerCardPlaceholder = findViewById<ImageView>(R.id.newDealerCard4) //Second extra card
        } else if (playerCards.cards.size == 5) {
            dealerCardPlaceholder = findViewById<ImageView>(R.id.newDealerCard5) //Third extra card
        } else {
            dealerCardPlaceholder = findViewById<ImageView>(R.id.newDealerCard6) //Fourth extra card
        }
        dealerCardPlaceholder.visibility = View.VISIBLE // Make the card visible on screen
        val uriDealer = "@drawable/".plus(dealerCard.imageName)
        val imageResource = resources.getIdentifier(uriDealer, null, packageName)
        dealerCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource))
        updateCardsValue() //Update the score
    }

    //Reset the gameround and prepare for next round
    fun resetForNextRound(){
        dealerCards.moveAllToStartDeck(cards) //Move the cards back to the deck
        playerCards.moveAllToStartDeck(cards) //Move the cards back to the deck
        var playerScore = findViewById<TextView>(R.id.playerValueTextView)
        playerScore.text = "0" //Set player score to 0
        var dealerScore = findViewById<TextView>(R.id.dealerValueTextView)
        playerScore.text = "0" //Set dealer score to 0
        //Change text and function of draw button to start
        var drawButton = findViewById<Button>(R.id.drawButton)
        drawButton.text = "Start"
        drawButton.setOnClickListener {
            setUpGame()
        }
    }
}