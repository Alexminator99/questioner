package com.everything.questioner.ui

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.everything.questioner.R
import com.everything.questioner.activities.MainActivity
import com.everything.questioner.databinding.FragmentReportBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.AnswerType
import com.everything.questioner.utils.visibleWithFade
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by viewModels(
        ownerProducer = { requireActivity() },
        factoryProducer = { DataViewModel.Factory }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initVM()
        initListeners()
    }

    private fun initUI() {
        (activity as MainActivity).supportActionBar?.title = "Reporte"
    }

    private fun initListeners() {
        binding.buttonBackToHome.setOnClickListener {
            viewModel.clearQuestionerData()
            findNavController().navigate(ReportFragmentDirections.actionReportFragmentToHomeFragment())
        }
    }

    private fun initVM() {
        val reportBuilder = StringBuilder()
        val report = viewModel.getReportData()

        reportBuilder.append("Reporte de Cuestionario").append("\n").append("\n")

        report?.let { it ->
            it.apply {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("es", "ES"))
                val timeFormat = SimpleDateFormat("hh:mm aa", Locale("es", "ES"))
                val date = dateFormat.format(Calendar.getInstance().time)
                val time = timeFormat.format(Calendar.getInstance().time)

                if (!questionerOneAnswers.isNullOrEmpty()) {
                    val questionerOneName = "Cuestionario Víctima"
                    val information = "$questionerOneName • $date • $time"
                    binding.textViewInformationQuestionerOne.text = information

                    reportBuilder.append(information).append("\n")

                    var isVictim = false

                    questionerOneAnswers.mapKeys {
                        if (it.key == 6 || it.key == 7 || it.key == 9 || it.key == 12 || it.key == 13 || it.key == 14) {
                            if (it.value == AnswerType.YES) {
                                isVictim = true
                            }
                        }
                    }

                    binding.textViewVerdict.text =
                        Html.fromHtml(
                            if (isVictim)
                                getString(R.string.is_victim)
                            else
                                getString(R.string.is_not_victim),
                            Html.FROM_HTML_MODE_COMPACT
                        )

                    reportBuilder.append(binding.textViewVerdict.text).append("\n")

                    binding.materialCardViewQuestioner1.visibleWithFade(duration = 500)
                }

                if (!questionerTwoAnswers.isNullOrEmpty()) {
                    val questionerTwoName = "Cuestionario Agresor"
                    val information = "$questionerTwoName • $date • $time "
                    binding.textViewInformationQuestionerSecond.text = information

                    reportBuilder.append("\n\n").append(questionerTwoName).append("\n")
                    reportBuilder.append(information).append("\n")

                    var isAggressor = false

                    questionerTwoAnswers.mapKeys {
                        if (it.key == 5 || it.key == 6 || it.key == 7 || it.key == 8 || it.key == 10 || it.key == 12
                            || it.key == 13 || it.key == 14
                        ) {
                            if (it.value == AnswerType.YES) {
                                isAggressor = true
                            }
                        }
                    }

                    binding.textViewVerdict2.text =
                        Html.fromHtml(
                            if (isAggressor)
                                getString(R.string.is_aggressor)
                            else
                                getString(R.string.is_not_aggressor),
                            Html.FROM_HTML_MODE_COMPACT
                        )

                    reportBuilder.append(binding.textViewVerdict2.text).append("\n")

                    binding.materialCardViewQuestioner2.visibleWithFade(duration = 500)
                }

                if (behaviorInformation.isNotEmpty()) {

                    binding.materialCardViewSpecialist.visibleWithFade(duration = 500)

                    val specialistInformation = "$date • $time • Especialista"
                    binding.textViewSpecialistInformation.text = specialistInformation

                    reportBuilder.append(specialistInformation).append("\n")
                    reportBuilder.append(behaviorInformation)


                    binding.textViewSpecialistEnquiry.text = behaviorInformation
                }

                createQuestionerFile(withContent = reportBuilder.toString())
            }
        }
    }

    private fun createQuestionerFile(withContent: String) {
        try {
            val folderName = "Cuestionarios"
            val fileName = "Cuestionario-${UUID.randomUUID()}.txt"
            val filePath =
                "${requireContext().getExternalFilesDir(null)}/${Environment.DIRECTORY_DOCUMENTS}/$folderName/$fileName"
            val file = File(filePath)
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.createNewFile()
            }
            fillFile(file, withContent)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Reporte generado")
                .setMessage(
                    "El reporte se ha generado en la carpeta de documentos. El camino es: $filePath. " +
                            "\nIndicaciones para llegar a él:\n" +
                            "1 - Abra su explorador de archivos y haga click en android.\n" +
                            "2 - Haga click en data.\n" +
                            "3 - Haga click en com.everything.questioner.\n" +
                            "4 - Haga click en files.\n" +
                            "5 - Haga click en Documentos.\n" +
                            "6 - Haga click en Cuestionarios.\n" +
                            "7 - Haga click en $fileName.\n"
                )
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

            binding.textViewFileName.text = fileName
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun fillFile(file: File, contents: String) {
        val stream = FileOutputStream(file)
        stream.write(contents.toByteArray())
        stream.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}