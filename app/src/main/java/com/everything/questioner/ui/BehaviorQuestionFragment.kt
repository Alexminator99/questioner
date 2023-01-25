package com.everything.questioner.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.everything.questioner.R
import com.everything.questioner.databinding.FragmentBehaviorQuestionBinding
import com.everything.questioner.utils.goneWithFade
import com.everything.questioner.utils.visibleWithFade
import com.google.android.material.card.MaterialCardView

/**
 * A simple [Fragment] subclass.
 * Use the [BehaviorQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BehaviorQuestionFragment : Fragment() {

    private var _binding: FragmentBehaviorQuestionBinding? = null
    private val binding get() = _binding!!

    private var initialBackgroundColor: ColorStateList = ColorStateList.valueOf(Color.WHITE)
    private var initialStrokeColor: ColorStateList = ColorStateList.valueOf(Color.GRAY)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBehaviorQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
    }

    private fun initUI() {
        binding.questionCardYes.setOnClickListener {
            resetCard(binding.questionCardNo)
            setCardYesEnabled()
            binding.tilArguments.goneWithFade(duration = 500)
        }

        binding.questionCardNo.setOnClickListener {
            resetCard(binding.questionCardYes)
            setCardNoEnabled()
            binding.tilArguments.visibleWithFade(duration = 500)
        }

        binding.buttonContinue.setOnClickListener {
            findNavController().navigate(BehaviorQuestionFragmentDirections.actionBehaviorQuestionFragmentToFirstQuestionerFragment())
        }

        initCardViewDefaultValues()
    }

    private fun initCardViewDefaultValues() {
        initialBackgroundColor = binding.questionCardYes.cardBackgroundColor
        initialStrokeColor =
            binding.questionCardYes.strokeColorStateList ?: ColorStateList.valueOf(
                binding.questionCardYes
                    .strokeColorStateList?.defaultColor ?: Color.GRAY
            )
    }

    private fun setCardYesEnabled() {
        with(binding.questionCardYes) {
            cardElevation = 0f
            setCardBackgroundColor(Color.argb(60, 0, 255, 0))
            strokeColor = Color.GREEN
        }
    }

    private fun setCardNoEnabled() {
        with(binding.questionCardNo) {
            cardElevation = 0f
            setCardBackgroundColor(Color.argb(60, 255, 0, 0))
            strokeColor = Color.RED
        }
    }

    private fun resetCard(card: MaterialCardView) {
        with(card) {
            cardElevation = 4f
            setCardBackgroundColor(initialBackgroundColor)
            strokeColor = initialStrokeColor.defaultColor
        }
    }

    private fun initListeners() {

    }
}