package com.example.booksport

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksport.models.Venue
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var venueList: ArrayList<Venue>
    private lateinit var venueAdapter: VenueAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var allVenues: ArrayList<Venue>
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var venuesRecyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var myBookingsFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        // Set up toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookSport"

        // Find views in layout
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view)
        venuesRecyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        myBookingsFab = findViewById(R.id.myBookingsFab)

        // Initialize venue data
        initializeData()

        // Setup search functionality
        setupSearch()

        // Setup category filtering
        setupCategoryFiltering()

        // Setup venues RecyclerView
        setupVenuesRecyclerView()

        // Setup My Bookings button
        setupMyBookingsButton()
    }

    private fun initializeData() {
        // Initialize all venues
        allVenues = ArrayList()

        // Add dummy data with more varied venues
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

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterVenues(newText)
                return true
            }
        })
    }

    private fun filterVenues(query: String?) {
        if (query.isNullOrBlank()) {
            // If search is cleared, reapply the current category filter
            val selectedCategory = getSelectedCategory()
            filterVenuesByCategory(selectedCategory)
            return
        }

        val lowercaseQuery = query.lowercase(Locale.getDefault())
        val filteredList = allVenues.filter { venue ->
            venue.name.lowercase(Locale.getDefault()).contains(lowercaseQuery) ||
                    venue.address.lowercase(Locale.getDefault()).contains(lowercaseQuery) ||
                    venue.sportType.lowercase(Locale.getDefault()).contains(lowercaseQuery)
        }

        venueList.clear()
        venueList.addAll(filteredList)
        venueAdapter.notifyDataSetChanged()

        // If no results found, show a message
        if (filteredList.isEmpty()) {
            showNoResultsMessage(true)
        } else {
            showNoResultsMessage(false)
        }
    }

    private fun showNoResultsMessage(show: Boolean) {
        val noResultsView = findViewById<TextView>(R.id.noResultsText)
        noResultsView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getSelectedCategory(): String {
        // This is a helper method to get the currently selected category
        // You would need to modify your CategoryAdapter to expose the selected category
        return (categoryAdapter.getSelectedCategory() ?: "All")
    }

    private fun setupCategoryFiltering() {
        // Get unique categories
        val categories = mutableListOf("All")
        categories.addAll(allVenues.map { it.sportType }.distinct().sorted())

        categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            filterVenuesByCategory(selectedCategory)

            // Clear search when selecting a category
            searchView.setQuery("", false)
            searchView.clearFocus()
        }

        // Set horizontal layout manager for categories
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecyclerView.adapter = categoryAdapter
    }

    private fun setupVenuesRecyclerView() {
        venueAdapter = VenueAdapter(venueList) { selectedVenue ->
            navigateToBookingForm(selectedVenue)
        }

        venuesRecyclerView.layoutManager = LinearLayoutManager(this)
        venuesRecyclerView.adapter = venueAdapter
    }

    private fun navigateToBookingForm(venue: Venue) {
        val intent = Intent(this, BookingFormActivity::class.java)
        intent.putExtra("venueName", venue.name)
        intent.putExtra("venueAddress", venue.address)
        intent.putExtra("venueImage", venue.imageResId)
        intent.putExtra("venueType", venue.sportType)
        startActivity(intent)
    }

    private fun filterVenuesByCategory(category: String) {
        venueList.clear()

        if (category == "All") {
            venueList.addAll(allVenues)
        } else {
            venueList.addAll(allVenues.filter { it.sportType == category })
        }

        venueAdapter.notifyDataSetChanged()

        // Show message if no venues in category
        showNoResultsMessage(venueList.isEmpty())
    }

    private fun setupMyBookingsButton() {
        myBookingsFab.setOnClickListener {
            // Navigate to MyBookingsActivity
            val intent = Intent(this, MyBookingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings action
                true
            }
            R.id.action_about -> {
                // Show about info
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Refresh venue list when returning to this activity
    override fun onResume() {
        super.onResume()
        // This ensures we see any changes if the user made a booking and returns
        val selectedCategory = getSelectedCategory()
        filterVenuesByCategory(selectedCategory)
    }
}