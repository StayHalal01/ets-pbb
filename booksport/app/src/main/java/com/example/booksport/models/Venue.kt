package com.example.booksport.models

data class Venue(
    val id: String,
    val name: String,
    val type: String, // Golf, Badminton, Basket
    val address: String,
    val imageUrl: String,
    val description: String = ""
)