package com.andreich.weather.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.andreich.weather.R
import com.andreich.weather.ext.WeatherFabState
import com.andreich.weather.presentation.core.UiMessage
import com.andreich.weather.presentation.weatherdetail.WeatherCondition
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsEvent
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsIntent
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsState
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsViewModel
import com.andreich.weather.presentation.weatherdetail.WeatherUi
import com.andreich.weather.presentation.weatherdetail.WindDirection
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun WeatherForecastScreen(
    modifier: Modifier,
    state: WeatherDetailsState,
    onAddToFavoriteClick: () -> Unit,
    onRemoveFromFavoriteClick: () -> Unit
) {
    val lazyState = rememberLazyListState()
    val weatherList = state.cityWeather?.forecastWeatherList
    if (state.isLoading) {
        CircularProgressIndicator()
    }
    weatherList?.let { list ->
        SideEffect {
            Log.d("Compose", "WeatherForecastScreen recomposed")
        }
        val windDirectionArray = stringArrayResource(R.array.wind_direction)
        LazyColumn(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize(), state = lazyState
        ) {

            item {
                Text(
                    text = stringResource(
                        R.string.weather_in_city,
                        if (state.lang == "ru") state.cityWeather?.cityName?.dropLastWhile { it == 'а' || it == 'я' }
                            .plus("е") else state.cityWeather?.cityName ?: ""
                    ), fontSize = 20.sp, modifier = Modifier.padding(4.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    if (state.imageLoading) CircularProgressIndicator()
                    state.cityImage?.let {
                        AsyncImage(
                            model = it.regular,
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            placeholder = painterResource(R.drawable.city),
                            error = painterResource(R.drawable.city)
                        )
                    } ?: Image(
                        painter = painterResource(R.drawable.city),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )
                    IconButton(
                        onClick = {
                            if (state.isFavorite) onRemoveFromFavoriteClick() else onAddToFavoriteClick()
                        }, modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(R.drawable.favorite_filled),
                                contentDescription = null,
                                tint = Color.Gray
                            )
                            Icon(
                                painter = if (state.isFavorite) painterResource(R.drawable.favorite_true) else painterResource(
                                    R.drawable.favorite_24px
                                ),
                                modifier = Modifier.size(24.dp),
                                contentDescription = null,
                                tint = if (state.isFavorite) Color.Red else Color.Unspecified
                            )
                        }

                    }
                }
            }
            items(list, key = { it.listWeather[0].dt }) { item ->
                Card(
                    modifier = Modifier.padding(4.dp),
                    shape = CardDefaults.elevatedShape,
                    elevation = CardDefaults.cardElevation(8.dp),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onSecondary)
                ) {
                    SideEffect {
                        Log.d("Compose_card", "Card recomposed")
                    }
                    Text(item.header.replaceFirstChar{ it.uppercase() }, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                    HorizontalDivider()
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        item.listWeather.forEach { weather ->
                            WeatherRow(weather, windDirectionArray)

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WeatherRow(weather: WeatherUi, windDirectionArray: Array<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = weather.hour, fontSize = 12.sp, modifier = Modifier.weight(1f))
        val iconId = remember(weather.dt) {
            weather.weatherIcon()
        }
        val windDirections =
            remember(weather.dt) { weather.windDirection.windDirectionRes(windDirectionArray) }
        TempRow(weather, iconId, modifier = Modifier.weight(1f))
        weather.WindRow(windDirections, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun WeatherUi.WindRow(windDirections: Pair<Int, String>, modifier: Modifier) {
    Row(modifier) {
        Text(
            text = stringResource(
                R.string.wind_speed,
                windSpeed
            ), fontSize = 14.sp, modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
            painter = painterResource(windDirections.first),
            contentDescription = null,
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.CenterVertically)
        )
        Text(text = windDirections.second, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 4.dp))
    }
}

@Composable
private fun TempRow(weather: WeatherUi, iconId: Int, modifier: Modifier) {
    Row(modifier.padding(end = 16.dp), horizontalArrangement = Arrangement.Center) {
        Text(
            weather.temp,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
            painter = painterResource(iconId),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

private fun WindDirection.windDirectionRes(windDirections: Array<String>): Pair<Int, String> {
    return when (this) {
        WindDirection.North -> R.drawable.wind_north_navigation to windDirections[0]
        WindDirection.NorthWest -> R.drawable.wind_west_navigation to windDirections[1]
        WindDirection.West -> R.drawable.wind_west_navigation to windDirections[2]
        WindDirection.SouthWest -> R.drawable.wind_south_west_navigation to windDirections[3]
        WindDirection.South -> R.drawable.wind_south_navigation to windDirections[4]
        WindDirection.SouthEast -> R.drawable.wind_south_east_navigation to windDirections[5]
        WindDirection.East -> R.drawable.wind_east_navigation to windDirections[6]
        WindDirection.NorthEast -> R.drawable.wind_north_east_navigation to windDirections[7]
    }
}

private fun WeatherUi.weatherIcon(): Int {
    return when (weatherCondition) {
        WeatherCondition.ClearDay -> R.drawable.clear_day_cropped
        WeatherCondition.ClearNight -> R.drawable.clear_night_cropped
        WeatherCondition.CloudyDay -> R.drawable.cloudy_day_cropped
        WeatherCondition.CloudyNight -> R.drawable.cloudy_night_cropped
        WeatherCondition.Drizzle -> R.drawable.hail_cropped
        WeatherCondition.Dust -> R.drawable.dust_cropped
        WeatherCondition.MistDay -> R.drawable.fog_day_cropped
        WeatherCondition.MistNight -> R.drawable.fog_night_cropped
        WeatherCondition.RainDay -> R.drawable.rainy_day_cropped
        WeatherCondition.RainNight -> R.drawable.rainy_night_cropped
        WeatherCondition.SnowDay -> R.drawable.snowy_2_day_cropped
        WeatherCondition.SnowNight -> R.drawable.snowy_2_night_cropped
        WeatherCondition.Squall -> R.drawable.hurricane_cropped
        WeatherCondition.ThunderStormDay -> R.drawable.thunderstorm_cropped
        WeatherCondition.ThunderStormNight -> R.drawable.thunderstorm_night_cropped
        WeatherCondition.Tornado -> R.drawable.tornado_cropped
        WeatherCondition.Wind -> R.drawable.wind_cropped
    }
}

@Composable
fun WeatherForecastRoute(
    snackbarHostState: SnackbarHostState,
    cityId: Int,
    setFabState: (WeatherFabState) -> Unit,
) {
    val viewModel: WeatherDetailsViewModel =
        koinViewModel<WeatherDetailsViewModel>(parameters = { parametersOf(cityId) })
    val state by viewModel.state.collectAsState()
    LaunchedEffect(viewModel) {
        Log.d("WEATHER_DETAILS_ROUTE_ID", cityId.toString())
        viewModel.sendIntent(WeatherDetailsIntent.ObserveWeather(cityId))
        viewModel.sendIntent(WeatherDetailsIntent.UpdateWeather(cityId))
        viewModel.sendIntent(WeatherDetailsIntent.ObserveFavorites(cityId))
        viewModel.sendIntent(WeatherDetailsIntent.UpdateImage(cityId))
        viewModel.sendIntent(WeatherDetailsIntent.LoadImage(cityId))
        viewModel.messages.collect {
            when (it) {
                is UiMessage.ShowMessage -> snackbarHostState.showSnackbar(it.message)
            }
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.events.collect {
            when (it) {
                is WeatherDetailsEvent.AddToFavoriteSuccess -> {
                    snackbarHostState.showSnackbar(it.message)
                }

                is WeatherDetailsEvent.RemoveFromFavoriteSuccess -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }
    WeatherForecastScreen(Modifier, state, onAddToFavoriteClick = {
        viewModel.sendIntent(WeatherDetailsIntent.AddToFavorite)
    }, onRemoveFromFavoriteClick = {
        viewModel.sendIntent(WeatherDetailsIntent.RemoveFromFavorite)
    })
}