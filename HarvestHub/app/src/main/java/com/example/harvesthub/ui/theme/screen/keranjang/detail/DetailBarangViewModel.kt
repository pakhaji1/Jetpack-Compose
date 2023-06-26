package com.example.harvesthub.ui.theme.screen.keranjang.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.data.RepositoryBarang
import com.example.harvesthub.model.Barang
import com.example.harvesthub.model.OrderBarang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBarangViewModel(
    private val repository: RepositoryBarang
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderBarang>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderBarang>>
        get() = _uiState

    fun getBarangById(barangId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderKeranjangBarangById(barangId))
        }
    }

    fun updateKeranjangBarang(barangId: Long, jumlah: Int,newState: Boolean) = viewModelScope.launch {
        repository.updateKeranjangBarang(barangId, jumlah,!newState)
            .collect { isUpdated ->
                if (isUpdated) getBarangById(barangId)
            }
    }

}