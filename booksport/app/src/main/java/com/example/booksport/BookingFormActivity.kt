package com.example.booksport

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingFormActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var bookButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_form)

        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        dateInput = findViewById(R.id.dateInput)
        timeInput = findViewById(R.id.timeInput)
        bookButton = findViewById(R.id.bookButton)

        // Get venue information from intent
        val venueName = intent.getStringExtra("venueName") ?: ""
        val venueAddress = intent.getStringExtra("venueAddress") ?: ""

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
        }

        if (!isValid) {
            Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show()
        }

        return isValid
    }
}