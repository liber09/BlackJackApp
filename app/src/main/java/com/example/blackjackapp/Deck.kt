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
    //Get a card at specified index from the pile of cards
    fun getCard(i: Int): Card{
        return cards[i]
    }
    //add a card to the deck
    fun addCard(card:Card){
        cards.add(card)
    }

    //Remove a card from the deck at position i
    private fun removeCard(i:Int){
        cards.removeAt(i)
    }

    //Draw nex card from the deck and then remove it from the list of available cards
    fun drawCard(comingFrom: Deck){
        this.cards.add(comingFrom.getCard(0))
        comingFrom.removeCard(0)
    }
}
