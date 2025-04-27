package com.example.booksport

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var confirmationText: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        confirmationText = findViewById(R.id.confirmationText)

        val bookingCode = UUID.randomUUID().toString().substring(0, 8).uppercase()

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val venueName = intent.getStringExtra("venueName")
        val venueAddress = intent.getStringExtra("venueAddress")

        confirmationText.text = """
            Kode Booking: $bookingCode

            Nama: $name
            Email: $email
            No. Telp: $phone
            
            Venue: $venueName
            Alamat: $venueAddress
            Tanggal: $date
            Waktu: $time
        """.trimIndent()
    }
}