package com.springminutes.example

class Cow {

    Breed breed
    String color
    Long legs

    static constraints = {
        color(blank:false, nullable: false)
        legs(range: 0..10)
    }
}
