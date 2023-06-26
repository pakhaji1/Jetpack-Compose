package com.example.harvesthub.ui.theme.screen.keranjang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.data.RepositoryBarang
import com.example.harvesthub.model.Barang
import com.example.harvesthub.model.OrderBarang
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KeranjangViewModel(
    private val repository: RepositoryBarang
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<KeranjangState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<KeranjangState>>
        get() = _uiState

    fun getKeranjangBarang() = viewModelScope.launch {
        repository.getKeranjangBarang()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect { orderBarang ->
                val totalHarga =
                    orderBarang.sumOf { it.barang.harga * it.jumlah }
                _uiState.value = UiState.Success(KeranjangState(orderBarang, totalHarga))
            }
    }

    fun updateKeranjangBarang(barangId: Long, jumlah: Int, newState: Boolean) {
        viewModelScope.launch {
            repository.updateKeranjangBarang(barangId, jumlah, newState)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getKeranjangBarang()
                    }
                }
        }
    }
}