package com.example.booksport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksport.models.Booking
import com.example.booksport.models.Venue
import com.example.booksport.repository.BookingRepository
import com.example.booksport.repository.VenueRepository
import com.example.booksport.utils.FormValidator
import java.util.Date

class BookingViewModel : ViewModel() {
    private val bookingRepository = BookingRepository()
    private val venueRepository = VenueRepository()

    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    private val _booking = MutableLiveData<Booking>()
    val booking: LiveData<Booking> = _booking

    private val _formErrors = MutableLiveData<Map<String, String>>()
    val formErrors: LiveData<Map<String, String>> = _formErrors

    fun loadVenue(venueId: String) {
        _venue.value = venueRepository.getVenueById(venueId)
    }

    fun createBooking(
        userName: String,
        email: String,
        phone: String,
        date: Date,
        time: String
    ): Boolean {
        val errors = validateForm(userName, email, phone)
        if (errors.isNotEmpty()) {
            _formErrors.value = errors
            return false
        }

        val venue = _venue.value ?: return false

        val newBooking = bookingRepository.createBooking(
            venueId = venue.id,
            venueName = venue.name,
            userName = userName,
            email = email,
            phone = phone,
            date = date,
            time = time
        )

        _booking.value = newBooking
        return true
    }

    private fun validateForm(
        userName: String,
        email: String,
        phone: String
    ): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (!FormValidator.isValidName(userName)) {
            errors["name"] = "Nama harus diisi"
        }

        if (!FormValidator.isValidEmail(email)) {
            errors["email"] = "Format email tidak valid"
        }

        if (!FormValidator.isValidPhone(phone)) {
            errors["phone"] = "Format nomor telepon tidak valid"
        }

        return errors
    }
}