package com.example.hackverse2.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    // If you need to update the LiveData value, use setValue()
    fun updateText(newText: String) {
        _text.value = newText  // Use setValue() instead of assigning directly
    }
}
