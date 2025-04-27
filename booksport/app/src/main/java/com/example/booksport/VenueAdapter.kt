package com.example.booksport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booksport.models.Venue

class VenueAdapter(
    private val venueList: List<Venue>,
    private val onItemClick: (Venue) -> Unit
) : RecyclerView.Adapter<VenueAdapter.VenueViewHolder>() {

    class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val venueImage: ImageView = itemView.findViewById(R.id.venueImage)
        val venueName: TextView = itemView.findViewById(R.id.venueName)
        val venueAddress: TextView = itemView.findViewById(R.id.venueAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_venue, parent, false)
        return VenueViewHolder(view)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venue = venueList[position]
        holder.venueName.text = venue.name
        holder.venueAddress.text = venue.address
        holder.venueImage.setImageResource(venue.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(venue)
        }
    }

    override fun getItemCount(): Int {
        return venueList.size
    }
}
