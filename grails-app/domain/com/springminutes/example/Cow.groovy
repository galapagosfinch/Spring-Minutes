package com.springminutes.example

class Cow {

    Breed breed
    String color
    Long legs

    static constraints = {
        color(blank:false, nullable: false)
        legs(nullable: false, validator: { Long l, Cow c ->
            def maxLegs = (c.breed == Breed.Guernsey ? 4 : 10)
            c.legs < 0 || c.legs > maxLegs ? "invalid.range" : null
        })
    }
}
