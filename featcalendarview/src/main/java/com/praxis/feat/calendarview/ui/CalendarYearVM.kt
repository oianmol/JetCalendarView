package com.praxis.feat.calendarview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetYear
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarYearVM @Inject constructor(private val navigator: Navigator) : ViewModel() {
  private lateinit var year: JetYear
  private lateinit var selectedDate: JetDay
  val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

  var yearState = MutableStateFlow<JetYear?>(null)
    private set
  var gridListSwitch = MutableStateFlow(true)
    private set

  init {
    setYear(LocalDate.now())
  }

  fun setYear(date: LocalDate) {
    viewModelScope.launch {
      selectedDate = JetDay(date, isPartOfMonth = true)
      withContext(Dispatchers.Default) {
        year = JetYear.current(selectedDate.date, firstDayOfWeek)
      }
      yearState.value = year
    }
  }

  fun navigateMonth(jetDay: JetDay) {
    navigator.navigate(Screen.CalendarMonthRoute.createRoute(jetDay.date.toEpochDay()))
  }

  fun switchView() {
    gridListSwitch.value = !gridListSwitch.value
  }

  fun nextYear() {
    yearState.value?.endDate?.plusDays(1)?.let { setYear(it) }

  }

  fun previousYear() {
    yearState.value?.startDate?.minusDays(1)?.let { setYear(it) }
  }

}
