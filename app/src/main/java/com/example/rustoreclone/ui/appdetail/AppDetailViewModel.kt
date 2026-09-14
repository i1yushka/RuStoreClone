package com.example.rustoreclone.ui.appdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.rustoreclone.data.model.App
import com.example.rustoreclone.data.repository.AppRepository

sealed interface AppDetailUiState {
    object Loading : AppDetailUiState
    data class Success(val app: App) : AppDetailUiState
    data class Error(val message: String) : AppDetailUiState
}

class AppDetailViewModel(
    private val repository: AppRepository = AppRepository()
) : ViewModel() {

    private val _uiState = mutableStateOf<AppDetailUiState>(AppDetailUiState.Loading)
    val uiState: State<AppDetailUiState> = _uiState

    fun loadApp(appId: String) {
        _uiState.value = AppDetailUiState.Loading
        val app = repository.getAppById(appId)
        _uiState.value = if (app != null) {
            AppDetailUiState.Success(app)
        } else {
            AppDetailUiState.Error("Приложение не найдено")
        }
    }
}