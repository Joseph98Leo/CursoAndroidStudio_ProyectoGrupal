package com.usil.myappcomponents.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usil.myappcomponents.data.model.Question
import com.usil.myappcomponents.data.api.QuizApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizViewModel : ViewModel() {
//https://opentdb.com/api.php?amount=10
    private val api: QuizApi = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuizApi::class.java)

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val questions: StateFlow<List<Question>> = _questions.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getQuestions() {

        viewModelScope.launch {
            try {
                _isLoading.value = false
                _error.value = null

                val response = api.getQuizzes()
                _questions.value = response.results
            } catch (e: Exception) {
                _error.value = "Error al obtener las preguntas"
            } finally {
                _isLoading.value = false
            }
        }

    }

}

sealed class ApiQuizResult {
    data class Success(val questions: List<Question>) : ApiQuizResult()
    data class Error(val message: String) : ApiQuizResult()
}