package com.example.booksport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksport.R
import com.example.booksport.adapters.VenueAdapter
import com.example.booksport.databinding.FragmentHomeBinding
import com.example.booksport.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var venueAdapter: VenueAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupFilterChips()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        venueAdapter = VenueAdapter { venue ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToVenueDetailFragment(venue.id)
            )
        }

        binding.venuesRecyclerView.apply {
            adapter = venueAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchVenues(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.loadAllVenues()
                }
                return true
            }
        })
    }

    private fun setupFilterChips() {
        binding.chipGolf.setOnClickListener {
            viewModel.filterVenuesByType("Golf")
        }

        binding.chipBadminton.setOnClickListener {
            viewModel.filterVenuesByType("Badminton")
        }

        binding.chipBasket.setOnClickListener {
            viewModel.filterVenuesByType("Basket")
        }

        binding.chipAll.setOnClickListener {
            viewModel.loadAllVenues()
        }
    }

    private fun observeViewModel() {
        viewModel.venues.observe(viewLifecycleOwner) { venues ->
            venueAdapter.submitList(venues)
        }

        viewModel.selectedType.observe(viewLifecycleOwner) { type ->
            // Update chip selection
            binding.chipGolf.isChecked = type == "Golf"
            binding.chipBadminton.isChecked = type == "Badminton"
            binding.chipBasket.isChecked = type == "Basket"
            binding.chipAll.isChecked = type.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}