package com.example.harvesthub.ui.theme.screen.keranjang.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.data.RepositoryBarang
import com.example.harvesthub.model.Barang
import com.example.harvesthub.model.OrderBarang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RepositoryBarang
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderBarang>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderBarang>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch{
        _query.value = newQuery
        repository.searchBarang(_query.value)
            .catch { _uiState.value = UiState.Error(it.message.toString())
            }
            .collect{_uiState.value = UiState.Success(it)
            }
    }
}