package com.praxis.feat.calendarview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mutualmobile.praxis.navigator.Navigator
import com.mutualmobile.praxis.navigator.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetPagingSource
import dev.baseio.libjetcalendar.data.JetYear
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarYearVM @Inject constructor(private val navigator: Navigator) : ViewModel() {
  var selectedDate = JetDay(LocalDate.now(), isPartOfMonth = true)
  val firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
  val year = JetYear.current(selectedDate.date, firstDayOfWeek)

  fun navigateMonth() {
    navigator.navigate(Screen.CalendarMonthRoute.createRoute(selectedDate.date.toEpochDay()))
  }

}
