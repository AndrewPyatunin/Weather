package com.andreich.weather.ext

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreich.weather.R
import com.andreich.weather.domain.model.CityWeatherItem

@Composable
fun WeatherItem(weatherItem: CityWeatherItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable(onClick = onClick),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, color = Color.Black)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(painter = painterResource(R.drawable.city), contentDescription = null, modifier = Modifier.weight(1f))
            Column(modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(2f)) {
                Text(text = "${weatherItem.name}: ${weatherItem.temp}℃", fontSize = 18.sp)
                Text(text = weatherItem.description, fontSize = 14.sp, overflow = TextOverflow.Ellipsis, maxLines = 5)
            }
        }
    }

}