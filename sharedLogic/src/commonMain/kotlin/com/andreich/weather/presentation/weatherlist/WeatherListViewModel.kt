package com.andreich.weather.presentation.weatherlist

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.usecase.GetCityListUseCase
import com.andreich.weather.domain.usecase.UpdateCityWeatherListUseCase
import com.andreich.weather.presentation.core.BaseViewModel
import com.andreich.weather.presentation.core.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class WeatherListViewModel(
    private val updateCityWeatherListUseCase: UpdateCityWeatherListUseCase,
    private val getCityListUseCase: GetCityListUseCase
) : BaseViewModel<WeatherListState, WeatherListEvent, WeatherListIntent>(
    WeatherListState()
) {

    private var limit = MutableStateFlow(PAGE_SIZE)

    companion object {

        private const val PAGE_SIZE = 12
    }

    private fun loadNextPage() {
        limit.update { it + PAGE_SIZE }
        if (limit.value > state.value.weatherList.size) {
            _state.update {
                it.copy(isLoadingNextPage = false)
            }
        }
    }

    override fun sendIntent(intent: WeatherListIntent) {
        launch {
            when (intent) {
                WeatherListIntent.HideMenu -> {
                    _state.update { it.copy(menuExpanded = false) }
                }
                WeatherListIntent.LoadNextPage -> {
                    _state.update { it.copy(isLoadingNextPage = true) }
                    loadNextPage()
                }
                WeatherListIntent.ObserveWeather -> {
                    getCityListUseCase(state.value.lang, state.value.country)
                        .onStart {
                            _state.update { it.copy(isLoading = true) }
                        }.onEach { list ->
                            _state.update { it.copy(weatherList = list) }
                        }.collect()
                }
                WeatherListIntent.ShowMenu -> {
                    _state.update { it.copy(menuExpanded = !it.menuExpanded) }
                }
                WeatherListIntent.UpdateWeather -> {
                    val result = updateCityWeatherListUseCase(state.value.lang, state.value.country)
                    when (result) {
                        is RequestResult.Failure.InvalidApiKey -> onRequestError(result.message)
                        is RequestResult.Failure.InvalidParams -> onRequestError(result.message)
                        is RequestResult.Failure.NoInternet -> onRequestError(result.message)
                        is RequestResult.Failure.NotFound -> onRequestError(result.message)
                        is RequestResult.Failure.Serialization -> onRequestError(result.message)
                        is RequestResult.Failure.Server -> onRequestError(result.message)
                        is RequestResult.Failure.TimeOut -> onRequestError(result.message)
                        is RequestResult.Failure.TooManyRequests -> onRequestError(result.message)
                        is RequestResult.Failure.Unknown -> onRequestError(result.message)
                        is RequestResult.Success -> _messages.emit(UiMessage.ShowMessage("Weather successfully updated!"))
                    }
                }
                is WeatherListIntent.WeatherItemClick -> {
                    _events.emit(WeatherListEvent.NavigateToDetails(intent.id))
                }
            }
        }

    }

    suspend fun onRequestError(message: String) {
        _messages.emit(UiMessage.ShowMessage(message))
    }

    override suspend fun onError(e: Throwable) {
        _messages.emit(UiMessage.ShowMessage(e.message.orEmpty()))
    }
}