package com.example.booksport.utils

import android.util.Patterns

object FormValidator {
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= 3
    }

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.isNotBlank() && phone.length >= 10 && phone.all { it.isDigit() }
    }
}