package com.example.blackjackapp

class Deck {
    val cards = mutableListOf<Card>()

   fun createFullDeck(){
      Suit.values().forEach { s ->
          Value.values().forEach {v ->
              cards.add(Card(v,s))
          }
      }
   }
}
