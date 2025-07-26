package com.usil.myappcomponents.domain.repository

import com.usil.myappcomponents.data.model.Question
import com.usil.myappcomponents.utils.Result

interface QuizRepository {

    suspend fun getQuizQuestions(): Result<List<Question>>

}