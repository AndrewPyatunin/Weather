package com.andreich.weather.presentation.weatherdetail

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.usecase.GetCityImageUseCase
import com.andreich.weather.domain.usecase.GetCityWeatherInfoUseCase
import com.andreich.weather.domain.usecase.InsertWeatherForecastUseCase
import com.andreich.weather.domain.usecase.UpdateCityImageUseCase
import com.andreich.weather.domain.usecase.UpdateWeatherForecastUseCase
import com.andreich.weather.presentation.core.BaseViewModel
import com.andreich.weather.presentation.core.UiMessage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class WeatherDetailsViewModel(
    private val getCityImageUseCase: GetCityImageUseCase,
    private val getCityWeatherInfoUseCase: GetCityWeatherInfoUseCase,
    private val updateCityImageUseCase: UpdateCityImageUseCase,
    private val updateWeatherForecastUseCase: UpdateWeatherForecastUseCase,
    private val insertWeatherForecastUseCase: InsertWeatherForecastUseCase
) : BaseViewModel<WeatherDetailsState, WeatherDetailsEvent, WeatherDetailsIntent>(
    initialState = WeatherDetailsState()
) {
    override fun sendIntent(intent: WeatherDetailsIntent) {
        launch {
            when (intent) {
                is WeatherDetailsIntent.LoadImage -> {
                    getCityImageUseCase(intent.id).onStart {
                        _state.update { it.copy(imageLoading = true) }
                    }.onEach { image ->
                        _state.update { it.copy(cityImage = image, imageLoading = false) }
                    }.collect()
                }
                is WeatherDetailsIntent.ObserveWeather -> {
                    getCityWeatherInfoUseCase(intent.id).onStart {
                        _state.update { it.copy(isLoading = true) }
                    }.onEach { weather ->
                        _state.update { it.copy(cityWeather = weather, isFavorite = weather.isFavorite) }
                    }.collect()
                }

                is WeatherDetailsIntent.UpdateImage -> {
                    when(val result = updateCityImageUseCase(intent.id)) {
                        is RequestResult.Failure.InvalidApiKey -> showError(result.message)
                        is RequestResult.Failure.InvalidParams -> showError(result.message)
                        is RequestResult.Failure.NoInternet -> showError(result.message)
                        is RequestResult.Failure.NotFound -> showError(result.message)
                        is RequestResult.Failure.Serialization -> showError(result.message)
                        is RequestResult.Failure.Server -> showError(result.message)
                        is RequestResult.Failure.TimeOut -> showError(result.message)
                        is RequestResult.Failure.TooManyRequests -> showError(result.message)
                        is RequestResult.Failure.Unknown -> showError(result.message)
                        RequestResult.Success -> {}
                        RequestResult.Undefined -> {}
                    }
                }
                is WeatherDetailsIntent.UpdateWeather -> {
                    when(val result = updateWeatherForecastUseCase(intent.id)) {
                        is RequestResult.Failure.InvalidApiKey -> showError(result.message)
                        is RequestResult.Failure.InvalidParams -> showError(result.message)
                        is RequestResult.Failure.NoInternet -> showError(result.message)
                        is RequestResult.Failure.NotFound -> showError(result.message)
                        is RequestResult.Failure.Serialization -> showError(result.message)
                        is RequestResult.Failure.Server -> showError(result.message)
                        is RequestResult.Failure.TimeOut -> showError(result.message)
                        is RequestResult.Failure.TooManyRequests -> showError(result.message)
                        is RequestResult.Failure.Unknown -> showError(result.message)
                        RequestResult.Success -> {}
                        RequestResult.Undefined -> {}
                    }
                }

                WeatherDetailsIntent.AddToFavorite -> {
                    state.value.cityWeather?.let { insertWeatherForecastUseCase(it.copy(isFavorite = true)) }
                }
                WeatherDetailsIntent.RemoveFromFavorite -> {
                    state.value.cityWeather?.let { insertWeatherForecastUseCase(it.copy(isFavorite = false)) }
                }
            }
        }
    }

    private suspend fun showError(message: String) {
        _messages.emit(UiMessage.ShowMessage(message))
    }

    override suspend fun onError(e: Throwable) {
        _messages.emit(UiMessage.ShowMessage(e.message.orEmpty()))
    }
}