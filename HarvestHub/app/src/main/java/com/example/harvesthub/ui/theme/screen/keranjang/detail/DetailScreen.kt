package com.example.harvesthub.ui.theme.screen.keranjang.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.harvesthub.ui.theme.common.UiState
import com.example.harvesthub.di.Injection
import com.example.harvesthub.item.RatingBarView
import com.example.harvesthub.R
import com.example.harvesthub.ViewModelFactory
import com.example.harvesthub.ui.theme.components.AddKeranjangButton

@Composable
fun DetailScreen(
    barangId: Long,
    viewModel: DetailBarangViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBarangById(barangId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.barang.id,
                    data.barang.image,
                    data.barang.nama,
                    data.barang.berat,
                    data.isKeranjang,
                    data.barang.harga,
                    data.barang.deskripsi,
                    data.jumlah,
                    onBackClick = navigateBack,
                    onAddToCart = {barangId,jumlah,state ->
                        viewModel.updateKeranjangBarang(barangId,jumlah,state)
                        navigateToCart()
                    },
                )
            }
            is UiState.Error -> {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun DetailContent(
    id: Long,
    image: String,
    nama: String,
    berat: String,
    isKeranjang: Boolean,
    harga: Int,
    deskripsi: String,
    jumlah: Int,
    onBackClick: () -> Unit,
    onAddToCart: (id: Long,jumlah: Int, state:Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(topBar =  {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 10.dp
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = Color.White,
                contentDescription = stringResource(com.example.harvesthub.R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = stringResource(com.example.harvesthub.R.string.menu_detail),
                modifier = Modifier.padding(horizontal = 12.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }, content = {
        Column(
            modifier = modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Box {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Column{
                        Text(
                            text = nama,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = berat,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.harga, harga),
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = deskripsi,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color.LightGray)
                )
                Row(modifier = Modifier
                    .padding(20.dp)){
                    Image(
                        painter = painterResource(com.example.harvesthub.R.drawable.dummy_avatar),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .padding(4.dp)
                            .border(3.dp, MaterialTheme.colors.primary, CircleShape)
                            .clip(CircleShape)
                            .size(70.dp)
                    )
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Pak Dadang",
                            fontWeight = FontWeight.Bold,
                        )

                        RatingBarView(
                            modifier = modifier.padding(0.dp,10.dp,0.dp,0.dp),
                            rating = mutableStateOf(4),
                            isRatingEditable = false,
                            isViewAnimated = true,
                            starIcon = painterResource(id = R.drawable.icon_star),
                            unRatedStarsColor = Color.LightGray,
                            starsPadding = 5.dp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color.LightGray)
                )
            }

            BottomAppBar(elevation = 100.dp) {
                AddKeranjangButton(
                    modifier = Modifier.padding(50.dp, 10.dp, 50.dp, 10.dp),
                    text = stringResource(R.string.tambah_ke_keranjang),
                    onClick = {
                        onAddToCart(id,jumlah, isKeranjang)
                    }
                )
            }
        }
    })
}