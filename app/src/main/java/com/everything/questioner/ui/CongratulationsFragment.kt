package com.everything.questioner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everything.questioner.R
import com.everything.questioner.databinding.FragmentCongratulationsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CongratulationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CongratulationsFragment : Fragment() {

    private var _binding: FragmentCongratulationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCongratulationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
    }

    private fun initUI() {

    }


    private fun initListeners() {

    }
}