package com.everything.questioner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.everything.questioner.R
import com.everything.questioner.databinding.FragmentIntroBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.collectFlow
import com.everything.questioner.utils.visibleWithFade

/**
 * A simple [Fragment] subclass.
 * Use the [IntroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { DataViewModel.Factory }
    )
    private var navigateToHome = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initVM()
        initListeners()
    }

    private fun initUI() {
        binding.progressBar.show()
    }

    private fun initVM() {
        collectFlow(viewModel.getProfessionalData()) {
            if (it != null) {
                navigateToHome = true
                binding.progressBar.hide()
                binding.buttonContinue.visibleWithFade(duration = 500)
            } else {
                navigateToHome = false
                binding.progressBar.hide()
                binding.buttonContinue.visibleWithFade(duration = 500)
            }
        }
    }

    private fun initListeners() {
        binding.buttonContinue.setOnClickListener {
            val action =
                if (navigateToHome)
                    IntroFragmentDirections.actionIntroFragmentToHomeFragment()
                else
                    IntroFragmentDirections.actionIntroFragmentToProfessionalDataFragment()
            findNavController().navigate(action)
        }
    }
}