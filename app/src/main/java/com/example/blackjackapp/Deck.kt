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
    // to get a shuffled deck.
    fun shuffleDeck(){
        cards.shuffle()
    }
}
