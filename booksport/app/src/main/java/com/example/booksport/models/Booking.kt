package com.example.booksport.models

import java.util.Date

data class Booking(
    val id: String,
    val venueId: String,
    val venueName: String,
    val userName: String,
    val email: String,
    val phone: String,
    val date: Date,
    val time: String,
    val bookingCode: String
)