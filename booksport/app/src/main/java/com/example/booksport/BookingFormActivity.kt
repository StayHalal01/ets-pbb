package com.example.booksport

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingFormActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var bookButton: Button
    private lateinit var clearButton: Button
    private lateinit var venueNameText: TextView
    private lateinit var venueAddressText: TextView
    private lateinit var venueImageView: ImageView
    private lateinit var toolbar: Toolbar

    private val calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_form)

        // Initialize views
        initializeViews()

        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Book a Venue"

        // Get venue information from intent
        val venueName = intent.getStringExtra("venueName") ?: ""
        val venueAddress = intent.getStringExtra("venueAddress") ?: ""
        val venueImageResId = intent.getIntExtra("venueImage", R.drawable.golf_image)

        // Set venue information
        venueNameText.text = venueName
        venueAddressText.text = venueAddress
        venueImageView.setImageResource(venueImageResId)

        // Set up date and time pickers
        setupDateTimePickers()

        // Set up buttons
        setupButtons(venueName, venueAddress)
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        dateInput = findViewById(R.id.dateInput)
        timeInput = findViewById(R.id.timeInput)
        bookButton = findViewById(R.id.bookButton)
        clearButton = findViewById(R.id.clearButton)
        venueNameText = findViewById(R.id.venueNameText)
        venueAddressText = findViewById(R.id.venueAddressText)
        venueImageView = findViewById(R.id.venueImageView)
    }

    private fun setupDateTimePickers() {
        // Set current date as minimum date
        val today = Calendar.getInstance()

        // Date picker
        dateInput.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    dateInput.setText(dateFormatter.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Set minimum date to today
            datePickerDialog.datePicker.minDate = today.timeInMillis

            // Set maximum date to 3 months from now
            val maxDate = Calendar.getInstance()
            maxDate.add(Calendar.MONTH, 3)
            datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

            datePickerDialog.show()
        }

        // Time picker
        timeInput.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    timeInput.setText(timeFormatter.format(calendar.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }

    private fun setupButtons(venueName: String, venueAddress: String) {
        // Book button
        bookButton.setOnClickListener {
            if (validateForm()) {
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("name", nameInput.text.toString())
                intent.putExtra("email", emailInput.text.toString())
                intent.putExtra("phone", phoneInput.text.toString())
                intent.putExtra("date", dateInput.text.toString())
                intent.putExtra("time", timeInput.text.toString())
                intent.putExtra("venueName", venueName)
                intent.putExtra("venueAddress", venueAddress)
                startActivity(intent)
            }
        }

        // Clear button
        clearButton.setOnClickListener {
            nameInput.text.clear()
            emailInput.text.clear()
            phoneInput.text.clear()
            dateInput.text.clear()
            timeInput.text.clear()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Check name
        if (nameInput.text.toString().trim().isEmpty()) {
            nameInput.error = "Nama tidak boleh kosong"
            isValid = false
        }

        // Check email
        if (emailInput.text.toString().trim().isEmpty()) {
            emailInput.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString().trim()).matches()) {
            emailInput.error = "Format email tidak valid"
            isValid = false
        }

        // Check phone
        if (phoneInput.text.toString().trim().isEmpty()) {
            phoneInput.error = "Nomor telepon tidak boleh kosong"
            isValid = false
        } else if (phoneInput.text.toString().trim().length < 10) {
            phoneInput.error = "Nomor telepon minimal 10 digit"
            isValid = false
        }

        // Check date
        if (dateInput.text.toString().trim().isEmpty()) {
            dateInput.error = "Tanggal tidak boleh kosong"
            isValid = false
        }

        // Check time
        if (timeInput.text.toString().trim().isEmpty()) {
            timeInput.error = "Waktu tidak boleh kosong"
            isValid = false
        } else {
            // Check if time is within operating hours (8:00 - 22:00)
            val selectedTime = timeInput.text.toString()
            try {
                val time = timeFormatter.parse(selectedTime)
                val timeCalendar = Calendar.getInstance()
                if (time != null) {
                    timeCalendar.time = time

                    val hour = timeCalendar.get(Calendar.HOUR_OF_DAY)

                    if (hour < 8 || hour >= 22) {
                        timeInput.error = "Waktu booking harus antara 08:00 - 22:00"
                        isValid = false
                    }
                }
            } catch (e: Exception) {
                timeInput.error = "Format waktu tidak valid"
                isValid = false
            }
        }

        if (!isValid) {
            Toast.makeText(this, "Mohon lengkapi semua data dengan benar", Toast.LENGTH_SHORT).show()
        }

        return isValid
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}