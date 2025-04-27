package com.example.booksport.repository

import com.example.booksport.models.Booking
import com.example.booksport.utils.BookingUtils
import java.util.Date

class BookingRepository {
    private val bookings = mutableListOf<Booking>()

    fun createBooking(
        venueId: String,
        venueName: String,
        userName: String,
        email: String,
        phone: String,
        date: Date,
        time: String
    ): Booking {
        val bookingCode = BookingUtils.generateBookingCode()
        val booking = Booking(
            id = System.currentTimeMillis().toString(),
            venueId = venueId,
            venueName = venueName,
            userName = userName,
            email = email,
            phone = phone,
            date = date,
            time = time,
            bookingCode = bookingCode
        )
        bookings.add(booking)
        return booking
    }

    fun getBookingById(id: String): Booking? {
        return bookings.find { it.id == id }
    }

    fun getBookingByCode(code: String): Booking? {
        return bookings.find { it.bookingCode == code }
    }
}