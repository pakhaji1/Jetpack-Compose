package com.example.harvesthub.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.harvesthub.R

@Composable
fun ProfilScreen(navigateBack: () -> Unit) {
    val image = painterResource(id = R.drawable.gambar_about)
    val nama = "Muhammad Rizeky Rahmatullah"
    val email = "muhammadrizeky64@gmail.com"

    Scaffold(topBar =  {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 10.dp) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateBack() }
            )
            Text(
                text = stringResource(R.string.menu_profile),
                modifier = Modifier.padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }, content = {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = image,
                    contentDescription = "about_image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .padding(30.dp)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Column {
                    Text(
                        text = nama,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = email,
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    })
}