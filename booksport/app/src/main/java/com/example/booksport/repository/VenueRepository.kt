package com.example.booksport.repository

import com.example.booksport.models.Venue

class VenueRepository {
    // In a real app, this would come from a database or API
    fun getAllVenues(): List<Venue> {
        return listOf(
            Venue(
                id = "1",
                name = "Golf Graha Famili - Club House",
                type = "Golf",
                address = "Jl. Raya Golf Graha Famili, Pradahkalikendal, Kec. Dukuhpakis, Surabaya, Jawa Timur",
                imageUrl = "golf_graha_famili.jpg"
            ),
            Venue(
                id = "2",
                name = "SuperSmash Hall",
                type = "Badminton",
                address = "Jl. Raya Lontar No.123, Surabaya",
                imageUrl = "supersmash_hall.jpg"
            ),
            Venue(
                id = "3",
                name = "Ciputra Golf, Club & Hotel Surabaya",
                type = "Golf",
                address = "Jl. Citraland Utama, Surabaya",
                imageUrl = "ciputra_golf.jpg"
            ),
            Venue(
                id = "4",
                name = "GOR Glx Badminton",
                type = "Badminton",
                address = "Jl. Raya Lontar No.51",
                imageUrl = "gor_glx.jpg"
            )
        )
    }

    fun getVenuesByType(type: String): List<Venue> {
        return getAllVenues().filter { it.type == type }
    }

    fun getVenueById(id: String): Venue? {
        return getAllVenues().find { it.id == id }
    }
}