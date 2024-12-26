package com.example.hackverse2.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    // Method to trigger loading state
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    // Method to update text
    fun updateText(newText: String) {
        _text.value = newText
    }
}
