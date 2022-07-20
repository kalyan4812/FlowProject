package com.example.flowproject.ui.add_task_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowproject.models.Task
import com.example.flowproject.repositories.TaskRepository
import com.example.flowproject.ui.add_task_screen.AddTaskScreenEvent.*
import com.example.flowproject.utils.UIEvent
import com.example.flowproject.utils.UIEvent.popBackStack
import com.example.flowproject.utils.UIEvent.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //state flows.
    var task = MutableStateFlow<Task>(Task("", "", false))


    var title = MutableStateFlow("")
    val live_title = title.asStateFlow()

    var description = MutableStateFlow("")
    val live_description = description.asStateFlow()

    //channel flow.
    private val _uievents = Channel<UIEvent>()
    val ui_events = _uievents.receiveAsFlow()


    fun onEvent(event: AddTaskScreenEvent) {
        when (event) {
            is onScreenLoaded -> {
                if (event.id != -1) {
                    viewModelScope.launch {
                        taskRepository.getTaskById(event.id!!)?.let { utask ->
                            title.value = utask.title
                            description.value = utask.description ?: ""
                            this@AddTaskViewModel.task.value = utask
                        }
                    }
                }
            }
            is onTitleChange -> {
                title.value = event.title
            }
            is onDescriptionChange -> {
                description.value = event.description
            }
            is onSaveTask -> {
                viewModelScope.launch {
                    if (title.value.isBlank()) {
                        sendUiEvent(showSnackBar("Title can't be empty"))
                        return@launch
                    }
                    taskRepository.insertTask(
                        Task(
                            title.value,
                            description.value,
                            isDone = task.value?.isDone ?: false, id = task.value?.id
                        )
                    )
                    sendUiEvent(popBackStack)
                }
            }

        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uievents.send(event)
        }
    }

}