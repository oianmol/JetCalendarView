package com.praxis.feat.calendarview.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.libjetcalendar.data.JetMonth
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
class CalendarMonthVM @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val navigator: Navigator
) : ViewModel() {

  private val monthDate =
    savedStateHandle.get<Long>(Screen.CalendarMonthRoute.navArguments.first().name)!!
  var selectedDate: LocalDate = LocalDate.ofEpochDay(monthDate)
  private val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

  var titleState = MutableStateFlow("")
    private set
  var month = MutableStateFlow<JetMonth?>(null)
    private set

  init {
    dateSelected(selectedDate)
  }

  fun dateSelected(date: LocalDate) {
    viewModelScope.launch {
      selectedDate = date
      titleState.value = date.year.toString()
      val monthCurrent = withContext(Dispatchers.Default) {
        JetMonth.current(date, firstDayOfWeek)
      }
      month.value = monthCurrent
    }
  }

  fun navBack() {
    navigator.navigateUp()
  }

  fun nextMonth() {
    month.value?.endDate?.plusDays(1)?.let { dateSelected(it) }
  }

  fun previousMonth() {
    month.value?.startDate?.minusDays(1)?.let { dateSelected(it) }
  }

}