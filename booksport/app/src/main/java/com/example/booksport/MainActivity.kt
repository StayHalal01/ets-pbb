package com.example.booksport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksport.models.Venue

class MainActivity : AppCompatActivity() {

    private lateinit var venueList: ArrayList<Venue>
    private lateinit var venueAdapter: VenueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        venueList = ArrayList()
        // Isi dummy data
        venueList.add(
            Venue("Golf Graha Famili - Club House", "Jl. Raya Golf Graha Famili", R.drawable.golf_image2, "Golf")
        )
        venueList.add(
            Venue("Ciputra Golf Club", "Jl. Citra Raya Utama", R.drawable.golf_image, "Golf")
        )
        venueList.add(
            Venue("SuperSmash Hall", "Taman Telaga Golf TC 3 No.5", R.drawable.badminton_image2, "Badminton")
        )
        venueList.add(
            Venue("GOR Glx Badminton", "Jl. Raya Lontar No.51", R.drawable.badminton_image, "Badminton")
        )
        venueList.add(
            Venue("JAWA POS Arena", "Jl. Ahmad Yani No.88", R.drawable.basketball_image, "Basketball")
        )
        venueList.add(
            Venue("GOR BASKETBALL UNESA", "Jl. Bukit Indah Lontar XI No.82", R.drawable.basketball_image2, "Basketball")
        )

        venueAdapter = VenueAdapter(venueList) { selectedVenue ->
            val intent = Intent(this, BookingFormActivity::class.java)
            intent.putExtra("venueName", selectedVenue.name)
            intent.putExtra("venueAddress", selectedVenue.address)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = venueAdapter
    }
}
