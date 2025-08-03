package com.example.printzy

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class PrintOrder(
    var jobType: String = "",
    var cover: String = "",
    var printOption: String = "",
    var color: String = "",
    var bookSize: String = "",
    var orientation: String = ""
)

class OrderViewModel : ViewModel() {
    val order = mutableStateOf(PrintOrder())

    fun setJobType(type: String) {
        order.value = order.value.copy(jobType = type)
    }

    fun setOptions(
        cover: String,
        printOption: String,
        color: String,
        bookSize: String,
        orientation: String
    ) {
        order.value = order.value.copy(
            cover = cover,
            printOption = printOption,
            color = color,
            bookSize = bookSize,
            orientation = orientation
        )
    }
}