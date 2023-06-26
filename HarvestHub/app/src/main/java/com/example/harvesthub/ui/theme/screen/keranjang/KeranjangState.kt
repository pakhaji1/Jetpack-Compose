package com.example.harvesthub.ui.theme.screen.keranjang

import com.example.harvesthub.model.OrderBarang

data class KeranjangState(
    val orderBarang: List<OrderBarang>,
    val totalHarga: Int,
)