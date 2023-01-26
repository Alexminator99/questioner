package com.everything.questioner.ui

//import alice.tuprolog.Prolog
import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.everything.questioner.activities.MainActivity
import com.everything.questioner.ui.common.QuestionerAdapter
import com.everything.questioner.R
import com.everything.questioner.data.DataConstants
import com.everything.questioner.data.models.StudentInformation
import com.everything.questioner.data.models.interestingFacts
import com.everything.questioner.data.models.motivationalPhrases
import com.everything.questioner.data.models.victimQuestions
import com.everything.questioner.databinding.FragmentFirstQuestionerBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.AnswerType
import com.everything.questioner.utils.closeSoftKeyboard
import com.everything.questioner.utils.goneWithFade
import com.everything.questioner.utils.visibleWithFade
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstQuestionerFragment : Fragment() {

    private var _binding: FragmentFirstQuestionerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { DataViewModel.Factory }
    )
    private val mutableMapOfAnswers = mutableMapOf<String, AnswerType>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstQuestionerBinding.inflate(inflater, container, false)
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
    }

    private fun initListeners() {
        binding.buttonNext.setOnClickListener {
            testQuery()
            if (verifyQuestionerCompleted()) {
                viewModel.setQuestionerOneAnswers(mutableMapOfAnswers)
                findNavController().navigate(FirstQuestionerFragmentDirections.actionFirstQuestionerFragmentToSecondQuestionerFragment())
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
                animateScreenForQuestioner()
            }
        }
    }

    private fun animateScreenForQuestioner() {
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
                (activity as MainActivity).supportActionBar?.title = "Cuestionario 1"
                binding.animationView.goneWithFade()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
    }

    private fun checkUIForStudentData(): Boolean {
        binding.etAge.error = if (binding.etAge.text.toString().isEmpty()) "Campo requerido" else null
        binding.etGrade.error = if (binding.etGrade.text.toString().isEmpty()) "Campo requerido" else null
        binding.autoCompleteSex.error =
            if (binding.autoCompleteSex.text.toString().isEmpty()) "Campo requerido" else null

        return binding.etAge.error == null && binding.etGrade.error == null && binding.autoCompleteSex.error == null
    }

    private fun verifyQuestionerCompleted(): Boolean {
        return mutableMapOfAnswers.size == victimQuestions.size
    }

    private fun testQuery() {
        val fileContents = """
             victima('Andres', les_quitan_la_merienda).
             victima('Andres', les_rompen_su_propiedad).
             victima('Andres', casi_siempre_tienen_moretones).
             
             ${DataConstants.programPl}
        """.trimIndent()
        // TODO: FIX ME
        /*val engine = Prolog()
        var info = engine.solve(fileContents)
        while (info.isSuccess) {
            Toast.makeText(context, info.toString(), Toast.LENGTH_LONG).show()
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }*/
    }

    private fun initRecyclerView() {
        val randomQuestioner = mutableListOf<Any>()

        randomQuestioner.addAll(victimQuestions)
        randomQuestioner.addAll(interestingFacts.shuffled().subList(0, 2))
        randomQuestioner.addAll(motivationalPhrases.shuffled().subList(0, 2))

        val adapter = QuestionerAdapter(questionList = randomQuestioner.shuffled(), listener = object :
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