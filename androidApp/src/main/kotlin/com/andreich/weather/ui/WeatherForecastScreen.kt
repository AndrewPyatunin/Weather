package com.andreich.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.andreich.weather.R
import com.andreich.weather.ext.WeatherFabState
import com.andreich.weather.presentation.core.UiMessage
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsEvent
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsIntent
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsState
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun WeatherForecastScreen(
    modifier: Modifier,
    state: WeatherDetailsState,
    onAddToFavoriteClick: () -> Unit,
    onRemoveFromFavoriteClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        state = lazyListState
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
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
                IconButton(onClick = {
                    if (state.isFavorite) onRemoveFromFavoriteClick() else onAddToFavoriteClick()
                }, modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopEnd)) {
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
        state.cityWeather?.let {
            items(it.forecastWeather, key = { it.dt }) {
                WeatherForecastItem(it) {

                }
            }
        }


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
        viewModel.sendIntent(WeatherDetailsIntent.UpdateWeather(cityId))
        viewModel.sendIntent(WeatherDetailsIntent.ObserveWeather(cityId))
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