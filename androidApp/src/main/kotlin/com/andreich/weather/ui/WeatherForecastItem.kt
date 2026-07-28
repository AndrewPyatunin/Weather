package com.andreich.weather.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andreich.weather.domain.model.Weather

@Composable
fun WeatherForecastItem(weatherItem: Weather, onClick: () -> Unit) {
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

            Column(modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(2f)) {
                Text(text = "Температура: ${weatherItem.temp}℃", fontSize = 16.sp)
                Text(text = weatherItem.description, fontSize = 14.sp, overflow = TextOverflow.Ellipsis, maxLines = 5)
                Text(text = weatherItem.weatherMain, fontSize = 14.sp)
                Text(text = weatherItem.clouds.toString(), fontSize = 14.sp)
                Text(text = weatherItem.dtTxt, fontSize = 14.sp)
            }
        }
    }

}