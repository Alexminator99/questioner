package com.everything.questioner.ui

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.everything.questioner.activities.MainActivity
import com.everything.questioner.data.models.ReportData
import com.everything.questioner.databinding.FragmentCongratulationsBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.visibleWithAnimationAfterDelay
import com.everything.questioner.utils.visibleWithFade
import java.io.File
import java.io.FileOutputStream

/**
 * A simple [Fragment] subclass.
 * Use the [CongratulationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CongratulationsFragment : Fragment() {

    private var _binding: FragmentCongratulationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(
        ownerProducer = ({ requireActivity() }),
        factoryProducer = { DataViewModel.Factory }
    )

    private val args: CongratulationsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCongratulationsBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
    }

    private fun initUI() {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "¡Felicidades!"

        binding.buttonContinue.visibleWithAnimationAfterDelay(delay = 10000)
    }


    private fun initListeners() {
        binding.buttonContinue.setOnClickListener {
            viewModel.processData()
            findNavController().navigate(CongratulationsFragmentDirections.actionCongratulationsFragmentToReportFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}