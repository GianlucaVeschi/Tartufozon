package com.example.tartufozon.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.domain.model.Truffle
import com.example.tartufozon.util.DEFAULT_FOOD_IMAGE
import com.example.tartufozon.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@kotlinx.coroutines.ExperimentalCoroutinesApi
@Composable
fun TruffleCard(
    truffle: Truffle,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column() {
            truffle.image_url.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_FOOD_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Truffle Card image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            truffle.tartufoName.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5
                    )
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Add to Favorites",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Preview
@Composable
fun PreviewTruffleCard() {
    TruffleCard(
        truffle = Truffle(
            1,
            "Tartufinho",
            "Buonisssimo",
            "https://www.moscatotartufi.it/wp-content/uploads/2015/03/vendita-tartufo-bianco-pregiato.jpg",
            9
        ),
        onClick = {
            // TODO: 08.02.21 onCLICK Truffle
        })
}

