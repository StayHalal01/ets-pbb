package com.example.booksport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.booksport.databinding.FragmentBookingBinding
import com.example.booksport.utils.DateTimeUtils
import com.example.booksport.viewmodels.BookingViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Date

class BookingFragment : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookingViewModel by viewModels()
    private val args: BookingFragmentArgs by navArgs()

    private var selectedDate: Date? = null
    private var selectedTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadVenue(args.venueId)
        setupDatePicker()
        setupTimePicker()
        setupSubmitButton()
        observeViewModel()
    }

    private fun setupDatePicker() {
        binding.datePickerButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                selectedDate = Date(selection)
                binding.datePickerButton.text = DateTimeUtils.formatDate(selectedDate!!)
            }

            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }
    }

    private fun setupTimePicker() {
        binding.timePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .setTitleText("Pilih Waktu")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val hour = timePicker.hour
                val minute = timePicker.minute
                selectedTime = String.format("%02d:%02d", hour, minute)
                binding.timePickerButton.text = selectedTime
            }

            timePicker.show(parentFragmentManager, "TIME_PICKER")
        }
    }

    private fun setupSubmitButton() {
        binding.bookButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            if (selectedDate == null || selectedTime == null) {
                Snackbar.make(
                    binding.root,
                    "Mohon pilih tanggal dan waktu",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val success = viewModel.createBooking(
                userName = name,
                email = email,
                phone = phone,
                date = selectedDate!!,
                time = selectedTime!!
            )

            if (success) {
                findNavController().navigate(
                    BookingFragmentDirections.actionBookingFragmentToBookingConfirmationFragment()
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.venue.observe(viewLifecycleOwner) { venue ->
            binding.venueNameTextView.text = venue.name
            binding.venueAddressTextView.text = venue.address
        }

        viewModel.formErrors.observe(viewLifecycleOwner) { errors ->
            binding.nameLayout.error = errors["name"]
            binding.emailLayout.error = errors["email"]
            binding.phoneLayout.error = errors["phone"]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}