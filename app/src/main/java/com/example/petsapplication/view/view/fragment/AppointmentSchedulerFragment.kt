package com.example.petsapplication.view.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petsapplication.R
import com.example.petsapplication.databinding.FragmentAppointmentSchedulerBinding
import com.example.petsapplication.view.model.InventoryAppointment
import com.example.petsapplication.view.view.adapter.ListAppointmentAdapter
import com.example.petsapplication.view.viewmodel.AppointmentModel

class AppointmentSchedulerFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentSchedulerBinding
    private val appointmentModel: AppointmentModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentSchedulerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.addNewAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentSchedulerFragment_to_addNewAppointmentFragment)
        }
    }

    private fun observeViewModel() {
        appointmentModel.listInventory.observe(viewLifecycleOwner) { listInventory ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ListAppointmentAdapter(listInventory) { item ->
                navigateToAppointmentDetails(item)
            }
            recycler.adapter = adapter
        }

        appointmentModel.progresState.observe(viewLifecycleOwner) { status ->
            binding.progress.isVisible = status
        }
    }


    private fun navigateToAppointmentDetails(inventoryAppointment: InventoryAppointment) {
        val bundle = Bundle().apply {
            putString("petName", inventoryAppointment.pet_name)
            putString("petBreed", inventoryAppointment.pet_breed)
            putString("petDetails", inventoryAppointment.pel_symptoms)
            putString("owner", inventoryAppointment.owner_name)
            putString("phone", inventoryAppointment.tel_numbe)
            putString("idNumber", inventoryAppointment.id.toString())
            putString("imageUrl",   inventoryAppointment.imageUrl)
        }
        findNavController().navigate(R.id.action_appointmentSchedulerFragment_to_appointmentDetailsFragment, bundle)
    }
}