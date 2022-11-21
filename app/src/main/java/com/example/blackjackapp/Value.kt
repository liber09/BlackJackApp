package com.example.blackjackapp

public enum class Value(i: Int) {
    TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),TEN(10),JACK(10),QUEEN(10),KING(10),ACE(1);

    private var value = 0
    fun Value(value: Int) {
        this.value = value
    }
    fun getValue(): Int {
        return value
    }
}