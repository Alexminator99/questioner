package com.everything.questioner.ui

import android.animation.Animator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.everything.questioner.R
import com.everything.questioner.activities.MainActivity
import com.everything.questioner.data.models.*
import com.everything.questioner.ui.common.QuestionerAdapter
import com.everything.questioner.databinding.FragmentSecondQuestionerBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.*
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondQuestionerFragment : Fragment() {

    private var _binding: FragmentSecondQuestionerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { DataViewModel.Factory }
    )
    private val mutableMapOfAnswers = mutableMapOf<Int, AnswerType>()

    private val args: SecondQuestionerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
        // Create an ArrayAdapter using the string array and a default spinner layout with sex string resource
        val sexAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.sex_options, R.layout.dropdown_menu_popup_item
        )
        // Specify the layout to use when the list of choices
        binding.autoCompleteSex.setAdapter(sexAdapter)

        (activity as MainActivity).supportActionBar?.title = "Cuentános un poco sobre ti..."

        if (args.comesFromFirstQuestioner) {
            binding.tilAge.gone()
            binding.tilGrade.gone()
            binding.tilSex.gone()
            binding.buttonAccept.gone()

            binding.animationView.visibleWithFade()
            binding.animationView.playAnimation()
            binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    animateScreenForQuestioner()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
        } else {
            binding.animationView.gone()
        }
    }

    private fun animateScreenForSoloQuestioner() {
        binding.tilAge.goneWithFade(duration = 500)
        binding.tilGrade.goneWithFade(duration = 500)
        binding.tilSex.goneWithFade(duration = 500)
        binding.buttonAccept.goneWithFade(duration = 500)

        binding.animationView.visibleWithFade()
        binding.animationView.playAnimation()
        closeSoftKeyboard()

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                binding.nestedScrollViewQuestioner.visibleWithFade(duration = 500)
                (activity as MainActivity).supportActionBar?.title = "Cuestionario 2"
                binding.animationView.goneWithFade()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
    }

    private fun animateScreenForQuestioner() {
        binding.animationView.goneWithFade()
        binding.nestedScrollViewQuestioner.visibleWithFade(duration = 500)
        (activity as MainActivity).supportActionBar?.title = "Cuestionario 2"
    }

    private fun initListeners() {
        binding.buttonNext.setOnClickListener {
            if (verifyQuestionerCompleted()) {
                viewModel.setQuestionerTwoAnswers(mutableMapOfAnswers)
                findNavController().navigate(SecondQuestionerFragmentDirections.actionSecondQuestionerFragmentToCongratulationsFragment())
            } else {
                Snackbar.make(requireView(), "Por favor, responde todas las preguntas.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.buttonAccept.setOnClickListener {
            if (checkUIForStudentData()) {
                val studentInformation = StudentInformation(
                    age = binding.etAge.text.toString().toInt(),
                    grade = binding.etGrade.text.toString().toInt(),
                    sex = binding.autoCompleteSex.text.toString(),
                )

                viewModel.setStudentInformation(studentInformation)
                animateScreenForSoloQuestioner()
            }
        }
    }

    private fun checkUIForStudentData(): Boolean {
        binding.etAge.error = if (binding.etAge.text.toString().isEmpty()) "Campo requerido" else null
        binding.etGrade.error = if (binding.etGrade.text.toString().isEmpty()) "Campo requerido" else null
        binding.autoCompleteSex.error =
            if (binding.autoCompleteSex.text.toString().isEmpty()) "Campo requerido" else null

        return binding.etAge.error == null && binding.etGrade.error == null && binding.autoCompleteSex.error == null
    }

    private fun verifyQuestionerCompleted(): Boolean {
        return mutableMapOfAnswers.size == aggressorQuestions.size
    }

    private fun initRecyclerView() {
        val randomQuestioner = mutableListOf<Any>()

        randomQuestioner.addAll(aggressorQuestions)
        randomQuestioner.addAll(interestingFacts.shuffled().subList(0, 2))
        randomQuestioner.addAll(motivationalPhrases.shuffled().subList(0, 2))

        val adapter = QuestionerAdapter(questionList = randomQuestioner.shuffled(), listener = object :
            QuestionerAdapter.OnButtonClickListener {
            override fun onButtonClick(question: Question, answerType: AnswerType) {
                mutableMapOfAnswers[question.order] = answerType
            }
        })
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}