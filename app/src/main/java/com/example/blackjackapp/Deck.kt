package com.example.blackjackapp

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
    //add a card to the deck
    fun addCard(card:Card){
        cards.add(card)
    }

    //Remove a card from the deck at position i
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
                TWO -> totalValueOnHand += 2
                THREE -> totalValueOnHand += 3
                FOUR -> totalValueOnHand += 4
                FIVE -> totalValueOnHand += 5
                SIX -> totalValueOnHand += 6
                SEVEN -> totalValueOnHand += 7
                EIGHT -> totalValueOnHand += 8
                NINE -> totalValueOnHand += 9
                TEN,JACK,QUEEN,KING -> totalValueOnHand += 10
                ACE -> aces += 1 // Special solution since ace can be worth 1 or 11.
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
