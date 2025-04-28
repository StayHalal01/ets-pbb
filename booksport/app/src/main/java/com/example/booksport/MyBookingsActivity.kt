package com.example.booksport

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksport.models.Booking
import com.example.booksport.utils.BookingManager

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingManager: BookingManager
    private lateinit var noBookingsText: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        // Initialize views
        recyclerView = findViewById(R.id.bookingsRecyclerView)
        noBookingsText = findViewById(R.id.noBookingsText)
        toolbar = findViewById(R.id.toolbar)

        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Bookings"

        // Initialize booking manager
        bookingManager = BookingManager(this)

        // Setup recycler view
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val bookings = bookingManager.getAllBookings()

        // Show no bookings message if list is empty
        if (bookings.isEmpty()) {
            noBookingsText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            noBookingsText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            // Set up adapter
            val adapter = BookingsAdapter(bookings) { booking ->
                // Handle booking item click - show details or options
                showBookingOptions(booking)
            }

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
    }

    private fun showBookingOptions(booking: Booking) {
        // Show dialog with options to view details, share, or cancel booking
        // This is a placeholder for the implementation
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}