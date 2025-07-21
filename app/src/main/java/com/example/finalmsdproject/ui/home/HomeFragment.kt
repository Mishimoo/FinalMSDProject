package com.example.finalmsdproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalmsdproject.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController
import com.example.finalmsdproject.R


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Update title and description
        binding.homeTitle.text = "D2L"
        binding.courseDescription.text = "Desire 2 Learn"

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}