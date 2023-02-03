package com.everything.questioner.data.models

import com.everything.questioner.utils.AnswerType

data class ReportData(
    val studentInformation: StudentInformation?,
    val professionalData: ProfessionalData?,
    val behaviorInformation: String = "",
    val questionerOneAnswers: Map<Int, AnswerType>?,
    val questionerTwoAnswers: Map<Int, AnswerType>?
)