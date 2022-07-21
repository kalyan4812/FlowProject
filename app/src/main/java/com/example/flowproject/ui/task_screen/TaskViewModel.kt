package com.example.flowproject.ui.task_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowproject.models.Task
import com.example.flowproject.repositories.TaskRepository
import com.example.flowproject.ui.task_screen.TaskScreenEvent.*
import com.example.flowproject.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    private val _uievents = Channel<UIEvent>()
    val ui_events = _uievents.receiveAsFlow()

    private var deletedTask: Task? = null
    fun OnEventRecieved(event: TaskScreenEvent) {
        when (event) {
            is deleteTask -> {
                viewModelScope.launch {
                    deletedTask = event.task
                    taskRepository.deleteTask(event.task)
                    sendUiEvent(UIEvent.showSnackBar(messsage = "Task Deleted", action = "Undo"))
                }

            }

            is taskState -> {
                viewModelScope.launch {
                    println(event.task.title+" "+event.task.id)
                    taskRepository.updateTask(event.task.copy(isDone = event.isDone))
                }

            }
            is onUndoClick -> {

                deletedTask?.let {
                    viewModelScope.launch {
                        taskRepository.insertTask(it)
                    }
                }

            }

            is onTaskClick -> {
                sendUiEvent(UIEvent.Navigate(id = event.task.id))
            }
            is addTaskClick -> {
                sendUiEvent(UIEvent.Navigate(id = -1))
            }

        }
    }

    fun getFlowTasks(): Flow<List<Task>> {
        return taskRepository.getTasks()
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uievents.send(event)
        }
    }
}