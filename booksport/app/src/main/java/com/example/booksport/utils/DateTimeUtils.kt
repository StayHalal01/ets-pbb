package com.example.booksport.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }
}