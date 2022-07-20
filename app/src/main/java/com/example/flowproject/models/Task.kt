package com.example.flowproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TASK_TABLE")
data class Task(val title:String,val description:String,val isDone:Boolean,@PrimaryKey val id:Int?=null)

