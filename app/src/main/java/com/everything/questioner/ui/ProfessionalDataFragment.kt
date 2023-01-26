package com.everything.questioner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.everything.questioner.R
import com.everything.questioner.data.models.ProfessionalData
import com.everything.questioner.databinding.FragmentProfessionalDataBinding
import com.everything.questioner.ui.viewmodel.DataViewModel
import com.everything.questioner.utils.collectFlow
import com.everything.questioner.utils.visibleWithFade
import kotlin.math.exp

/**
 * A simple [Fragment] subclass.
 * Use the [ProfessionalDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfessionalDataFragment : Fragment() {
    private var _binding: FragmentProfessionalDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        _binding = FragmentProfessionalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initVM()
    }

    private fun initUI() {
        binding.progressBar.show()

        // Create an ArrayAdapter using the string array and a default spinner layout with sex string resource
        val sexAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.sex_options, R.layout.dropdown_menu_popup_item
        )
        // Specify the layout to use when the list of choices
        binding.autoCompleteSex.setAdapter(sexAdapter)

        binding.buttonContinue.setOnClickListener {
            // Check if all fields are filled
            if (checkUI()) {

                // Create a ProfessionalData object with the data from the fields
                val data = ProfessionalData(
                    name = binding.etName.text.toString(),
                    scientificCategory = binding.etScientificCategory.text.toString(),
                    sex = binding.autoCompleteSex.text.toString(),
                    specialist = binding.etSpecialty.text.toString(),
                    experience = binding.etYearsExpertise.text.toString(),
                    graduatedOf = binding.etGraduated.text.toString(),
                    organization = binding.etWorkCenter.text.toString(),
                    profession = binding.etProfession.text.toString(),
                    docentCategory = binding.etDocentCategory.text.toString(),
                )

                viewModel.setProfessionalData(professionalData = data)
                findNavController().navigate(ProfessionalDataFragmentDirections.actionProfessionalDataFragmentToBehaviorQuestionFragment())
            }
        }
    }

    private fun initVM() {
        collectFlow(viewModel.getProfessionalData()) {
            if (it != null) {
                binding.nestedScrollViewQuestioner.visibleWithFade(duration = 500)
                bindFields(it)
            }
            binding.progressBar.hide()
        }
    }

    private fun bindFields(professionalData: ProfessionalData) {
        binding.etName.setText(professionalData.name)
        binding.etScientificCategory.setText(professionalData.scientificCategory)
        binding.autoCompleteSex.setText(professionalData.sex)
        binding.etSpecialty.setText(professionalData.specialist)
        binding.etYearsExpertise.setText(professionalData.experience)
        binding.etGraduated.setText(professionalData.graduatedOf)
        binding.etWorkCenter.setText(professionalData.organization)
        binding.etProfession.setText(professionalData.profession)
        binding.etDocentCategory.setText(professionalData.docentCategory)
    }

    /*
     * Check if all fields are filled
     */
    private fun checkUI(): Boolean {
        binding.etProfession.error = if (binding.etProfession.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.autoCompleteSex.error = if (binding.autoCompleteSex.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etDocentCategory.error = if (binding.etDocentCategory.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etScientificCategory.error = if (binding.etScientificCategory.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etGraduated.error = if (binding.etGraduated.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etWorkCenter.error = if (binding.etWorkCenter.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etName.error = if (binding.etName.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etSpecialty.error = if (binding.etSpecialty.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }
        binding.etYearsExpertise.error = if (binding.etYearsExpertise.text.toString().isEmpty()) {
            getString(R.string.error_field_required)
        } else {
            null
        }

        return binding.etProfession.error == null &&
                binding.autoCompleteSex.error == null &&
                binding.etDocentCategory.error == null &&
                binding.etScientificCategory.error == null &&
                binding.etGraduated.error == null &&
                binding.etWorkCenter.error == null &&
                binding.etName.error == null &&
                binding.etSpecialty.error == null &&
                binding.etYearsExpertise.error == null
    }
}