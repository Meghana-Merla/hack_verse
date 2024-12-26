package com.example.hackverse2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>().apply {
        value = null
    }
    val error: LiveData<String?> = _error

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(message: String) {
        _error.value = message
    }

    fun updateText(newText: String) {
        _text.value = newText
    }
}
