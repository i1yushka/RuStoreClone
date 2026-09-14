package com.example.rustoreclone.ui.catalog

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rustoreclone.data.model.App
import com.example.rustoreclone.data.repository.AppRepository

sealed interface CatalogUiState {
    object Loading : CatalogUiState
    data class Success(val apps: List<App>) : CatalogUiState
    data class Error(val message: String) : CatalogUiState
}

class CatalogViewModel(
    private val repository: AppRepository = AppRepository()
) : ViewModel() {

    private val _uiState = mutableStateOf<CatalogUiState>(CatalogUiState.Loading)
    val uiState: State<CatalogUiState> = _uiState

    init {
        loadApps()
    }

    fun loadApps() {
        _uiState.value = CatalogUiState.Loading
        try {
            val apps = repository.getAllApps()
            _uiState.value = CatalogUiState.Success(apps)
        } catch (e: Exception) {
            _uiState.value = CatalogUiState.Error("Не удалось загрузить приложения")
        }
    }
}