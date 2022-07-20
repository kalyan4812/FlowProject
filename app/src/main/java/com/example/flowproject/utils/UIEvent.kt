package com.example.flowproject.utils

sealed class UIEvent {
    object popBackStack:UIEvent()
    data class Navigate(val id:Int?=-1):UIEvent()
    data class showSnackBar(val messsage:String,val action:String?=null):UIEvent()
}