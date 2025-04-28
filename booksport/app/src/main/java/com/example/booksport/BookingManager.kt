package com.example.booksport.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.booksport.models.Booking
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BookingManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "BookSport_Prefs"
        private const val KEY_BOOKINGS = "user_bookings"
    }

    fun saveBooking(booking: Booking) {
        val bookings = getAllBookings().toMutableList()

        // Check if booking already exists by code
        val existingIndex = bookings.indexOfFirst { it.bookingCode == booking.bookingCode }

        if (existingIndex != -1) {
            // Update existing booking
            bookings[existingIndex] = booking
        } else {
            // Add new booking
            bookings.add(booking)
        }

        // Save updated list
        val bookingsJson = gson.toJson(bookings)
        prefs.edit().putString(KEY_BOOKINGS, bookingsJson).apply()
    }

    fun getAllBookings(): List<Booking> {
        val bookingsJson = prefs.getString(KEY_BOOKINGS, null) ?: return emptyList()

        // Create TypeToken for List<Booking>
        val bookingListType: Type = object : TypeToken<List<Booking>>() {}.type
        return gson.fromJson(bookingsJson, bookingListType) ?: emptyList()
    }

    fun getBookingByCode(bookingCode: String): Booking? {
        return getAllBookings().find { it.bookingCode == bookingCode }
    }

    fun deleteBooking(bookingCode: String): Boolean {
        val bookings = getAllBookings().toMutableList()
        val removed = bookings.removeIf { it.bookingCode == bookingCode }

        if (removed) {
            val bookingsJson = gson.toJson(bookings)
            prefs.edit().putString(KEY_BOOKINGS, bookingsJson).apply()
        }

        return removed
    }

    fun clearAllBookings() {
        prefs.edit().remove(KEY_BOOKINGS).apply()
    }
}