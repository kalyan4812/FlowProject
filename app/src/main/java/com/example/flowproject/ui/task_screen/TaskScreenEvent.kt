package com.example.flowproject.ui.task_screen

import com.example.flowproject.models.Task

sealed class TaskScreenEvent {

    // possible ui actions on taskScreen.

    data class deleteTask(val task: Task) : TaskScreenEvent() // deleting task.

    data class taskState(val task: Task, val isDone: Boolean) :
        TaskScreenEvent() // check or uncheck task.

    object onUndoClick : TaskScreenEvent() // undo the deletion of task.

    data class onTaskClick(val task: Task) : TaskScreenEvent() // onclick of task to edit the task.

    object addTaskClick : TaskScreenEvent()


    // if we have params use data class otherwise object.
}
