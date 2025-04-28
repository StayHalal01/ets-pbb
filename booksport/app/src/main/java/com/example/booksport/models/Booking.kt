package com.example.booksport.models

data class Booking(
    val bookingCode: String,
    val name: String,
    val email: String,
    val phone: String,
    val date: String,
    val time: String,
    val venueName: String,
    val venueAddress: String,
    val timestamp: Long = System.currentTimeMillis()
)