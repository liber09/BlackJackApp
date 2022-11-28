package com.example.blackjackapp

import android.util.Log
import java.util.*

class Deck {
    var cards = mutableListOf<Card>() // holds the  original card deck.

    /*
    This method create a new card for each color and value by iterating through
    each value in suit and value enums.
     */
   fun createFullDeck(){
      Suit.values().forEach { s ->
          Value.values().forEach {v ->
              cards.add(Card(v,s))
          }
      }
   }
    // to get a shuffled deck
    fun shuffleDeck(){
        cards.shuffle()
    }
    //Gets a card at specified index from the deck
    fun getCard(index: Int): Card{
        return cards[index]
    }
    /*
    Add a card to the deck, used when adding card to player and dealer decks
    and when a round is completed and cards should be returned to the original deck
     */
    fun addCard(card:Card){
        cards.add(card)
    }

    //Remove a card from the original deck at position index
    fun removeCard(index:Int){
        cards.removeAt(index)
    }

    //Draw next card from the deck and then remove it from the list of available cards
    fun drawCard(comingFrom: Deck){
        this.cards.add(comingFrom.getCard(0))
        comingFrom.removeCard(0)
    }


    // Function for getting the total value of all cards on hand.
    fun cardsValue():Int{
        var totalValueOnHand = 0
        var aces = 0
        for(card in cards){
            when(card.value){
                Value.TWO -> totalValueOnHand += 2
                Value.THREE -> totalValueOnHand += 3
                Value.FOUR -> totalValueOnHand += 4
                Value.FIVE -> totalValueOnHand += 5
                Value.SIX -> totalValueOnHand += 6
                Value.SEVEN -> totalValueOnHand += 7
                Value.EIGHT -> totalValueOnHand += 8
                Value.NINE -> totalValueOnHand += 9
                Value.TEN,Value.JACK,Value.QUEEN,Value.KING -> totalValueOnHand += 10
                Value.ACE -> aces += 1 // Special solution since ace can be worth 1 or 11.
                else ->{
                    Log.d("!!!","Something gone wrong when reading card value.")
                }
            }
            if(totalValueOnHand <= 10 && aces == 1){
                totalValueOnHand += 11
            }else{
                var totalAceValue = aces * 1 // Multiplies aces on hand with one.
                totalValueOnHand += totalAceValue // Adds value of aces on hand to total hand value.
            }
        }
        return totalValueOnHand
    }

    //Get the int value of a specific card
    fun getCardValue(card: Card):Int{
        var cardValue = 0
        when(card.value){
            Value.TWO -> cardValue = 2
            Value.THREE -> cardValue = 3
            Value.FOUR -> cardValue = 4
            Value.FIVE -> cardValue = 5
            Value.SIX -> cardValue = 6
            Value.SEVEN -> cardValue = 7
            Value.EIGHT -> cardValue = 8
            Value.NINE -> cardValue = 9
            Value.TEN,Value.JACK,Value.QUEEN,Value.KING -> cardValue = 10
            Value.ACE -> cardValue = 11
            else ->{
                Log.d("!!!","Something gone wrong when reading card value.")
            }
        }
        return cardValue
    }

    // Returns the number of cards in deck.
    fun getDeckSize() : Int{
        return this.cards.size
    }

    /*
    Takes all cards in player or dealer list and
    returns them to start deck and then clears dealer or player list
     */
    fun moveAllToStartDeck(startDeck : Deck){
        for(card in this.cards){
            startDeck.addCard(card)
        }
        this.cards.clear()
    }
}
