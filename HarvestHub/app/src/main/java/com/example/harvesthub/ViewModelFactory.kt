package com.example.harvesthub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harvesthub.data.RepositoryBarang
import com.example.harvesthub.ui.theme.screen.keranjang.detail.DetailBarangViewModel
import com.example.harvesthub.ui.theme.screen.keranjang.home.HomeViewModel
import com.example.harvesthub.ui.theme.screen.keranjang.KeranjangViewModel

class ViewModelFactory(private val repository: RepositoryBarang) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBarangViewModel::class.java)) {
            return DetailBarangViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(KeranjangViewModel::class.java)) {
            return KeranjangViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}