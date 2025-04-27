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
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var allVenues: ArrayList<Venue>
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var venuesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        // Find RecyclerViews in layout
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view)
        venuesRecyclerView = findViewById(R.id.recyclerView)

        // Initialize venue data
        initializeData()

        // Setup category filtering
        setupCategoryFiltering()

        // Setup venues RecyclerView
        setupVenuesRecyclerView()
    }

    private fun initializeData() {
        // Initialize all venues
        allVenues = ArrayList()
        // Isi dummy data
        allVenues.add(
            Venue("Golf Graha Famili - Club House", "Jl. Raya Golf Graha Famili", R.drawable.golf_image2, "Golf")
        )
        allVenues.add(
            Venue("Ciputra Golf Club", "Jl. Citra Raya Utama", R.drawable.golf_image, "Golf")
        )
        allVenues.add(
            Venue("SuperSmash Hall", "Taman Telaga Golf TC 3 No.5", R.drawable.badminton_image2, "Badminton")
        )
        allVenues.add(
            Venue("GOR Glx Badminton", "Jl. Raya Lontar No.51", R.drawable.badminton_image, "Badminton")
        )
        allVenues.add(
            Venue("JAWA POS Arena", "Jl. Ahmad Yani No.88", R.drawable.basketball_image, "Basketball")
        )
        allVenues.add(
            Venue("GOR BASKETBALL UNESA", "Jl. Bukit Indah Lontar XI No.82", R.drawable.basketball_image2, "Basketball")
        )

        // Clone the list to our working list
        venueList = ArrayList(allVenues)
    }

    private fun setupCategoryFiltering() {
        // Get unique categories
        val categories = mutableListOf("All")
        categories.addAll(allVenues.map { it.sportType }.distinct())

        categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            filterVenuesByCategory(selectedCategory)
        }

        // Set horizontal layout manager for categories
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecyclerView.adapter = categoryAdapter
    }

    private fun setupVenuesRecyclerView() {
        venueAdapter = VenueAdapter(venueList) { selectedVenue ->
            val intent = Intent(this, BookingFormActivity::class.java)
            intent.putExtra("venueName", selectedVenue.name)
            intent.putExtra("venueAddress", selectedVenue.address)
            startActivity(intent)
        }

        venuesRecyclerView.layoutManager = LinearLayoutManager(this)
        venuesRecyclerView.adapter = venueAdapter
    }

    private fun filterVenuesByCategory(category: String) {
        venueList.clear()

        if (category == "All") {
            venueList.addAll(allVenues)
        } else {
            venueList.addAll(allVenues.filter { it.sportType == category })
        }

        venueAdapter.notifyDataSetChanged()
    }
}