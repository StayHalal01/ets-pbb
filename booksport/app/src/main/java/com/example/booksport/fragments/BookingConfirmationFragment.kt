package com.example.booksport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.booksport.R
import com.example.booksport.databinding.FragmentBookingConfirmationBinding
import com.example.booksport.utils.DateTimeUtils
import com.example.booksport.viewmodels.BookingViewModel

class BookingConfirmationFragment : Fragment() {
    private var _binding: FragmentBookingConfirmationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.doneButton.setOnClickListener {
            findNavController().navigate(
                BookingConfirmationFragmentDirections.actionBookingConfirmationFragmentToHomeFragment()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.booking.observe(viewLifecycleOwner) { booking ->
            binding.venueName.text = booking.venueName
            binding.bookingCode.text = booking.bookingCode
            binding.bookingDate.text = DateTimeUtils.formatDate(booking.date)
            binding.bookingTime.text = booking.time
            binding.userName.text = booking.userName
            binding.userEmail.text = booking.email
            binding.userPhone.text = booking.phone
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}