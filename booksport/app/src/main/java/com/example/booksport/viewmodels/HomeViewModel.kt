package com.example.booksport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksport.models.Venue
import com.example.booksport.repository.VenueRepository

class HomeViewModel : ViewModel() {
    private val venueRepository = VenueRepository()

    private val _venues = MutableLiveData<List<Venue>>()
    val venues: LiveData<List<Venue>> = _venues

    private val _selectedType = MutableLiveData<String>()
    val selectedType: LiveData<String> = _selectedType

    init {
        loadAllVenues()
    }

    fun loadAllVenues() {
        _venues.value = venueRepository.getAllVenues()
        _selectedType.value = ""
    }

    fun filterVenuesByType(type: String) {
        if (type.isEmpty()) {
            loadAllVenues()
        } else {
            _venues.value = venueRepository.getVenuesByType(type)
            _selectedType.value = type
        }
    }

    fun searchVenues(query: String) {
        if (query.isEmpty()) {
            loadAllVenues()
        } else {
            _venues.value = venueRepository.getAllVenues().filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.address.contains(query, ignoreCase = true)
            }
        }
    }
}