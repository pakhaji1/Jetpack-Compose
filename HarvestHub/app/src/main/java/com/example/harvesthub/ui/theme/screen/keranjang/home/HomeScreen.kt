package com.example.harvesthub.ui.theme.screen.keranjang.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.ui.theme.components.ItemBarang
import com.example.harvesthub.di.Injection
import com.example.harvesthub.item.EmptyList
import com.example.harvesthub.model.Barang
import com.example.harvesthub.R
import com.example.harvesthub.ViewModelFactory
import com.example.harvesthub.item.Search
import com.example.harvesthub.model.OrderBarang

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    keranjangBarang = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    keranjangBarang: List<OrderBarang>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(R.drawable.banner),
                contentDescription = "Banner Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.height(200.dp)
            )
            Search(
                query = query,
                onQueryChange = onQueryChange
            )
            if (keranjangBarang.isNotEmpty()) {
                keranjangBarang
                navigateToDetail
            } else {
                EmptyList(
                    Warning = stringResource(R.string.data_tidak_ada)
                )
            }
        }
        Text(
            modifier = Modifier.padding(30.dp, 5.dp, 0.dp, 5.dp),
            text = "Pilihan Hari ini",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )

        val rows = keranjangBarang.chunked(2)

        for (row in rows) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                for (item in row) {
                    Card(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .clickable {
                                navigateToDetail(item.barang.id)
                            }
                    ) {
                        ItemBarang(
                            image = item.barang.image,
                            nama = item.barang.nama,
                            harga = item.barang.harga,
                        )
                    }
                }
            }
        }
    }
}
