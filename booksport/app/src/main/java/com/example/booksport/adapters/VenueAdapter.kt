package com.example.booksport.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksport.R
import com.example.booksport.databinding.ItemVenueBinding
import com.example.booksport.models.Venue

class VenueAdapter(
    private val onItemClicked: (Venue) -> Unit
) : ListAdapter<Venue, VenueAdapter.VenueViewHolder>(VenueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val binding = ItemVenueBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VenueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VenueViewHolder(
        private val binding: ItemVenueBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(getItem(position))
                }
            }
        }

        fun bind(venue: Venue) {
            binding.venueName.text = venue.name
            binding.venueAddress.text = venue.address
            binding.venueType.text = venue.type

            // Load image with Glide
            Glide.with(binding.root)
                .load(venue.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.venueImage)

            binding.bookButton.setOnClickListener {
                onItemClicked(venue)
            }
        }
    }

    class VenueDiffCallback : DiffUtil.ItemCallback<Venue>() {
        override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem == newItem
        }
    }
}