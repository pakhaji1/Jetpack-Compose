package com.example.harvesthub.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.TrashAlt
import com.example.harvesthub.R

@Composable
fun ItemKeranjang(
    barangId: Long,
    image: String,
    nama: String,
    harga: Int,
    isKeranjang: Boolean,
    jumlah: Int,
    onAddToChart: (barangId: Long,jumlah: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),

        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = modifier
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(350.dp, 100.dp)

            )
            Row(
                modifier = Modifier
                    .padding(10.dp, 10.dp, 0.dp, 10.dp)
            ) {
                Column {
                    Text(
                        text = nama,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(R.string.harga, harga),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row{
            ProductCounter(
                orderId = barangId,
                orderCount = jumlah,
                onProductIncreased = { onAddToChart(barangId, jumlah + 1,true) },
                onProductDecreased = { onAddToChart(barangId, jumlah - 1, true) },
                modifier = Modifier.padding(0.dp,110.dp,10.dp,0.dp)
            )
        }
        Surface(
            shape = RoundedCornerShape(size = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            color = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.primary,
            elevation = 100.dp,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
        ) {
            Icon(
                imageVector = if (isKeranjang) FontAwesomeIcons.Regular.TrashAlt else FontAwesomeIcons.Regular.TrashAlt,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .testTag("item_delete")
                    .clickable { onAddToChart(barangId, jumlah, !isKeranjang) }
            )
        }
    }
}

