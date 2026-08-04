package com.andreich.weather.presentation.core

import com.andreich.weather.domain.model.Weather
import com.andreich.weather.presentation.weatherdetail.ForecastWeatherItem
import com.andreich.weather.presentation.weatherdetail.TimeOfDay
import com.andreich.weather.presentation.weatherdetail.WeatherCondition
import com.andreich.weather.presentation.weatherdetail.WeatherUi
import com.andreich.weather.presentation.weatherdetail.WindDirection
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Instant


actual class UiDataBuilder(
    private val locale: Locale
) {
    private data class WeatherData(
        val weather: Weather,
        val localDateTime: LocalDateTime
    )
    private fun List<Weather>.buildList(timeZone: TimeZone, offset: Int): List<List<WeatherData>> {
        val localDateTime = Instant.fromEpochSeconds(this[0].dt.toLong()).toLocalDateTime(timeZone)
        var date = localDateTime.date
        var innerList: MutableList<WeatherData> = mutableListOf()
        val outerList: MutableList<List<WeatherData>> = mutableListOf()

        this.forEach { weather ->
            val instant = Instant.fromEpochSeconds(weather.dt.toLong()).toLocalDateTime(timeZone)
            if (date == instant.date) {
                innerList.add(WeatherData(weather, instant))

            } else {
                date = instant.date
                outerList.add(innerList.toList())
                innerList = mutableListOf(WeatherData(weather, instant))
            }
        }
        outerList.add(innerList)
        return outerList.toList()
    }

    actual fun makeForecastWeatherItemList(
        weatherList: List<Weather>,
        timezone: Int
    ): List<ForecastWeatherItem> {
        val timeZone = TimeZone.of(UtcOffset(seconds = timezone).toString())
        val today = Clock.System.todayIn(timeZone)
        val first = WeatherData(weatherList[0], Instant.fromEpochSeconds(weatherList[0].dt.toLong()).toLocalDateTime(timeZone))
        return weatherList.buildList(timeZone, timezone).mapIndexed { index, weatherList ->
            val current = today.plus(index, DateTimeUnit.DAY)
            val localDateTime = weatherList.first().localDateTime
            val localDate = localDateTime.date
            ForecastWeatherItem(
                header = "${
                    current.toJavaLocalDate().dayOfWeek.getDisplayName(
                        TextStyle.FULL,
                        locale
                    )
                }, ${
                    localDate.day
//                    if (day[8] == '0') day.substring(9..9)
//                    else weatherList[0].dtTxt.substring(8..9)
                } ${localDate.toJavaLocalDate().month.getDisplayName(TextStyle.FULL, locale)}",
                weatherList.map { it.toWeatherUi(first) }
            )
        }
    }

    private fun WeatherData.toWeatherUi(first: WeatherData): WeatherUi {
        val localTime = localDateTime.time
        return WeatherUi(
            dt = weather.dt,
            hour = "%02d:%02d".format(localTime.hour, localTime.minute),
            temp = "${weather.temp.toInt()}℃",
            weatherCondition = weatherCondition(first),
            windSpeed = String.format(locale, "%.1f", weather.windSpeed),
            windDirection = weather.windDirectionDeg.windDirection(),
        )
    }

    private fun Int.windDirection(): WindDirection {
        return when (this) {
            in (0..22) -> WindDirection.North
            in (23..67) -> WindDirection.NorthWest
            in (68..112) -> WindDirection.West
            in (113..157) -> WindDirection.SouthWest
            in (158..202) -> WindDirection.South
            in (203..247) -> WindDirection.SouthEast
            in (248..292) -> WindDirection.East
            in (293..337) -> WindDirection.NorthEast
            in (338..360) -> WindDirection.North
            else -> WindDirection.North
        }
    }

    private fun WeatherData.findTimeOfDay(first: WeatherData): TimeOfDay {
        val sun = adjustedSunrise(first)
        return when  {
            weather.dt !in sun.first..sun.second -> TimeOfDay.Night
            else -> TimeOfDay.Day
        }
    }

    private fun WeatherData.adjustedSunrise(first: WeatherData): Pair<Int, Int> {
        val date = localDateTime.date

        val days = first.localDateTime.date.daysUntil(date)

        return first.weather.sunrise + days * 24 * 3600 to first.weather.sunset + days * 24 * 3600
    }

    private fun WeatherData.weatherCondition(first: WeatherData): WeatherCondition {
        return when (findTimeOfDay(first)) {
            TimeOfDay.Day -> when (weather.weatherMain) {
                "Rain" -> WeatherCondition.RainDay
                "Clear" -> WeatherCondition.ClearDay
                "Clouds" -> WeatherCondition.CloudyDay
                "Thunderstorm" -> WeatherCondition.ThunderStormDay
                "Drizzle" -> WeatherCondition.Drizzle
                "Snow" -> WeatherCondition.SnowDay
                "Mist", "Haze", "Fog" -> WeatherCondition.MistDay
                "Tornado" -> WeatherCondition.Tornado
                "Dust", "Smoke", "Sand", "Ash" -> WeatherCondition.Dust
                "Wind" -> WeatherCondition.Wind
                "Squall" -> WeatherCondition.Squall
                else -> WeatherCondition.ClearDay
            }

            TimeOfDay.Night -> when (weather.weatherMain) {
                "Rain" -> WeatherCondition.RainNight
                "Clear" -> WeatherCondition.ClearNight
                "Clouds" -> WeatherCondition.CloudyNight
                "Thunderstorm" -> WeatherCondition.ThunderStormNight
                "Drizzle" -> WeatherCondition.Drizzle
                "Snow" -> WeatherCondition.SnowNight
                "Mist", "Haze", "Fog" -> WeatherCondition.MistNight
                "Tornado" -> WeatherCondition.Tornado
                "Dust", "Smoke", "Sand", "Ash" -> WeatherCondition.Dust
                "Wind" -> WeatherCondition.Wind
                "Squall" -> WeatherCondition.Squall
                else -> WeatherCondition.ClearNight
            }
        }
    }
}