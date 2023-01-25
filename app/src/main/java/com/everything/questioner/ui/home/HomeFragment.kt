package com.everything.questioner.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.everything.questioner.activities.MainActivity
import com.everything.questioner.databinding.FragmentHomeBinding
import com.everything.questioner.ui.home.adapter.NavigationCardAdapter
import com.everything.questioner.ui.home.adapter.listOfNavigationCards

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide back button
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        initUI()
        initListeners()
    }

    private fun initUI() {
        binding.recyclerView.adapter = NavigationCardAdapter(listOfNavigationCards = listOfNavigationCards,
            onClickAction = {
                findNavController().navigate(it)
            })
    }


    private fun initListeners() {

    }
}