package com.usil.myappcomponents.data.repository

import com.usil.myappcomponents.data.api.QuizApi
import com.usil.myappcomponents.data.model.Question
import com.usil.myappcomponents.domain.repository.QuizRepository
import com.usil.myappcomponents.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuizRepositoryImpl(
    private val apiService: QuizApi
): QuizRepository{

    override suspend fun getQuizQuestions(): Result<List<Question>> = withContext(Dispatchers.IO
    ){
        try {
            val response = apiService.getQuizzes()
            if (response.results.isNotEmpty()) {
                Result.Success(response.results)
            } else {
                Result.Error("No questions found")
            }
        } catch (e: Exception) {
            Result.Error("Error fetching quiz questions: ${e.message}", e)
        }
    }
}

