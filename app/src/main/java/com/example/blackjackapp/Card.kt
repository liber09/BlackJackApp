package com.example.blackjackapp

class Card (var value : Value, var suit : Suit){
    lateinit var imageName : String

    /* Returns the suit(ex. heart,spade..) and the value(ex. two,three..)
    Since suit and value are enums I need to use toString on them
    to get the actual value, otherwise I would get a reference only.
    */
    override fun toString() : String{
        return suit.toString().plus("_").plus(value.toString()).lowercase()
    }

    // Returns the value of the cards that it gets from enum list.
    fun getValueOfCard() : Value{
        return this.value
    }
}