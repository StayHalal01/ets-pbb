package com.example.booksport.utils

import java.util.Random

object BookingUtils {
    private val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private const val CODE_LENGTH = 12

    fun generateBookingCode(): String {
        val random = Random()
        return (1..CODE_LENGTH)
            .map { CHARS[random.nextInt(CHARS.length)] }
            .joinToString("")
    }
}
