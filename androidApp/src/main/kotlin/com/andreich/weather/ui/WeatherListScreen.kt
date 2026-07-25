package com.andreich.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.andreich.weather.ext.AppBarState
import com.andreich.weather.ext.WeatherFabState
import com.andreich.weather.ext.WeatherItem
import com.andreich.weather.presentation.core.UiMessage
import com.andreich.weather.presentation.weatherlist.WeatherListEvent
import com.andreich.weather.presentation.weatherlist.WeatherListIntent
import com.andreich.weather.presentation.weatherlist.WeatherListState
import com.andreich.weather.presentation.weatherlist.WeatherListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun WeatherListScreen(
    state: WeatherListState,
    lazyListState: LazyListState,
    setFabState: (WeatherFabState) -> Unit,
    onNextPageLoad: () -> Unit,
    onWeatherDetailsClick: (Int) -> Unit
) {
    val shouldLoadMore by remember(lazyListState, state.weatherList.size) {
        derivedStateOf {
            val lastVisible = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisible >= state.weatherList.lastIndex - 5
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onNextPageLoad()
        }
    }
    val scope = rememberCoroutineScope()
    val fabVisual = remember { mutableStateOf(false) }
    val showFab by remember(Unit) {
        derivedStateOf {
            (lazyListState.lastScrolledBackward) && fabVisual.value
                    && lazyListState.firstVisibleItemIndex >= 1
        }
    }
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .collectLatest { scrolling ->
                if (scrolling) {
                    fabVisual.value = true
                } else {
                    delay(1000.milliseconds)
                    fabVisual.value = false
                }
            }
    }
    val scrollToTop = remember(scope) {
        {
            scope.launch {
                lazyListState.scrollToItem(0)
            }
        }
    }
    LaunchedEffect(showFab) {
        setFabState(WeatherFabState(showFab, scrollToTop))
    }
    DisposableEffect(Unit) {
        onDispose {
            setFabState(WeatherFabState(false) { Job() })
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        state = lazyListState
    ) {
        items(state.weatherList, key = { it.id }) {
            WeatherItem(it) {
                onWeatherDetailsClick(it.id)
            }
        }
    }
}

@Composable
fun WeatherListRoute(
    snackbarHostState: SnackbarHostState,
    onSetAppBarState: (AppBarState) -> Unit,
    setFabState: (WeatherFabState) -> Unit,
    onWeatherDetailsNavigate: (Int) -> Unit
) {
    val viewModel = koinViewModel<WeatherListViewModel>()
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(state.menuExpanded) {
        onSetAppBarState(
            AppBarState(
                showFilter = state.menuExpanded,
                onFilterClick = {
                    viewModel.sendIntent(WeatherListIntent.ShowMenu)
                }
            )
        )
    }
    LaunchedEffect(viewModel) {
        viewModel.sendIntent(WeatherListIntent.UpdateWeather)
        viewModel.sendIntent(WeatherListIntent.ObserveWeather)
        viewModel.events.collect {
            when (it) {
                is WeatherListEvent.NavigateToDetails -> onWeatherDetailsNavigate(it.id)
            }
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.messages.collect {
            when (it) {
                is UiMessage.ShowMessage -> snackbarHostState.showSnackbar(message = it.message)
            }
        }
    }
    WeatherListScreen(state, lazyListState, setFabState, onNextPageLoad = {
        viewModel.sendIntent(WeatherListIntent.LoadNextPage)
    }) {
        viewModel.sendIntent(WeatherListIntent.WeatherItemClick(it))
    }
}