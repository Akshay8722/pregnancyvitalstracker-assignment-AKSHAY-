package com.example.pregnancyvitals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pregnancyvitals.data.Vital
import com.example.pregnancyvitals.repository.VitalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VitalViewModel(private val repository: VitalRepository) : ViewModel() {
    val vitals = repository.allVitals.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addVital(vital: Vital) {
        viewModelScope.launch {
            repository.insert(vital)
        }
    }
}

class VitalViewModelFactory(private val repository: VitalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VitalViewModel(repository) as T
    }
}
