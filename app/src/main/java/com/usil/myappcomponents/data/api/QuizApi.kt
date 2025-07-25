package com.usil.myappcomponents.data.api

import com.usil.myappcomponents.data.model.QuestionResponse
import retrofit2.http.GET

interface QuizApi {

    @GET("api.php?amount=8")
    suspend fun getQuizzes(): QuestionResponse

}
