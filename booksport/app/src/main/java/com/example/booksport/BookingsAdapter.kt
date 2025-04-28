package com.example.booksport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booksport.models.Booking

class BookingsAdapter(
    private val bookings: List<Booking>,
    private val onItemClick: (Booking) -> Unit
) : RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookingCode: TextView = itemView.findViewById(R.id.bookingCode)
        val venueName: TextView = itemView.findViewById(R.id.venueName)
        val dateTimeText: TextView = itemView.findViewById(R.id.dateTimeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]

        holder.bookingCode.text = booking.bookingCode
        holder.venueName.text = booking.venueName
        holder.dateTimeText.text = "${booking.date} at ${booking.time}"

        holder.itemView.setOnClickListener {
            onItemClick(booking)
        }
    }

    override fun getItemCount(): Int = bookings.size
}