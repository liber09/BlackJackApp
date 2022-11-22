package com.example.blackjackapp

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {
    val gameDeck = Deck() // The card deck
    val dealerCards = Deck() // The cards that the dealer has on hand
    val playerCards = Deck() // The cards that the player has on hand
    var playerMoney = 1500
    var dealerMoney = 1500
    var betPlayer = 0
    var betDealer = 0
    var totalBetAmount = 0
    var playerStayed = false
    var playerWon = false
    var dealerWon = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameDeck.createFullDeck() // Creates the initial card deck
        gameDeck.shuffleDeck()  // Shuffles the deck
        addInitialCards() // Gives the player 2 cards and the dealer 2 cards

        //Setup clickHandler for drawButton. This Button clickEvent changes during the game
        val drawButtonClick = findViewById<Button>(R.id.drawButton)
        drawButtonClick.setOnClickListener() {
            setUpGame()
        }
        //Setup clickHandler for stayButton. Reveals dealer cards and finishes up round
        val stayButtonClick = findViewById<Button>(R.id.stayButton)
        stayButtonClick.setOnClickListener {
            revealDealerCards()
        }
        /*
         ButtonClick that doubles the placed bet. Ex. player and dealer has placed 300
         each = 600,click on double makes you bet 1200 more.
         */
        val doubleButtonClick = findViewById<Button>(R.id.doubleButton)
        doubleButtonClick.setOnClickListener {
             doubleBet()
        }

        // The chipButtons that handles betting and calls on function betValue that takes in an int value.
        val betOneButtonClick = findViewById<ImageButton>(R.id.betOneButton)
        betOneButtonClick.setOnClickListener {
             betValue(1)
        }
        val betFiveButtonClick = findViewById<ImageButton>(R.id.betFiveButton)
        betFiveButtonClick.setOnClickListener {
             betValue(5)
        }
        val betTenButtonClick = findViewById<ImageButton>(R.id.betTenButton)
        betTenButtonClick.setOnClickListener {
             betValue(10)
        }
        val betTwentyFiveButtonClick = findViewById<ImageButton>(R.id.betTwentyFiveButton)
        betTwentyFiveButtonClick.setOnClickListener {
             betValue(25)
        }
        val betOneHundredButtonClick = findViewById<ImageButton>(R.id.betOneHundredButton)
        betOneHundredButtonClick.setOnClickListener {
             betValue(100)
        }
    }

    /*
     Handles betting from the player. Takes the value from the chip that the player clicked on
     and adds it to the total betting amount for this round. Checks if player has enough money
     Then does the same for the dealer with the same amount. Finally updates the GUI fields
     with totalBettingAmount and withdraws bet amount from player and dealer money.
     */
    fun betValue(valueOnChip : Int){
        val playerMoneyTextView = findViewById<TextView>(R.id.moneyLeftTextView)
        //val dealerMoneyTextView = findViewById<TextView>(R.id.dealerMoneyLeftTextView)
        val totalBetAmountTextView = findViewById<TextView>(R.id.totalBetAmountTextView)
        if(playerMoney - valueOnChip >= 0){
            betPlayer += valueOnChip
            playerMoney -= valueOnChip
        }else{
            betPlayer = playerMoney // If player doesn't have enough money, bet all player money
        }
        if(dealerMoney - valueOnChip >= 0){
            betDealer += valueOnChip
            dealerMoney -= valueOnChip
        }else{
            betDealer = dealerMoney // If dealer doesn't have enough money, bet all dealer money
        }
        // Adds player and dealer bet to totalBetAmount
        totalBetAmount += (betPlayer + betDealer)

        // Updates the GUI with the new amounts.
        playerMoneyTextView.text = playerMoney.toString()
        //dealerMoneyTextView.text = dealerMoney.toString()
        totalBetAmountTextView.text = totalBetAmount.toString()
    }

    /*
        Doubles the already made bets. Checks if player and dealer has enough money and
        withdraws the double amount from both player and dealer. Updates the GUI with new
        amounts.
     */
    fun doubleBet(){
        val playerMoneyTextView = findViewById<TextView>(R.id.moneyLeftTextView)
        //val dealerMoneyTextView = findViewById<TextView>(R.id.dealerMoneyLeftTextView)
        val totalBetAmountTextView = findViewById<TextView>(R.id.totalBetAmountTextView)
        if(playerMoney - (totalBetAmount*2) >= 0){
            playerMoney -= (totalBetAmount*2)
            playerMoneyTextView.text = playerMoney.toString()
        }else{
            playerMoneyTextView.text = "0"
        }
        if(dealerMoney - (totalBetAmount*2) >= 0){
            dealerMoney -= (totalBetAmount*2)
            //dealerMoneyTextView.text = dealerMoney.toString()
        }
        totalBetAmount += (totalBetAmount*2)
        totalBetAmountTextView.text = totalBetAmount.toString()
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
        playerCards.drawCard(gameDeck)
        dealerCards.drawCard(gameDeck)
        playerCards.drawCard(gameDeck)
        dealerCards.drawCard(gameDeck)
        var playerMoneyTextView = findViewById<TextView>(R.id.moneyLeftTextView)
        playerMoneyTextView.text = playerMoney.toString()

        //Use the suit and value of card to generate filename to be able to dynamically set image to card
        var dealerCard = dealerCards.getCard(1)
        var fileName = dealerCard.toString()
        dealerCard.imageName = fileName
        val dealerSecondCard = findViewById<ImageView>(R.id.dealerSecondCard)
        var uriDealer = "@drawable/".plus(dealerCard.imageName)
        var imageResourceDealer = resources.getIdentifier(uriDealer, null, packageName)
        dealerSecondCard.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResourceDealer))

        val dealerFirstCardPlaceholder = findViewById<ImageView>(R.id.dealerFirstCard)
        uriDealer = "@drawable/".plus("back")
        imageResourceDealer = resources.getIdentifier(uriDealer, null, packageName)
        dealerFirstCardPlaceholder.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResourceDealer))

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
        drawButton.text = getString(R.string.Draw)
        drawButton.setOnClickListener {
            drawPlayerCard()
        }
    }

    // Update the value of both player dealer cards
    fun updateCardsValue(){
        val playerScore = findViewById<TextView>(R.id.playerValueTextView)  // Get a reference to the players score
        val dealerScore = findViewById<TextView>(R.id.dealerValueTextView)
        playerScore.text = playerCards.cardsValue().toString()
        if(!playerStayed){
            dealerScore.text = dealerCards.getCard(1).value.getValue().toString()
        }else{
            dealerScore.text = dealerCards.cardsValue().toString()
        }


        checkIfPlayerLostTheGame()
    }

    //Check if the player lost the round, if so print message to screen
    fun checkIfPlayerLostTheGame(){
        val playerScore = findViewById<TextView>(R.id.playerValueTextView)
        if (playerScore.text.toString().toInt() >21){
            val resultTextView = findViewById<TextView>(R.id.roundResultTextView)
            resultTextView.text = getString(R.string.DealerWonRound)
            resultTextView.visibility = View.VISIBLE
        }
    }

    //Draws the additional player cards to the board
    fun drawPlayerCard() {
        playerCards.drawCard(gameDeck) // Draw a new card from the deck
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
        playerStayed = true
        //Use the suit and value of card to generate filename to be able to dynamically set image to card
        var dealerCard = dealerCards.getCard(0) // get the second card
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
                resultTextView.text = getString(R.string.Congratulations) // Set the text for the resultvire
                resultTextView.visibility = View.VISIBLE // Show the result textview
            }
        }
        checkWinner() //Check who won
        checkGameOver() //Check if player or dealer out of money
        var drawButton = findViewById<Button>(R.id.drawButton) // Get a reference to the drawBtton
        drawButton.text = getString(R.string.NextRound)
        //Set the clickLKistener to reset
        drawButton.setOnClickListener {
            resetForNextRound()
        }
    }

    //Check who won this round
    fun checkWinner(){
        val dealerScore = findViewById<TextView>(R.id.dealerValueTextView).text.toString().toInt()
        val playerScore = findViewById<TextView>(R.id.playerValueTextView).text.toString().toInt()
        val roundResultTextView = findViewById<TextView>(R.id.roundResultTextView)
        //This round was tie
        if (playerScore == dealerScore){
            roundResultTextView.text = getString(R.string.TieRound)
            dealerMoney += (totalBetAmount/2)
            playerMoney += (totalBetAmount/2)
        //Player was fat, dealer wins
        }else if (playerScore > 21){
            roundResultTextView.text = getString(R.string.DealerWonRound)
            dealerWon = true
            dealerMoney += totalBetAmount
        //Dealer was fat, player wins
        }else if (dealerScore > 21){
            roundResultTextView.text = getString(R.string.Congratulations)
            playerWon = true
            playerMoney += totalBetAmount
        //Player has higer and wins
        }else if (playerScore < 22 && playerScore > dealerScore){
            roundResultTextView.text = getString(R.string.Congratulations)
            playerWon = true
            playerMoney += totalBetAmount
        //dealer has higher and wins
        }else{
            roundResultTextView.text = getString(R.string.DealerWonRound)
            dealerWon = true
            dealerMoney += totalBetAmount
        }
        roundResultTextView.visibility = View.VISIBLE

    }
    //Dealer draws a new card
    fun drawDealerCard(){
        dealerCards.drawCard(gameDeck) // Dealer draws the card
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
        dealerCards.moveAllToStartDeck(gameDeck) //Move the cards back to the deck
        playerCards.moveAllToStartDeck(gameDeck) //Move the cards back to the deck
        var playerScore = findViewById<TextView>(R.id.playerValueTextView)
        playerScore.text = "0" //Set player score to 0
        var dealerScore = findViewById<TextView>(R.id.dealerValueTextView)
        playerScore.text = "0" //Set dealer score to 0
        //Change text and function of draw button to start
        var drawButton = findViewById<Button>(R.id.drawButton)
        drawButton.text = getString(R.string.Start)
        drawButton.setOnClickListener {
            setUpGame()
        }
        resetGUIComponents()

    }

    fun nextRound() {

    }
    fun resetGUIComponents(){
        val playerCard1 = findViewById<ImageView>(R.id.playerFirstCardImageView)
        val playerCard2 = findViewById<ImageView>(R.id.playerSecondCardImageView)
        val playerCard3 = findViewById<ImageView>(R.id.playerCardImageView3)
        val playerCard4 = findViewById<ImageView>(R.id.playerCardImageView4)
        val playerCard5 = findViewById<ImageView>(R.id.playerCardImageView5)
        val playerCard6 = findViewById<ImageView>(R.id.playerCardImageView6)
        playerCard1.setImageDrawable(null)
        playerCard2.setImageDrawable(null)
        playerCard3.setImageDrawable(null)
        playerCard4.setImageDrawable(null)
        playerCard5.setImageDrawable(null)
        playerCard6.setImageDrawable(null)
        val DealerCard1 = findViewById<ImageView>(R.id.dealerFirstCard)
        val DealerCard2 = findViewById<ImageView>(R.id.dealerSecondCard)
        val DealerCard3 = findViewById<ImageView>(R.id.newDealerCard3)
        val DealerCard4 = findViewById<ImageView>(R.id.newDealerCard4)
        val DealerCard5 = findViewById<ImageView>(R.id.newDealerCard5)
        val DealerCard6 = findViewById<ImageView>(R.id.newDealerCard6)
        DealerCard1.setImageDrawable(null)
        DealerCard2.setImageDrawable(null)
        DealerCard3.setImageDrawable(null)
        DealerCard4.setImageDrawable(null)
        DealerCard5.setImageDrawable(null)
        DealerCard6.setImageDrawable(null)
        val playerScore = findViewById<TextView>(R.id.playerValueTextView)
        val dealerScore = findViewById<TextView>(R.id.dealerValueTextView)
        playerScore.text = "0"
        dealerScore.text = "0"
        val roundResultTextView = findViewById<TextView>(R.id.roundResultTextView)
        roundResultTextView.visibility = View.INVISIBLE
        val totalBetAmountTextView = findViewById<TextView>(R.id.totalBetAmountTextView)
        totalBetAmountTextView.text = getString(R.string.TotalBet)
    }
    fun checkGameOver(){
        if(playerMoney == 0){
            val lostScreen = Intent(this,LostActivity::class.java) //Get a reference to the game lost screen
            startActivity(lostScreen)
        }else if(dealerMoney == 0){
            val winScreen = Intent(this,WinActivity::class.java) //Get a reference to the game lost screen
            startActivity(winScreen)
        }

    }
}
