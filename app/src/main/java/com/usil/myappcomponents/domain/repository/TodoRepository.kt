package com.usil.myappcomponents.domain.repository
import com.usil.myappcomponents.utils.Result
import com.usil.myappcomponents.data.model.Todo
import com.usil.myappcomponents.data.model.TodoUpsert

// Creemos el contrato del modelo co la API
//



interface TodoRepository {

    // async - suspend
    suspend fun getTodos(): Result<List<Todo>>

    suspend fun getTodoById(id: Int): Result<Todo>

    suspend fun createTodo(todoUpsert: TodoUpsert): Result<Todo>

    suspend fun updatedTodo(id: Int, todoUpsert: TodoUpsert): Result<Todo>

}

















