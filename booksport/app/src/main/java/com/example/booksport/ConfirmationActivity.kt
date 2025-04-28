package com.example.booksport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.booksport.models.Booking
import com.example.booksport.utils.BookingManager
import java.util.UUID

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var confirmationCode: TextView
    private lateinit var nameValue: TextView
    private lateinit var emailValue: TextView
    private lateinit var phoneValue: TextView
    private lateinit var venueNameValue: TextView
    private lateinit var addressValue: TextView
    private lateinit var dateValue: TextView
    private lateinit var timeValue: TextView
    private lateinit var shareButton: Button
    private lateinit var homeButton: Button
    private lateinit var saveButton: Button
    private lateinit var confirmationCard: CardView

    private lateinit var bookingCode: String
    private lateinit var bookingManager: BookingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        // Initialize booking manager
        bookingManager = BookingManager(this)

        // Initialize views
        initViews()

        // Generate booking code
        bookingCode = generateBookingCode()

        // Retrieve booking info from intent
        val name = intent.getStringExtra("name") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val venueName = intent.getStringExtra("venueName") ?: ""
        val venueAddress = intent.getStringExtra("venueAddress") ?: ""

        // Display values
        confirmationCode.text = bookingCode
        nameValue.text = name
        emailValue.text = email
        phoneValue.text = phone
        venueNameValue.text = venueName
        addressValue.text = venueAddress
        dateValue.text = date
        timeValue.text = time

        // Set up button click listeners
        setupButtonListeners(name, email, phone, date, time, venueName, venueAddress)
    }

    private fun initViews() {
        confirmationCode = findViewById(R.id.confirmationCode)
        nameValue = findViewById(R.id.nameValue)
        emailValue = findViewById(R.id.emailValue)
        phoneValue = findViewById(R.id.phoneValue)
        venueNameValue = findViewById(R.id.venueNameValue)
        addressValue = findViewById(R.id.addressValue)
        dateValue = findViewById(R.id.dateValue)
        timeValue = findViewById(R.id.timeValue)
        shareButton = findViewById(R.id.shareButton)
        homeButton = findViewById(R.id.homeButton)
        saveButton = findViewById(R.id.saveButton)
        confirmationCard = findViewById(R.id.confirmationCard)
    }

    private fun generateBookingCode(): String {
        return UUID.randomUUID().toString().substring(0, 8).uppercase()
    }

    private fun setupButtonListeners(
        name: String,
        email: String,
        phone: String,
        date: String,
        time: String,
        venueName: String,
        venueAddress: String
    ) {
        // Share booking details
        shareButton.setOnClickListener {
            shareBookingDetails(name, email, phone, date, time, venueName, venueAddress)
        }

        // Save booking to local storage
        saveButton.setOnClickListener {
            val booking = Booking(
                bookingCode = bookingCode,
                name = name,
                email = email,
                phone = phone,
                date = date,
                time = time,
                venueName = venueName,
                venueAddress = venueAddress
            )

            bookingManager.saveBooking(booking)
            Toast.makeText(this, "Booking saved successfully!", Toast.LENGTH_SHORT).show()
        }

        // Return to home screen
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun shareBookingDetails(
        name: String,
        email: String,
        phone: String,
        date: String,
        time: String,
        venueName: String,
        venueAddress: String
    ) {
        val shareText = """
            Booking details from BookSport:
            
            Booking Code: $bookingCode
            Name: $name
            Date: $date
            Time: $time
            
            Venue: $venueName
            Address: $venueAddress
            
            Please arrive 15 minutes before your booking time.
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share booking details via")
        startActivity(shareIntent)
    }
}