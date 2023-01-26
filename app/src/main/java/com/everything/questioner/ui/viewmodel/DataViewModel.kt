package com.everything.questioner.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.everything.questioner.data.models.ProfessionalData
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
    private var questionerOneAnswers: Map<String, AnswerType>? = null
    private var questionerTwoAnswers: Map<String, AnswerType>? = null

    fun setProfessionalData(professionalData: ProfessionalData) {
        this.professionalData = professionalData

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                professionalData.let { dataStoreManager.saveToDataStore(it) }
            }
        }
    }

    fun getProfessionalData() = dataStoreManager.getProfessionalData()

    fun setQuestionerOneAnswers(questionerOneAnswers: Map<String, AnswerType>) {
        this.questionerOneAnswers = questionerOneAnswers
    }

    fun setQuestionerTwoAnswers(questionerTwoAnswers: Map<String, AnswerType>) {
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