package com.andreich.weather.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.andreich.weather.R
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.ui.core.ImageRequestFactory
import org.koin.compose.koinInject

@Composable
fun WeatherItem(weatherItem: CityWeatherItem, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable(onClick = onClick),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        val imageLoader = koinInject<ImageLoader>()
        val imageRequestFactory = remember(context) { ImageRequestFactory(context) }
        Row(modifier = Modifier.fillMaxWidth()) {
            weatherItem.image?.let {
                val request: ImageRequest = remember(it) { imageRequestFactory.create(it) }
                AsyncImage(
                    model = request,
                    imageLoader = imageLoader,
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(R.drawable.city),
                    placeholder = painterResource(R.drawable.city),
                    contentDescription = null
                )
            } ?: Image(painter = painterResource(R.drawable.city), contentDescription = null, modifier = Modifier.weight(1f))
            Column(modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(2f)) {
                Text(text = "${weatherItem.name}: ${weatherItem.temp}℃", fontSize = 18.sp)
                Text(text = weatherItem.description, fontSize = 14.sp, overflow = TextOverflow.Ellipsis, maxLines = 5)
            }
        }
    }

}