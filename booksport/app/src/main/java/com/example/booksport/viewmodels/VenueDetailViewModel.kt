package com.example.booksport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksport.models.Venue
import com.example.booksport.repository.VenueRepository

class VenueDetailViewModel : ViewModel() {
    private val venueRepository = VenueRepository()

    private val _venue = MutableLiveData<Venue>()
    val venue: LiveData<Venue> = _venue

    fun loadVenue(venueId: String) {
        _venue.value = venueRepository.getVenueById(venueId)
    }
}