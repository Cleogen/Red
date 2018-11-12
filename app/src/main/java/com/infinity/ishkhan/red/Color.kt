package com.infinity.ishkhan.red

class Color (hex:String,val color: Int) {

    val name:String

    init {
        name = "#$hex"
    }
}