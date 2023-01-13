package com.everything.questioner.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.everything.questioner.data.models.ProfessionalData
import com.everything.questioner.data.models.StudentInformation
import com.everything.questioner.utils.AnswerType

class DataViewModel : ViewModel() {
    private var studentInformation: StudentInformation? = null
    private var professionalData: ProfessionalData? = null
    private var questionerOneAnswers: Map<String, AnswerType>? = null
    private var questionerTwoAnswers: Map<String, AnswerType>? = null

    fun setProfessionalData(professionalData: ProfessionalData) {
        this.professionalData = professionalData
    }

    fun setQuestionerOneAnswers(questionerOneAnswers: Map<String, AnswerType>) {
        this.questionerOneAnswers = questionerOneAnswers
    }

    fun setQuestionerTwoAnswers(questionerTwoAnswers: Map<String, AnswerType>) {
        this.questionerTwoAnswers = questionerTwoAnswers
    }

    fun setStudentInformation(studentInformation: StudentInformation) {
        this.studentInformation = studentInformation
    }


}