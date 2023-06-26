package com.example.harvesthub.data

import com.example.harvesthub.model.Barang
import com.example.harvesthub.model.FakeDataBarang
import com.example.harvesthub.model.OrderBarang
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RepositoryBarang {
//    private val keranjangBarang = mutableListOf<Barang>()
    private val orderBarang = mutableListOf<OrderBarang>()

//    init {
//        if (keranjangBarang.isEmpty()) {
//            FakeDataBarang.dummyBarang.forEach {
//                keranjangBarang.add(it)
//            }
//        }
//    }

    init {
        if (orderBarang.isEmpty()) {
            FakeDataBarang.dummyBarang.forEach {
                orderBarang.add(OrderBarang(it, 0,isKeranjang = false))
            }
        }
    }

//    fun getKeranjangBarang(): Flow<List<Barang>> {
//        return flowOf(keranjangBarang.filter { it.isKeranjang })
//    }
//
//    fun searchBarang(query: String): Flow<List<Barang>> {
//        return flowOf(keranjangBarang.filter {
//            it.nama.contains(query, ignoreCase = true)
//        })
//    }
//
//    fun getOrderKeranjangBarangById(barangId: Long): Barang {
//        return keranjangBarang.first {
//            it.id == barangId
//        }
//    }
//
//    fun updateKeranjangBarang(barangId: Long, newState: Boolean): Flow<Boolean> {
//        val index = keranjangBarang.indexOfFirst { it.id == barangId }
//        val result = if (index >= 0) {
//            val keranjang = keranjangBarang[index]
//            keranjangBarang[index] =
//                keranjang.copy(isKeranjang = newState)
//            true
//        } else {
//            false
//        }
//        return flowOf(result)
//    }

    fun getKeranjangBarang(): Flow<List<OrderBarang>> {
        return flowOf(orderBarang.filter {it.isKeranjang})
    }

    fun searchBarang(query: String): Flow<List<OrderBarang>> {
        return flowOf(orderBarang.filter {
            it.barang.nama.contains(query, ignoreCase = true)
        })
    }

    fun getOrderKeranjangBarangById(barangId: Long): OrderBarang {
        return orderBarang.first {
            it.barang.id == barangId
        }
    }

    fun updateKeranjangBarang(barangId: Long, newCountValue: Int, newState: Boolean): Flow<Boolean> {
        val index = orderBarang.indexOfFirst { it.barang.id == barangId }
        val result = if (index >= 0) {
            val keranjang = orderBarang[index]
            orderBarang[index] =
                keranjang.copy( barang = keranjang.barang, jumlah = newCountValue, isKeranjang = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAllBarang(): Flow<List<OrderBarang>> {
        return flowOf(orderBarang)
    }

//    fun getOrderBarangById(barangId: Long): OrderBarang {
//        return orderBarang.first {
//            it.barang.id == barangId
//        }
//    }

    fun updateOrderBarang(barangId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderBarang.indexOfFirst { it.barang.id == barangId }
        val result = if (index >= 0) {
            val orderBarangs = orderBarang[index]
            orderBarang[index] =
                orderBarangs.copy(barang = orderBarangs.barang, jumlah = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderBarang(): Flow<List<OrderBarang>> {
        return getAllBarang()
            .map { orderBarang ->
                orderBarang.filter { orderBarangs ->
                    orderBarangs.jumlah != 0
                }
            }
    }


    companion object {
        @Volatile
        private var instance: RepositoryBarang? = null

        fun getInstance(): RepositoryBarang =
            instance ?: synchronized(this) {
                RepositoryBarang().apply {
                    instance = this
                }
            }
    }
}