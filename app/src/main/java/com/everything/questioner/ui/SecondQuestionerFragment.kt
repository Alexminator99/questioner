package com.everything.questioner.ui

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.everything.questioner.R
import com.everything.questioner.data.models.*
import com.everything.questioner.ui.common.QuestionerAdapter
import com.everything.questioner.databinding.FragmentSecondQuestionerBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.AnswerType
import com.everything.questioner.utils.goneWithFade
import com.everything.questioner.utils.visibleWithFade
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondQuestionerFragment : Fragment() {

    private var _binding: FragmentSecondQuestionerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(ownerProducer = { requireActivity() })
    private val mutableMapOfAnswers = mutableMapOf<String, AnswerType>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondQuestionerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
        initRecyclerView()
    }

    private fun initUI() {
        binding.animationView.playAnimation()
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                binding.animationView.goneWithFade()
                binding.nestedScrollViewQuestioner.visibleWithFade()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
    }


    private fun initListeners() {
        binding.buttonNext.setOnClickListener {
            if (verifyQuestionerCompleted()) {
                viewModel.setQuestionerTwoAnswers(mutableMapOfAnswers)
                //findNavController().navigate(R.id.action_FirstQuestionerFragment_to_SecondQuestionerFragment)
            } else {
                Snackbar.make(requireView(), "Por favor, responde todas las preguntas.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyQuestionerCompleted(): Boolean {
        return mutableMapOfAnswers.size == aggressorQuestions.size
    }

    private fun initRecyclerView() {
        val randomQuestioner = mutableListOf<Any>()

        randomQuestioner.addAll(aggressorQuestions)
        randomQuestioner.addAll(interestingFacts.shuffled().subList(0, 2))
        randomQuestioner.addAll(motivationalPhrases.shuffled().subList(0, 2))

        val adapter = QuestionerAdapter(questionList = randomQuestioner, listener = object :
            QuestionerAdapter.OnButtonClickListener {
            override fun onButtonClick(question: String, buttonType: AnswerType) {
                mutableMapOfAnswers[question] = buttonType
            }
        })
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}