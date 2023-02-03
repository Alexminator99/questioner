package com.everything.questioner.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.everything.questioner.data.models.ProfessionalData
import com.everything.questioner.data.models.ReportData
import com.everything.questioner.data.models.StudentInformation
import com.everything.questioner.utils.AnswerType
import com.everything.questioner.utils.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private var studentInformation: StudentInformation? = null
    private var professionalData: ProfessionalData? = null
    private var behaviorInformation: String? = null
    private var questionerOneAnswers: Map<Int, AnswerType>? = null
    private var questionerTwoAnswers: Map<Int, AnswerType>? = null

    private var reportData: ReportData? = null

    fun setProfessionalData(professionalData: ProfessionalData) {
        this.professionalData = professionalData

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                professionalData.let { dataStoreManager.saveToDataStore(it) }
            }
        }
    }

    fun getProfessionalData() = dataStoreManager.getProfessionalData()

    fun setQuestionerOneAnswers(questionerOneAnswers: Map<Int, AnswerType>) {
        this.questionerOneAnswers = questionerOneAnswers
    }

    fun setQuestionerTwoAnswers(questionerTwoAnswers: Map<Int, AnswerType>) {
        this.questionerTwoAnswers = questionerTwoAnswers
    }

    fun setStudentInformation(studentInformation: StudentInformation) {
        this.studentInformation = studentInformation
    }

    fun setBehaviorInformation(behaviorInformation: String) {
        this.behaviorInformation = behaviorInformation

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dataStoreManager.saveBehaviorInformation(behaviorInformation)
            }
        }
    }

    fun getBehaviorInformation() = dataStoreManager.getBehaviorInformation()
    fun processData() {
        reportData = ReportData(
            studentInformation,
            professionalData,
            behaviorInformation ?: "",
            questionerOneAnswers,
            questionerTwoAnswers
        )
    }

    fun getReportData() = reportData
    fun clearQuestionerData() {
        questionerOneAnswers = null
        questionerTwoAnswers = null
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                DataViewModel(
                    DataStoreManager(application)
                )
            }
        }
    }
}