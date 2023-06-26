package com.example.harvesthub.model

data class OrderBarang(
    val barang: Barang,
    val jumlah: Int,
    val isKeranjang: Boolean,
)
