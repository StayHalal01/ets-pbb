package com.example.booksport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.booksport.R
import com.example.booksport.databinding.FragmentVenueDetailBinding
import com.example.booksport.viewmodels.VenueDetailViewModel

class VenueDetailFragment : Fragment() {
    private var _binding: FragmentVenueDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VenueDetailViewModel by viewModels()
    private val args: VenueDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenueDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadVenue(args.venueId)
        observeViewModel()

        binding.bookButton.setOnClickListener {
            findNavController().navigate(
                VenueDetailFragmentDirections.actionVenueDetailFragmentToBookingFragment(args.venueId)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.venue.observe(viewLifecycleOwner) { venue ->
            binding.venueName.text = venue.name
            binding.venueAddress.text = venue.address
            binding.venueType.text = venue.type

            // Load image with Glide
            Glide.with(this)
                .load(venue.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.venueImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}