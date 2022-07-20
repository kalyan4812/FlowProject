package com.example.flowproject.ui.add_task_screen

sealed class AddTaskScreenEvent{

    data class onTitleChange(val title:String):AddTaskScreenEvent()
    data class onDescriptionChange(val description:String):AddTaskScreenEvent()
    object onSaveTask:AddTaskScreenEvent()
    data class onScreenLoaded(val id:Int?=-1):AddTaskScreenEvent()
}
