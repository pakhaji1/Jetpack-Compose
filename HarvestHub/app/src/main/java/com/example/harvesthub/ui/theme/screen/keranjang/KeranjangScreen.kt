package com.example.harvesthub.ui.theme.screen.keranjang

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harvesthub.R
import com.example.harvesthub.ViewModelFactory
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.ui.theme.components.ItemKeranjang
import com.example.harvesthub.di.Injection
import com.example.harvesthub.ui.theme.components.OrderButton

@Composable
fun KeranjangScreen(
    viewModel: KeranjangViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit,
    navigateBack: () -> Unit,
    onOrderButtonClick: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getKeranjangBarang()
            }
            is UiState.Success -> {
                val data = uiState.data
                CartContent(
                    data,
                    navigateToDetail = navigateToDetail,
                    onAddToChart = { id, jumlah, newState ->
                        viewModel.updateKeranjangBarang(id, jumlah,newState)
                    },
                    onOrderButtonClick = onOrderButtonClick,
                    navigateBack = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartContent(
    state: KeranjangState,
    navigateToDetail: (Long) -> Unit,
    onAddToChart: (id: Long, jumlah:Int, newState: Boolean) -> Unit,
    onOrderButtonClick: (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderBarang.count(),
        state.totalHarga
    )

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 10.dp
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateBack() }
            )
            Text(
                text = stringResource(R.string.menu_keranjang),
                modifier = Modifier.padding(horizontal = 12.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }, content = { it ->
        Column(
            modifier = modifier
                .padding(it)
        ) {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
                    .weight(1f)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.orderBarang, key = { it.barang.id }) { item ->
                        ItemKeranjang(
                            barangId = item.barang.id,
                            image = item.barang.image,
                            nama = item.barang.nama,
                            harga = item.barang.harga,
                            onAddToChart = onAddToChart,
                            isKeranjang = item.isKeranjang,
                            jumlah = item.jumlah,
                            modifier = Modifier
                                .animateItemPlacement(tween(durationMillis = 200))
                                .shadow(2.dp)
                                .clickable { navigateToDetail(item.barang.id) }
                        )
                        Divider()
                    }
                }
            }
            BottomAppBar(elevation = 100.dp) {
                OrderButton(
                    modifier = Modifier.padding(50.dp, 10.dp, 50.dp, 10.dp),
                    text = stringResource(R.string.tambah_ke_keranjang, state.totalHarga),
                    onClick = {
                        onOrderButtonClick(shareMessage)
                    }
                )
            }}
    })
}
